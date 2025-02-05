package com.asset_management.services.impl;

import com.asset_management.dto.Assets.AssetItemsResDTO;
import com.asset_management.dto.Assets.AssetsReqDTO;
import com.asset_management.dto.Assets.AssetsResDTO;
import com.asset_management.dto.AssetsHistory.AssetsHistoryReqDTO;
import com.asset_management.dto.Category.CategoryResDTO;
import com.asset_management.dto.User.UserResDTO;
import com.asset_management.enums.HttpStatusEnum;
import com.asset_management.exceptions.ErrorException;
import com.asset_management.mappers.AssetMapper;
import com.asset_management.mappers.CategoryMapper;
import com.asset_management.mappers.UserMapper;
import com.asset_management.models.Asset;
import com.asset_management.models.Category;
import com.asset_management.models.User;
import com.asset_management.repositories.AssetsRepository;
import com.asset_management.repositories.CategoryRepository;
import com.asset_management.repositories.UserRepository;
import com.asset_management.services.IAssetsHistoryService;
import com.asset_management.services.IAssetsService;
import com.asset_management.utils.PaginationPage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AssetsService implements IAssetsService {
    private final AssetsRepository assetsRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final AssetMapper assetMapper;
    private final IAssetsHistoryService assetsHistoryService;
    private final CategoryMapper categoryMapper;
    private final UserMapper userMapper;

    @Override
    public AssetsResDTO addAsset(AssetsReqDTO assetsReqDTO) {
        Asset savedAsset = saveAsset(new Asset(), assetsReqDTO);
        AssetsHistoryReqDTO assetsHistoryReqDTO = new AssetsHistoryReqDTO(savedAsset.getId(), "Add", "", LocalDate.now());
        assetsHistoryService.addAssetHistory(assetsHistoryReqDTO);
        return assetMapper.toDTO(savedAsset);
    }

    @Override
    public PaginationPage<AssetsResDTO> getAllAssets(String search, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Asset> resultPage;

        if (search == null || search.isBlank()) {
            resultPage = assetsRepository.findAllAsset(pageable);
        } else {
            resultPage = assetsRepository.findByName(search, pageable);
        }

        return new PaginationPage<>(resultPage.map(assetMapper::toDTO));
    }

    @Override
    public AssetsResDTO getAssetById(Long id) {
        Asset asset = assetsRepository.findById(id).orElseThrow(() -> new ErrorException(HttpStatusEnum.BAD_REQUEST, "Asset not found"));
        return assetMapper.toDTO(asset);
    }

    @Override
    public AssetsResDTO updateAsset(Long id, AssetsReqDTO assetsReqDTO) {
        Asset asset = assetsRepository.findById(id).orElseThrow(() -> new ErrorException(HttpStatusEnum.BAD_REQUEST, "Asset not found"));
        Asset savedAsset = saveAsset(asset, assetsReqDTO);
        AssetsHistoryReqDTO assetsHistoryReqDTO = new AssetsHistoryReqDTO(savedAsset.getId(), "Update", "", LocalDate.now());
        assetsHistoryService.addAssetHistory(assetsHistoryReqDTO);
        return assetMapper.toDTO(savedAsset);
    }

    @Override
    public void deleteAsset(Long id) {
        Asset asset = assetsRepository.findById(id).orElseThrow(() -> new ErrorException(HttpStatusEnum.BAD_REQUEST, "Asset not found"));
        asset.setIsDeleted(true);
        assetsRepository.save(asset);
        AssetsHistoryReqDTO assetsHistoryReqDTO = new AssetsHistoryReqDTO(asset.getId(), "Delete", "", LocalDate.now());
        assetsHistoryService.addAssetHistory(assetsHistoryReqDTO);
    }

    @Override
    public AssetsResDTO assignAssetToUser(Long assetId, Long userId) {
        Asset asset = assetsRepository.findById(assetId).orElseThrow(() -> new ErrorException(HttpStatusEnum.BAD_REQUEST, "Asset not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new ErrorException(HttpStatusEnum.BAD_REQUEST, "User not found"));

        asset.setAssignedTo(user);

        return assetMapper.toDTO(assetsRepository.save(asset));
    }

    @Override
    public AssetItemsResDTO getItems() {
        List<Category> categories = categoryRepository.findAllNotDeleted();
        List<User> users = userRepository.findAllActiveUser();

        List<CategoryResDTO> categoryReqDTOList = categories.stream().map(categoryMapper::toDTO).toList();
        List<UserResDTO> userResDTOList = users.stream().map(userMapper::toDTO).toList();
        return new AssetItemsResDTO(categoryReqDTOList, userResDTOList);
    }

    private Asset saveAsset(Asset asset, AssetsReqDTO assetsReqDTO){
        Category category = categoryRepository.findById(assetsReqDTO.getCategoryId()).orElseThrow(() -> new ErrorException(HttpStatusEnum.BAD_REQUEST, "Category not found"));

        asset.setName(assetsReqDTO.getName());
        asset.setSerialNumber(assetsReqDTO.getSerialNumber());
        asset.setCategory(category);
        asset.setStatus(assetsReqDTO.getStatus());
        asset.setLocation(assetsReqDTO.getLocation());
        asset.setAcquisitionDate(assetsReqDTO.getAcquisitionDate());
        if(assetsReqDTO.getAssignedTo() != null){
            User user = userRepository.findById(assetsReqDTO.getAssignedTo()).orElseThrow(() -> new ErrorException(HttpStatusEnum.BAD_REQUEST, "User not found"));
            asset.setAssignedTo(user);
        }
        asset.setWarrantyExpiryDate(assetsReqDTO.getWarrantyExpiryDate());
        asset.setDocuments(String.join(",", assetsReqDTO.getDocuments()));

        return assetsRepository.save(asset);
    }
}
