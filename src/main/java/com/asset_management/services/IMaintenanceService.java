package com.asset_management.services;

import com.asset_management.dto.Maintenance.MaintenanceReqDTO;
import com.asset_management.models.Maintenance;
import com.asset_management.utils.PaginationPage;

public interface IMaintenanceService {
    public Maintenance addMaintenance(MaintenanceReqDTO categoryReqDTO);
    public PaginationPage<Maintenance> getAllMaintenance(int page, int size);
    public Maintenance getMaintenanceById(Long id);
    public Maintenance updateMaintenance(Long id, MaintenanceReqDTO categoryReqDTO);
    public void deleteMaintenance(Long id);
}
