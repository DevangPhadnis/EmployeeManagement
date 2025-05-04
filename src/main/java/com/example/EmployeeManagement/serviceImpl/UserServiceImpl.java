package com.example.EmployeeManagement.serviceImpl;

import com.example.EmployeeManagement.dao.UserRepository;
import com.example.EmployeeManagement.models.User;
import com.example.EmployeeManagement.service.UserService;
import com.example.EmployeeManagement.utilis.DecryptionUtilis;
import com.example.EmployeeManagement.utilis.JWTUtilis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtilis jwtUtilis;

    @Autowired
    private DecryptionUtilis decryptionUtilis;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public Integer addNewUser(User user) {
        logger.info("Inside Add New User Serviceimpl");
        try {
            if(user.getPassword() == null || user.getPassword().equalsIgnoreCase("")) {
                user.setPassword(encoder.encode("EmsAdmin@1234"));
            }
            user.setCreatedDate(LocalDateTime.now());
            user.setStatus(1);
            userRepository.save(user);
        } catch (Exception e) {
            logger.error("Error Found" + e);
            return 0;
        }
        logger.info("Outside Add New User Serviceimpl");
        return 1;
    }

    @Override
    public String verifyUser(User user) {
        logger.info("Inside Verify User Serviceimpl");
        try {
            String password = decryptionUtilis.decrypt(user.getPassword());
            Authentication authentication = authenticationManager.authenticate(new
                    UsernamePasswordAuthenticationToken(user.getUserName(), password));
            if(authentication.isAuthenticated()) {
                User user1 = userRepository.findByUserName(user.getUserName());
                if(user1 != null && user1.getStatus() == 1) {
                    return jwtUtilis.generateToken(user.getUserName(), user1.getRole());
                }
                else {
                    return null;
                }
            }
            else {
                return null;
            }
        } catch (Exception e) {
            logger.error("Exception during authentication: " , e);
            return null;
        }
    }
}
