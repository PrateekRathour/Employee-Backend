package com.emp.management.controller;

import com.emp.management.dto.ApiResponse;
import com.emp.management.dto.DashboardStatsDto;
import com.emp.management.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@Tag(name = "Dashboard & Analytics", description = "REST APIs for employee analytics, KPIs, and department metrics")
@CrossOrigin(origins = "*")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/stats")
    @Operation(summary = "Get aggregate statistics and analytics for dashboard")
    public ResponseEntity<ApiResponse<DashboardStatsDto>> getDashboardStats() {
        DashboardStatsDto stats = dashboardService.getDashboardStatistics();
        return ResponseEntity.ok(ApiResponse.ok("Dashboard statistics retrieved", stats));
    }
}
