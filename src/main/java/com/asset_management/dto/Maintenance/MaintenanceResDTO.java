package com.asset_management.dto.Maintenance;

import com.asset_management.dto.Assets.AssetsResDTO;
import com.asset_management.dto.User.UserResDTO;
import com.asset_management.models.Asset;
import com.asset_management.models.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
