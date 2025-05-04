package com.example.EmployeeManagement.service;

import com.example.EmployeeManagement.models.User;

public interface UserService {

    public Integer addNewUser(User user);

    public String verifyUser(User user);
}
