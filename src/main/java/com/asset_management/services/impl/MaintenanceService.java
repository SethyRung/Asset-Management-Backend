package com.asset_management.services.impl;

import com.asset_management.dto.Maintenance.MaintenanceReqDTO;
import com.asset_management.enums.HttpStatusEnum;
import com.asset_management.exceptions.ErrorException;
import com.asset_management.models.Asset;
import com.asset_management.models.Maintenance;
import com.asset_management.models.User;
import com.asset_management.repositories.AssetsRepository;
import com.asset_management.repositories.MaintenanceRepository;
import com.asset_management.repositories.UserRepository;
import com.asset_management.services.IMaintenanceService;
import com.asset_management.utils.PaginationPage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MaintenanceService implements IMaintenanceService {
    private final MaintenanceRepository maintenanceRepository;
    private final UserRepository userRepository;
    private final AssetsRepository assetsRepository;

    @Override
    public Maintenance addMaintenance(MaintenanceReqDTO maintenanceReqDTO) {
        Maintenance maintenance = new Maintenance();
        return saveMaintenance(maintenance, maintenanceReqDTO);
    }

    @Override
    public PaginationPage<Maintenance> getAllMaintenance(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Maintenance> resultPage;

        resultPage = maintenanceRepository.findAll(pageable);

        return new PaginationPage<>(resultPage);
    }

    @Override
    public Maintenance getMaintenanceById(Long id) {
        return maintenanceRepository.findById(id).orElseThrow(() -> new ErrorException(HttpStatusEnum.BAD_REQUEST, "Maintenance not found"));
    }

    @Override
    public Maintenance updateMaintenance(Long id, MaintenanceReqDTO maintenanceReqDTO) {
        Maintenance maintenance = maintenanceRepository.findById(id).orElseThrow(() -> new ErrorException(HttpStatusEnum.BAD_REQUEST, "Maintenance not found"));
        return saveMaintenance(maintenance, maintenanceReqDTO);
    }

    @Override
    public void deleteMaintenance(Long id) {
        maintenanceRepository.deleteById(id);
    }

    private Maintenance saveMaintenance(Maintenance maintenance, MaintenanceReqDTO maintenanceReqDTO){
        User user = userRepository.findById(maintenanceReqDTO.getUserId()).orElseThrow(() -> new ErrorException(HttpStatusEnum.BAD_REQUEST, "User not found"));
        Asset asset = assetsRepository.findById(maintenanceReqDTO.getAssetId()).orElseThrow(() -> new ErrorException(HttpStatusEnum.BAD_REQUEST, "Asset not found"));

        maintenance.setAsset(asset);
        maintenance.setMaintenanceDate(maintenanceReqDTO.getMaintenanceDate());
        maintenance.setDescription(maintenanceReqDTO.getDescription());
        maintenance.setCost(maintenanceReqDTO.getCost());
        maintenance.setPerformedBy(user);

        return maintenanceRepository.save(maintenance);
    }
}
