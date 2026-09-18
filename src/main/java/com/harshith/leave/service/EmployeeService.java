package com.harshith.leave.service;

import com.harshith.leave.entity.*;
import com.harshith.leave.exception.ResourceNotFoundException;
import com.harshith.leave.repository.*;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final LeaveBalanceRepository balanceRepository;

    public EmployeeService(EmployeeRepository employeeRepository, LeaveBalanceRepository balanceRepository) {
        this.employeeRepository = employeeRepository; this.balanceRepository = balanceRepository;
    }
    public Employee create(Employee employee) {
        Employee saved = employeeRepository.save(employee);
        balanceRepository.save(new LeaveBalance(saved));
        return saved;
    }
    public List<Employee> getAll() { return employeeRepository.findAll(); }
    public Employee getById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + id));
    }
    public Employee update(Long id, Employee data) {
        Employee e = getById(id);
        e.setName(data.getName()); e.setEmail(data.getEmail()); e.setDepartment(data.getDepartment());
        return employeeRepository.save(e);
    }
    public void delete(Long id) { employeeRepository.delete(getById(id)); }
    public LeaveBalance getBalance(Long id) {
        getById(id);
        return balanceRepository.findByEmployeeId(id).orElseThrow(() -> new ResourceNotFoundException("Leave balance not found"));
    }
}