package com.asset_management.controllers;

import com.asset_management.dto.Assets.AssetItemsResDTO;
import com.asset_management.dto.Maintenance.MaintenanceItemsResDTO;
import com.asset_management.dto.Maintenance.MaintenanceReqDTO;
import com.asset_management.dto.Maintenance.MaintenanceResDTO;
import com.asset_management.models.Maintenance;
import com.asset_management.services.IMaintenanceService;
import com.asset_management.utils.PaginationPage;
import com.asset_management.utils.ResponseBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Maintenance")
@RestController
@RequestMapping(value = "/api/maintenance")
@RequiredArgsConstructor
public class MaintenanceController {
    private final IMaintenanceService maintenanceService;

    @PostMapping
    public ResponseEntity<ResponseBody<MaintenanceResDTO>> addMaintenance(@RequestBody MaintenanceReqDTO maintenanceReqDTO) {
        return ResponseEntity.ok(new ResponseBody<>(maintenanceService.addMaintenance(maintenanceReqDTO)));
    }

    @GetMapping
    public ResponseEntity<ResponseBody<PaginationPage<MaintenanceResDTO>>> getAllMaintenance(@RequestParam(required = false) String search,
                                                                                 @RequestParam(defaultValue = "0") int page,
                                                                                 @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(new ResponseBody<>(maintenanceService.getAllMaintenance(page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBody<MaintenanceResDTO>> getMaintenanceById(@PathVariable Long id) {
        return ResponseEntity.ok(new ResponseBody<>(maintenanceService.getMaintenanceById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBody<MaintenanceResDTO>> updateMaintenance(@PathVariable Long id, @RequestBody MaintenanceReqDTO maintenanceReqDTO) {
        return ResponseEntity.ok(new ResponseBody<>(maintenanceService.updateMaintenance(id, maintenanceReqDTO)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBody<?>> deleteMaintenance(@PathVariable Long id) {
        maintenanceService.deleteMaintenance(id);
        return ResponseEntity.ok(new ResponseBody<>());
    }

    @GetMapping(value = "/items")
    public ResponseEntity<ResponseBody<MaintenanceItemsResDTO>> getItems(){
        return ResponseEntity.ok(new ResponseBody<>(maintenanceService.getItems()));
    }
}
