package com.asset_management.maintenance.controller;

import com.asset_management.maintenance.dto.MaintenanceItemsResDTO;
import com.asset_management.maintenance.dto.MaintenanceReqDTO;
import com.asset_management.maintenance.dto.MaintenanceResDTO;
import com.asset_management.maintenance.service.IMaintenanceService;
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
