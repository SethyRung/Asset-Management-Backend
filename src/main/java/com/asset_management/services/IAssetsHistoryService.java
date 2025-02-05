package com.asset_management.services;

import com.asset_management.dto.AssetsHistory.AssetsHistoryReqDTO;
import com.asset_management.dto.AssetsHistory.AssetsHistoryResDTO;
import com.asset_management.utils.PaginationPage;

public interface IAssetsHistoryService {
    public PaginationPage<AssetsHistoryResDTO> getAllAssetHistory(int page, int size);
    public AssetsHistoryResDTO getAssetHistory(Long assetId);

    public AssetsHistoryResDTO addAssetHistory(AssetsHistoryReqDTO assetsHistoryReqDTO);
}
