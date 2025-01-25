package com.asset_management.repositories;

import com.asset_management.models.Asset;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AssetsRepository extends JpaRepository<Asset, Long> {

    @Query(
            value = "SELECT * FROM \"asset\" a WHERE a.is_deleted = false",
            nativeQuery = true
    )
    Page<Asset> findAllAsset(Pageable pageable);

    @Query(
            value = "SELECT * FROM \"asset\" a WHERE a.is_deleted = false AND LOWER(a.name) LIKE LOWER(%:search%)",
            nativeQuery = true
    )
    Page<Asset> findByName(@Param("search") String search, Pageable pageable);
}
