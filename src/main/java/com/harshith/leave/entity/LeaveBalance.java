package com.harshith.leave.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "leave_balances")
public class LeaveBalance {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "employee_id", unique = true)
    private Employee employee;

    private int casualLeave = 10;
    private int sickLeave = 10;
    private int earnedLeave = 15;

    public LeaveBalance() {}
    public LeaveBalance(Employee employee) { this.employee = employee; }
    public Long getId() { return id; }
    public Employee getEmployee() { return employee; }
    public int getCasualLeave() { return casualLeave; }
    public int getSickLeave() { return sickLeave; }
    public int getEarnedLeave() { return earnedLeave; }
    public void deduct(LeaveType type, int days) {
        if (type == LeaveType.CASUAL) casualLeave -= days;
        else if (type == LeaveType.SICK) sickLeave -= days;
        else earnedLeave -= days;
    }
}