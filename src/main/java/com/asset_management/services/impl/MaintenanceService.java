package com.asset_management.services.impl;

import com.asset_management.dto.Maintenance.MaintenanceItemsResDTO;
import com.asset_management.dto.Maintenance.MaintenanceReqDTO;
import com.asset_management.dto.Maintenance.MaintenanceResDTO;
import com.asset_management.enums.HttpStatusEnum;
import com.asset_management.exceptions.ErrorException;
import com.asset_management.mappers.MaintenanceMapper;
import com.asset_management.mappers.UserMapper;
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

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaintenanceService implements IMaintenanceService {
    private final MaintenanceRepository maintenanceRepository;
    private final UserRepository userRepository;
    private final AssetsRepository assetsRepository;
    private final MaintenanceMapper maintenanceMapper;
    private final UserMapper userMapper;

    @Override
    public MaintenanceResDTO addMaintenance(MaintenanceReqDTO maintenanceReqDTO) {
        Maintenance maintenance = new Maintenance();
        return saveMaintenance(maintenance, maintenanceReqDTO);
    }

    @Override
    public PaginationPage<MaintenanceResDTO> getAllMaintenance(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Maintenance> resultPage;

        resultPage = maintenanceRepository.findAll(pageable);

        return new PaginationPage<>(resultPage.map(maintenanceMapper::toDTO));
    }

    @Override
    public MaintenanceResDTO getMaintenanceById(Long id) {
        Maintenance maintenance = maintenanceRepository.findById(id).orElseThrow(() -> new ErrorException(HttpStatusEnum.BAD_REQUEST, "Maintenance not found"));
        return maintenanceMapper.toDTO(maintenance);
    }

    @Override
    public MaintenanceResDTO updateMaintenance(Long id, MaintenanceReqDTO maintenanceReqDTO) {
        Maintenance maintenance = maintenanceRepository.findById(id).orElseThrow(() -> new ErrorException(HttpStatusEnum.BAD_REQUEST, "Maintenance not found"));
        return saveMaintenance(maintenance, maintenanceReqDTO);
    }

    @Override
    public void deleteMaintenance(Long id) {
        maintenanceRepository.deleteById(id);
    }

    @Override
    public MaintenanceItemsResDTO getItems() {
        List<User> users = userRepository.findAllActiveUser();
        return new MaintenanceItemsResDTO(users.stream().map(userMapper::toDTO).toList());
    }

    private MaintenanceResDTO saveMaintenance(Maintenance maintenance, MaintenanceReqDTO maintenanceReqDTO){
        User user = userRepository.findById(maintenanceReqDTO.getPerformedBy()).orElseThrow(() -> new ErrorException(HttpStatusEnum.BAD_REQUEST, "User not found"));
        Asset asset = assetsRepository.findBySerialNumber(maintenanceReqDTO.getSerialNumber()).orElseThrow(() -> new ErrorException(HttpStatusEnum.BAD_REQUEST, "The provided serial number was not found. Please check and try again."));

        maintenance.setAsset(asset);
        maintenance.setMaintenanceDate(maintenanceReqDTO.getMaintenanceDate());
        maintenance.setDescription(maintenanceReqDTO.getDescription());
        maintenance.setCost(maintenanceReqDTO.getCost());
        maintenance.setPerformedBy(user);

        return maintenanceMapper.toDTO(maintenanceRepository.save(maintenance));
    }
}
