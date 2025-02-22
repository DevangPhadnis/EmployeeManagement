package com.example.EventManagement.controller;

import com.example.EventManagement.models.User;
import com.example.EventManagement.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String addUser(@RequestBody User user) {
        logger.info("Inside Add New User Controller");

        userService.addNewUser(user);

        logger.info("Outside Add New User Controller");
        return "Success";
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        logger.info("Inside Verify User Controller");
        return userService.verifyUser(user);
    }
}
