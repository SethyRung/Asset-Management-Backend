package com.asset_management.services;

import com.asset_management.dto.Assets.AssetsReqDTO;
import com.asset_management.dto.Assets.AssetsResDTO;
import com.asset_management.models.Asset;
import com.asset_management.utils.PaginationPage;

import java.util.List;

public interface IAssetsService {
    public AssetsResDTO addAsset(AssetsReqDTO assetsReqDTO);
    public PaginationPage<AssetsResDTO> getAllAssets(String search, int page, int size);
    public AssetsResDTO getAssetById(Long id);
    public AssetsResDTO updateAsset(Long id, AssetsReqDTO assetsReqDTO);
    public void deleteAsset(Long id);
    public AssetsResDTO assignAssetToUser(Long assetId, Long userId);
}
