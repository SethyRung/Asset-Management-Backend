package com.asset_management.services;

import com.asset_management.dto.Maintenance.MaintenanceItemsResDTO;
import com.asset_management.dto.Maintenance.MaintenanceReqDTO;
import com.asset_management.dto.Maintenance.MaintenanceResDTO;
import com.asset_management.models.Maintenance;
import com.asset_management.utils.PaginationPage;

public interface IMaintenanceService {
    public MaintenanceResDTO addMaintenance(MaintenanceReqDTO categoryReqDTO);
    public PaginationPage<MaintenanceResDTO> getAllMaintenance(int page, int size);
    public MaintenanceResDTO getMaintenanceById(Long id);
    public MaintenanceResDTO updateMaintenance(Long id, MaintenanceReqDTO categoryReqDTO);
    public void deleteMaintenance(Long id);
    public MaintenanceItemsResDTO getItems();
}
