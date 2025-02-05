package com.asset_management.services.impl;

import com.asset_management.models.Asset;
import com.asset_management.models.Category;
import com.asset_management.models.User;
import com.asset_management.repositories.AssetsRepository;
import com.asset_management.repositories.CategoryRepository;
import com.asset_management.repositories.UserRepository;
import com.asset_management.services.IDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class DashboardService implements IDashboardService {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final AssetsRepository assetsRepository;

    @Override
    public Map<String, Object> getDashboardData() {
        List<Category> categories = categoryRepository.findAllNotDeleted();
        List<User> users = userRepository.findAllActiveUser();
        List<Asset> assets = assetsRepository.findAll();

        long totalAsset = assets.size();
        long activeAsset = assets.stream().filter(asset -> Objects.equals(asset.getStatus(), "Active")).count();
        long warrantyExpired = assets.stream().filter((asset -> asset.getWarrantyExpiryDate().after(new Date()))).count();
        long client = users.size();

        Map<String, Integer> assetByCategory = new HashMap<>();
        for (Category category : categories){
            assetByCategory.put(category.getName(), assetsRepository.findAllByCategory(category.getId()).size());
        }

        Map<String, Integer> assetByStatus = new HashMap<>();
        assetByStatus.put("active", assetsRepository.findAllByStatus("Active").size());
        assetByStatus.put("inactive", assetsRepository.findAllByStatus("Inactive").size());
        assetByStatus.put("repair", assetsRepository.findAllByStatus("Repair").size());

        Map<String, Object> response = new HashMap<>();
        response.put("totalAsset", totalAsset);
        response.put("activeAsset", activeAsset);
        response.put("warrantyExpired", warrantyExpired);
        response.put("client", client);
        response.put("assetByCategory", assetByCategory);
        response.put("assetByStatus", assetByStatus);


        return response;
    }
}
