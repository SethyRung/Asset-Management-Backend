package com.asset_management.repositories;

import com.asset_management.models.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {
}
