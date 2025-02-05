package com.asset_management.services.impl;

import com.asset_management.dto.AssetsHistory.AssetsHistoryReqDTO;
import com.asset_management.dto.AssetsHistory.AssetsHistoryResDTO;
import com.asset_management.enums.HttpStatusEnum;
import com.asset_management.exceptions.ErrorException;
import com.asset_management.mappers.AssetHistoryMapper;
import com.asset_management.models.Asset;
import com.asset_management.models.AssetHistory;
import com.asset_management.models.User;
import com.asset_management.repositories.AssetsHistoryRepository;
import com.asset_management.repositories.AssetsRepository;
import com.asset_management.services.IAssetsHistoryService;
import com.asset_management.utils.PaginationPage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssetsHistoryService implements IAssetsHistoryService {
    private final AssetsHistoryRepository assetsHistoryRepository;
    private final AssetsRepository assetsRepository;
    private final UserService userService;
    private final AssetHistoryMapper assetHistoryMapper;

    @Override
    public PaginationPage<AssetsHistoryResDTO> getAllAssetHistory(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AssetHistory> resultPage;
        resultPage = assetsHistoryRepository.findAll(pageable);
        return new PaginationPage<>(resultPage.map(assetHistoryMapper::toDTO));
    }

    @Override
    public AssetsHistoryResDTO getAssetHistory(Long assetId) {
        AssetHistory assetHistory = assetsHistoryRepository.findByAssetId(assetId).orElseThrow(()->new ErrorException(HttpStatusEnum.BAD_REQUEST, "History of Asset not found"));
        return assetHistoryMapper.toDTO(assetHistory);
    }

    @Override
    public AssetsHistoryResDTO addAssetHistory(AssetsHistoryReqDTO assetsHistoryReqDTO) {
        AssetHistory assetHistory = new AssetHistory();
        Asset asset = assetsRepository.findById(assetsHistoryReqDTO.getAssetID()).orElseThrow(() -> new ErrorException(HttpStatusEnum.BAD_REQUEST, "Asset not found"));
        User user = userService.getCurrentUser();

        assetHistory.setAsset(asset);
        assetHistory.setAction(assetsHistoryReqDTO.getAction());
        assetHistory.setDetails(assetsHistoryReqDTO.getDetails());
        assetHistory.setActionDate(assetsHistoryReqDTO.getActionDate());
        assetHistory.setActionPerformedBy(user);

        return assetHistoryMapper.toDTO(assetsHistoryRepository.save(assetHistory));
    }
}
