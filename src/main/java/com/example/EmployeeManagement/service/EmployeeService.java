package com.example.EmployeeManagement.service;

import com.example.EmployeeManagement.models.Department;
import com.example.EmployeeManagement.models.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


public interface EmployeeService {
    public Integer addEmployee(Employee employee);

    public Page<Employee> fetchEmployee(PageRequest pageRequest);

    public ArrayList<Employee> fetchAllEmployee();

    public Integer deleteEmployee(Long emp_id);

    public Integer addDepartment(String departmentName);

    public ArrayList<Department> fetchDepartment();
}
