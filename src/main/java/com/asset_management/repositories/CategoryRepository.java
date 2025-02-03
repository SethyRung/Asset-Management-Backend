package com.asset_management.repositories;

import com.asset_management.models.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query(
            value = "SELECT * FROM \"category\" c WHERE c.is_deleted = 'FALSE' && c.name LIKE '%:search%'",
            nativeQuery = true
    )
    Page<Category> findByNameContainingIgnoreCase(@Param("search") String search, Pageable pageable);

    @Query(
            value = "SELECT * FROM \"category\" c WHERE c.is_deleted = 'FALSE'",
            nativeQuery = true
    )
    List<Category> findAllNotDeleted();

    @Query(
            value = "SELECT * FROM \"category\" c WHERE c.is_deleted = 'FALSE'",
            nativeQuery = true
    )
    Page<Category> findAllNotDeleted(Pageable pageable);


}
