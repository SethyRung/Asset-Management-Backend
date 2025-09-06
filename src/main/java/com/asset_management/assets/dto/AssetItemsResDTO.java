package com.asset_management.assets.dto;

import com.asset_management.category.dto.CategoryResDTO;
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
public class AssetItemsResDTO {
   private List<CategoryResDTO> categories;
   private List<UserResDTO> users;
}
