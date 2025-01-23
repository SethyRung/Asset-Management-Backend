package com.asset_management.repositories;

import com.asset_management.models.Asset;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetsRepository extends JpaRepository<Asset, Long> {
    Page<Asset> findByNameContainingIgnoreCase(String search, Pageable pageable);
}
