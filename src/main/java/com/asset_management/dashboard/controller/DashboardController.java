package com.asset_management.dashboard.controller;

import com.asset_management.dashboard.service.IDashboardService;
import com.asset_management.utils.ResponseBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Dashboard")
@RestController
@RequestMapping(value = "/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final IDashboardService dashboardService;

    @GetMapping
    public ResponseEntity<ResponseBody<?>> getDashboardData() {
        return ResponseEntity.ok(new ResponseBody<>(dashboardService.getDashboardData()));
    }
}
