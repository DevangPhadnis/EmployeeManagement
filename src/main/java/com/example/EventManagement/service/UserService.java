package com.example.EventManagement.service;

import com.example.EventManagement.models.User;

public interface UserService {

    public Integer addNewUser(User user);

    public String verifyUser(User user);
}
