package com.asset_management.maintenance.repository;

import com.asset_management.maintenance.model.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {
}
