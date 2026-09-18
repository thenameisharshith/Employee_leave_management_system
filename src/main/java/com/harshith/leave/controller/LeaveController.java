package com.harshith.leave.controller;

import com.harshith.leave.entity.LeaveRequest;
import com.harshith.leave.service.LeaveService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/leaves")
public class LeaveController {
    private final LeaveService service;
    public LeaveController(LeaveService service) { this.service = service; }

    @PostMapping("/employee/{employeeId}") public LeaveRequest apply(@PathVariable Long employeeId, @Valid @RequestBody LeaveRequest r) { return service.apply(employeeId, r); }
    @GetMapping public List<LeaveRequest> all() { return service.getAll(); }
    @GetMapping("/{id}") public LeaveRequest one(@PathVariable Long id) { return service.getById(id); }
    @GetMapping("/employee/{employeeId}") public List<LeaveRequest> byEmployee(@PathVariable Long employeeId) { return service.byEmployee(employeeId); }
    @PutMapping("/{id}/approve") public LeaveRequest approve(@PathVariable Long id) { return service.approve(id); }
    @PutMapping("/{id}/reject") public LeaveRequest reject(@PathVariable Long id) { return service.reject(id); }
    @PutMapping("/{id}/cancel") public LeaveRequest cancel(@PathVariable Long id) { return service.cancel(id); }
}