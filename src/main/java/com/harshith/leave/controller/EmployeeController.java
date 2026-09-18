package com.harshith.leave.controller;

import com.harshith.leave.entity.*;
import com.harshith.leave.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService service;
    public EmployeeController(EmployeeService service) { this.service = service; }

    @PostMapping public org.springframework.http.ResponseEntity<Employee> create(@Valid @RequestBody Employee e) {
        return new org.springframework.http.ResponseEntity<>(service.create(e), HttpStatus.CREATED);
    }
    @GetMapping public List<Employee> all() { return service.getAll(); }
    @GetMapping("/{id}") public Employee one(@PathVariable Long id) { return service.getById(id); }
    @PutMapping("/{id}") public Employee update(@PathVariable Long id, @Valid @RequestBody Employee e) { return service.update(id, e); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id) { service.delete(id); }
    @GetMapping("/{id}/balance") public LeaveBalance balance(@PathVariable Long id) { return service.getBalance(id); }
}