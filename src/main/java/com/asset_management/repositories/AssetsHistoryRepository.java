package com.asset_management.repositories;

import com.asset_management.models.AssetHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssetsHistoryRepository extends JpaRepository<AssetHistory, Long> {
    public Optional<AssetHistory> findByAssetId(Long assetId);
}
