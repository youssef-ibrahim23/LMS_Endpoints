package com.example.sms.Controllers;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.sms.Services.DashboardService;
import com.example.sms.Services.ResponseService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping()
    public ResponseEntity<?> getDashboardData(
        @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
        @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        try {
            // Validate input parameters
            if (from == null || to == null) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("Both 'from' and 'to' dates are required"));
            }
            if (from.isAfter(to)) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("'from' date cannot be after 'to' date"));
            }

            Map<String, Object> dashboardData = dashboardService.getDashboardData(from, to);
            
            if (dashboardData.isEmpty()) {
                return ResponseEntity.ok()
                    .body(ResponseService.createSuccessResponse("No data available for the selected period", dashboardData));
            }
            
            return ResponseEntity.ok()
                .body(ResponseService.createSuccessResponse("Dashboard data retrieved successfully", dashboardData));
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                .body(ResponseService.createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to retrieve dashboard data: " + e.getMessage()));
        }
    }
}