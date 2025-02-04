package com.asset_management.dto.Maintenance;

import com.asset_management.models.Asset;
import com.asset_management.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MaintenanceReqDTO {
    private String serialNumber;
    private LocalDate maintenanceDate;
    private String description;
    private Double cost;
    private Long performedBy;
}
