package com.asset_management.dto.Maintenance;

import com.asset_management.dto.Assets.AssetsResDTO;
import com.asset_management.dto.Category.CategoryResDTO;
import com.asset_management.dto.User.UserResDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MaintenanceItemsResDTO {
    private List<CategoryResDTO> categories;
}
