package com.asset_management.assets.service;

import com.asset_management.assets.dto.AssetItemsResDTO;
import com.asset_management.assets.dto.AssetsReqDTO;
import com.asset_management.assets.dto.AssetsResDTO;
import com.asset_management.utils.PaginationPage;


public interface IAssetsService {
    public AssetsResDTO addAsset(AssetsReqDTO assetsReqDTO);
    public PaginationPage<AssetsResDTO> getAllAssets(String search, int page, int size);
    public AssetsResDTO getAssetById(Long id);
    public AssetsResDTO updateAsset(Long id, AssetsReqDTO assetsReqDTO);
    public void deleteAsset(Long id);
    public AssetsResDTO assignAssetToUser(Long assetId, Long userId);
    public AssetItemsResDTO getItems();
}
