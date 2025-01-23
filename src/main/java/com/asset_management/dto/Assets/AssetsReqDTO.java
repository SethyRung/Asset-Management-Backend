package com.asset_management.dto.Assets;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AssetsReqDTO {
    private String name;
    private String serialNumber;
    private Long categoryId;
    private String status;  // E.g., Active, In Repair, Retired
    private String location;
    private Long userId;
    private Date acquisitionDate;
    private Date warrantyExpiryDate;
    private String documents;
}
