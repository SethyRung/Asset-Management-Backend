package com.asset_management.maintenance.service;

import com.asset_management.maintenance.dto.MaintenanceItemsResDTO;
import com.asset_management.maintenance.dto.MaintenanceReqDTO;
import com.asset_management.maintenance.dto.MaintenanceResDTO;
import com.asset_management.utils.PaginationPage;

public interface IMaintenanceService {
    public MaintenanceResDTO addMaintenance(MaintenanceReqDTO categoryReqDTO);
    public PaginationPage<MaintenanceResDTO> getAllMaintenance(int page, int size);
    public MaintenanceResDTO getMaintenanceById(Long id);
    public MaintenanceResDTO updateMaintenance(Long id, MaintenanceReqDTO categoryReqDTO);
    public void deleteMaintenance(Long id);
    public MaintenanceItemsResDTO getItems();
}
