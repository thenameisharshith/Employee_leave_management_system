package com.harshith.leave.service;

import com.harshith.leave.entity.*;
import com.harshith.leave.exception.*;
import com.harshith.leave.repository.*;
import org.springframework.stereotype.Service;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class LeaveService {
    private final LeaveRequestRepository leaveRepository;
    private final EmployeeRepository employeeRepository;
    private final LeaveBalanceRepository balanceRepository;

    public LeaveService(LeaveRequestRepository leaveRepository, EmployeeRepository employeeRepository, LeaveBalanceRepository balanceRepository) {
        this.leaveRepository = leaveRepository; this.employeeRepository = employeeRepository; this.balanceRepository = balanceRepository;
    }
    public LeaveRequest apply(Long employeeId, LeaveRequest request) {
        Employee employee = employeeRepository.findById(employeeId)
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + employeeId));
        if (request.getStartDate().isAfter(request.getEndDate()))
            throw new BusinessException("Start date cannot be after end date");
        request.setEmployee(employee);
        request.setStatus(LeaveStatus.PENDING);
        return leaveRepository.save(request);
    }
    public List<LeaveRequest> getAll() { return leaveRepository.findAll(); }
    public LeaveRequest getById(Long id) {
        return leaveRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Leave request not found: " + id));
    }
    public List<LeaveRequest> byEmployee(Long id) {
        employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + id));
        return leaveRepository.findByEmployeeId(id);
    }
    public LeaveRequest approve(Long id) {
        LeaveRequest r = getById(id);
        if (r.getStatus() != LeaveStatus.PENDING) throw new BusinessException("Only pending requests can be approved");
        int days = (int) (ChronoUnit.DAYS.between(r.getStartDate(), r.getEndDate()) + 1);
        LeaveBalance b = balanceRepository.findByEmployeeId(r.getEmployee().getId())
            .orElseThrow(() -> new ResourceNotFoundException("Leave balance not found"));
        int available = r.getLeaveType() == LeaveType.CASUAL ? b.getCasualLeave() :
                        r.getLeaveType() == LeaveType.SICK ? b.getSickLeave() : b.getEarnedLeave();
        if (days > available) throw new BusinessException("Insufficient leave balance");
        b.deduct(r.getLeaveType(), days);
        balanceRepository.save(b);
        r.setStatus(LeaveStatus.APPROVED);
        return leaveRepository.save(r);
    }
    public LeaveRequest reject(Long id) { return changeStatus(id, LeaveStatus.REJECTED); }
    public LeaveRequest cancel(Long id) { return changeStatus(id, LeaveStatus.CANCELLED); }
    private LeaveRequest changeStatus(Long id, LeaveStatus status) {
        LeaveRequest r = getById(id);
        if (r.getStatus() != LeaveStatus.PENDING) throw new BusinessException("Only pending requests can be changed");
        r.setStatus(status);
        return leaveRepository.save(r);
    }
}