package com.asset_management.dto.Assets;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AssetsResDTO {
    private Long id;
    private String name;
    private String serialNumber;
    private Long categoryId;
    private String status;
    private String location;
    private String acquisitionDate;
    private Long assignedTo;
    private String warrantyExpiryDate;
    private List<String> documents;
}
