package com.asset_management.assets_history.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AssetsHistoryReqDTO {
    private Long assetID;
    private String action;
    private String details;
    private LocalDate actionDate;
}
