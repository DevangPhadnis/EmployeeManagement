package com.example.EventManagement.serviceImpl;

import com.example.EventManagement.dao.UserRepository;
import com.example.EventManagement.models.User;
import com.example.EventManagement.service.UserService;
import com.example.EventManagement.utilis.JWTUtilis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtilis jwtUtilis;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public Integer addNewUser(User user) {
        logger.info("Inside Add New User Serviceimpl");
        try {
            String password = "NewTestingUser@1234";
            user.setPassword(encoder.encode(password));
            userRepository.save(user);
        } catch (Exception e) {
            logger.error("Error Found" + e);
            throw new RuntimeException(e);
        }
        logger.info("Outside Add New User Serviceimpl");
        return 1;
    }

    @Override
    public String verifyUser(User user) {
        logger.info("Inside Verify User Serviceimpl");
        Authentication authentication = authenticationManager.authenticate(new
                UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
        if(authentication.isAuthenticated()) {
            return jwtUtilis.generateToken(user.getUserName());
        }
        else {
            logger.info("Outside Verify User Serviceimpl With Authentication Failed");
            return "Error in Generating Token";
        }
    }
}
