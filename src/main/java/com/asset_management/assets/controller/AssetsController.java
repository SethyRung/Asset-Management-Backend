package com.asset_management.assets.controller;

import com.asset_management.assets.dto.AssetItemsResDTO;
import com.asset_management.assets.dto.AssetsReqDTO;
import com.asset_management.assets.dto.AssetsResDTO;
import com.asset_management.assets.service.IAssetsService;
import com.asset_management.utils.PaginationPage;
import com.asset_management.utils.ResponseBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Assets")
@RestController
@RequestMapping(value = "/api/assets")
@RequiredArgsConstructor
public class AssetsController {
    private final IAssetsService assetsService;

    @PostMapping
    public ResponseEntity<ResponseBody<AssetsResDTO>> addAsset(@RequestBody AssetsReqDTO assetsReqDTO){
        return ResponseEntity.ok(new ResponseBody<>(assetsService.addAsset(assetsReqDTO)));
    }

    @GetMapping
    public  ResponseEntity<ResponseBody<PaginationPage<AssetsResDTO>>> getAllAssets(@RequestParam(required = false) String search,
                                                                             @RequestParam(defaultValue = "0") int page,
                                                                             @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(new ResponseBody<>(assetsService.getAllAssets(search, page, size)));
    }

    @GetMapping(value = "/{id}")
    public  ResponseEntity<ResponseBody<AssetsResDTO>> getAssetById(@PathVariable("id") Long id){
        return ResponseEntity.ok(new ResponseBody<>(assetsService.getAssetById(id)));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ResponseBody<AssetsResDTO>> updateAsset(@PathVariable("id") Long id, @RequestBody AssetsReqDTO assetsReqDTO){
        return ResponseEntity.ok(new ResponseBody<>(assetsService.updateAsset(id, assetsReqDTO)));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ResponseBody<?>> deleteAsset(@PathVariable("id") Long id){
        assetsService.deleteAsset(id);
        return ResponseEntity.ok(new ResponseBody<>());
    }

    @PostMapping(value = "/assign")
    public ResponseEntity<ResponseBody<AssetsResDTO>> assignAssetToUser(@RequestParam Long assetId, @RequestParam Long userId){
        return ResponseEntity.ok(new ResponseBody<>(assetsService.assignAssetToUser(assetId, userId)));
    }

    @GetMapping(value = "/items")
    public ResponseEntity<ResponseBody<AssetItemsResDTO>> getItems(){
        return ResponseEntity.ok(new ResponseBody<>(assetsService.getItems()));
    }
}
