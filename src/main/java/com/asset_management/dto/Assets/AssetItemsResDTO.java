package com.asset_management.dto.Assets;

import com.asset_management.dto.Category.CategoryResDTO;
import com.asset_management.dto.User.UserResDTO;
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
