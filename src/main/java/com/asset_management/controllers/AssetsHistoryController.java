package com.asset_management.controllers;

import com.asset_management.dto.AssetsHistory.AssetsHistoryResDTO;
import com.asset_management.services.IAssetsHistoryService;
import com.asset_management.utils.PaginationPage;
import com.asset_management.utils.ResponseBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "History")
@RestController
@RequestMapping(value = "/api/history")
@RequiredArgsConstructor
public class AssetsHistoryController {
    private final IAssetsHistoryService assetsHistoryService;

    @GetMapping
    public ResponseEntity<ResponseBody<PaginationPage<AssetsHistoryResDTO>>> getAllAssetsHistory(
                                                            @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(new ResponseBody<>(assetsHistoryService.getAllAssetHistory(page, size)));
    }

    @GetMapping(value = "/{assetId}")
    public  ResponseEntity<ResponseBody<AssetsHistoryResDTO>> getAssetById(@PathVariable Long assetId){
        return ResponseEntity.ok(new ResponseBody<>(assetsHistoryService.getAssetHistory(assetId)));
    }
}
