package com.asset_management.maintenance.dto;

import com.asset_management.assets.dto.AssetsResDTO;
import com.asset_management.user.dto.UserResDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MaintenanceResDTO {
    private Long id;
    private AssetsResDTO asset;
    private LocalDate maintenanceDate;
    private String description;
    private Double cost;
    private UserResDTO performedBy;
}
