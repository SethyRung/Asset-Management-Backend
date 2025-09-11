package com.asset_management.maintenance.dto;

import com.asset_management.user.dto.UserResDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MaintenanceItemsResDTO {
    private List<UserResDTO> users;
}
