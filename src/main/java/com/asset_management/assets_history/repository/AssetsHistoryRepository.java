package com.asset_management.assets_history.repository;

import com.asset_management.assets_history.model.AssetHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssetsHistoryRepository extends JpaRepository<AssetHistory, Long> {
    public Optional<AssetHistory> findByAssetId(Long assetId);
}
