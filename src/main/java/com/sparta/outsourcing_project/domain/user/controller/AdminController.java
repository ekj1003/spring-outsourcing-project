package com.sparta.outsourcing_project.domain.user.controller;

import com.sparta.outsourcing_project.domain.user.dto.response.AdminUsersDto;
import com.sparta.outsourcing_project.domain.user.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/users")
    public ResponseEntity<Page<AdminUsersDto>> getAllUsers(@PageableDefault(size = 5, sort = "email") Pageable pageable) {
        return ResponseEntity.ok(adminService.getAllUsers(pageable));
    }

    @GetMapping("/orders/counts/daily")
    public ResponseEntity<?> getOrdersCountedDaily(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        if (date != null) {
            return ResponseEntity.ok(adminService.getOrdersCountByDate(date));
        }
        return ResponseEntity.ok(adminService.getOrdersCountedDaily());
    }

    @GetMapping("/orders/counts/monthly")
    public ResponseEntity<?> getOrdersCountedMonthly(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM") LocalDate date) {
        if (date != null) {
            return ResponseEntity.ok(adminService.getOrdersCountByMonth(date));
        }
        return ResponseEntity.ok(adminService.getOrdersCountedMonthly());
    }

    @GetMapping("/orders/total-prices/daily")
    public ResponseEntity<?> getOrdersTotalPriceDaily(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        if (date != null) {
            return ResponseEntity.ok(adminService.getOrdersTotalPriceByDate(date));
        }
        return ResponseEntity.ok(adminService.getOrdersTotalPriceDaily());
    }

    @GetMapping("/orders/total-prices/monthly")
    public ResponseEntity<?> getOrdersTotalPriceMonthly(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM") LocalDate date) {
        if (date != null) {
            return ResponseEntity.ok(adminService.getOrdersTotalPriceByMonth(date));
        }
        return ResponseEntity.ok(adminService.getOrdersTotalPriceMonthly());
    }
}
