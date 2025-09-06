package com.asset_management.assets_history.service;

import com.asset_management.assets_history.dto.AssetsHistoryReqDTO;
import com.asset_management.assets_history.dto.AssetsHistoryResDTO;
import com.asset_management.utils.PaginationPage;

public interface IAssetsHistoryService {
    public PaginationPage<AssetsHistoryResDTO> getAllAssetHistory(int page, int size);
    public AssetsHistoryResDTO getAssetHistory(Long assetId);

    public AssetsHistoryResDTO addAssetHistory(AssetsHistoryReqDTO assetsHistoryReqDTO);
}
