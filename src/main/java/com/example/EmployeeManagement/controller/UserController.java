package com.example.EmployeeManagement.controller;

import com.example.EmployeeManagement.dao.EmployeeRepository;
import com.example.EmployeeManagement.dao.UserRepository;
import com.example.EmployeeManagement.models.Employee;
import com.example.EmployeeManagement.models.Response;
import com.example.EmployeeManagement.models.User;
import com.example.EmployeeManagement.models.UserProfileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/profile")
    public ResponseEntity<?> fetchUserDetails(Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUserName(username);
        Employee employee = employeeRepository.findByEmail(username);


        UserProfileDto userProfileDto = new UserProfileDto();

        if(employee != null) {
            userProfileDto.setEmployeeId(employee.getEmployeeId());
            userProfileDto.setFirstName(employee.getFirstName());
            userProfileDto.setMiddleName(employee.getMiddleName());
            userProfileDto.setLastName(employee.getLastName());
            userProfileDto.setDepartmentName(employee.getDepartment().getDepartmentId().toString());
            userProfileDto.setDesignation(employee.getDesignation());
            userProfileDto.setJoiningDate(employee.getJoiningDate());
            userProfileDto.setMobile(employee.getMobile());
            userProfileDto.setEmail(employee.getEmail());
            userProfileDto.setEmp_id(employee.getEmp_id());
        }
        userProfileDto.setRole(user.getRole());

        Response response = new Response();
        response.setData(userProfileDto);
        response.setMessage("User Details Fetched Successfully");
        response.setStatus("1");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
