package com.example.EmployeeManagement.controller;

import com.example.EmployeeManagement.models.Department;
import com.example.EmployeeManagement.models.Employee;
import com.example.EmployeeManagement.models.Response;
import com.example.EmployeeManagement.models.User;
import com.example.EmployeeManagement.service.EmployeeService;
import com.example.EmployeeManagement.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/register")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        logger.info("Inside Add New User Controller");

        userService.addNewUser(user);

        logger.info("Outside Add New User Controller");
        Response response = new Response();
        response.setStatus("1");
        response.setMessage("User Added Successfully");
        response.setData(null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        logger.info("Inside Verify User Controller");
        String token = userService.verifyUser(user);
        if(token != null) {
            Response response = new Response();
            response.setStatus("1");
            response.setMessage("Login Success");
            response.setData(token);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else {
            Response response = new Response();
            response.setStatus("0");
            response.setMessage("Login Credentials are in correct");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/saveEmployee")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addEmployee(@RequestBody Employee employee) {
        logger.info("Inside Add Employee Method");
        Integer res = employeeService.addEmployee(employee);
        if(res == 1) {
            Response response = new Response();
            response.setStatus("1");
            response.setMessage("Employee Added Successfully");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else {
            Response response = new Response();
            response.setStatus("0");
            response.setMessage("Failure in Adding Employee");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/fetchEmployee")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> fetchEmployee(@RequestParam Integer pageNumber, @RequestParam Integer rowCount) {
        logger.info("Inside Fetch Employee Method of Admin Controller");
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, rowCount);
        Page<Employee> employeeList = employeeService.fetchEmployee(pageRequest);
        Map<String, Object> response = new HashMap<>();
        response.put("data", employeeList.getContent());
        response.put("totalRecords", employeeList.getTotalElements());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/deleteEmployee")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteEmployee(@RequestParam Long emp_id) {
        logger.info("Inside Delete Employee Method of Admin Controller");
        Integer res = employeeService.deleteEmployee(emp_id);

        if(res == 1) {
            Response response = new Response();
            response.setStatus("1");
            response.setMessage("Employee Deleted Successfully");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else {
            Response response = new Response();
            response.setStatus("0");
            response.setMessage("Failure in Deleting Employee");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addDepartment")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addDepartment(@RequestParam String departmentName) {
        logger.info("Inside Add Department Method of Admin Controller");
        Integer res = employeeService.addDepartment(departmentName);
        if(res == 1) {
            Response response = new Response();
            response.setStatus("1");
            response.setMessage("Department Added Successfully");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else {
            Response response = new Response();
            response.setStatus("0");
            response.setMessage("Failure in Adding Department");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/fetchDepartment")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> fetchDepartment() {
        logger.info("Inside Fetch Department Method of Admin Controller");
        ArrayList<Department> departmentList = employeeService.fetchDepartment();
        return new ResponseEntity<>(departmentList, HttpStatus.OK);
    }
}
