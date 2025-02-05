package com.asset_management.dto.AssetsHistory;

import com.asset_management.dto.Assets.AssetsResDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AssetsHistoryResDTO {
    private Long id;
    private AssetsResDTO asset;
    private String action;
    private String details;
    private String actionDate;
    private Long userId;
}
