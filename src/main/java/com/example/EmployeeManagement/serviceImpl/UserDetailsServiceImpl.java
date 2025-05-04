package com.example.EmployeeManagement.serviceImpl;

import com.example.EmployeeManagement.dao.UserRepository;
import com.example.EmployeeManagement.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if(user != null) {
            return org.springframework.security.core.userdetails.User
                    .builder()
                    .username(user.getUserName())
                    .password(user.getPassword())
                    .build();
        }

        throw new UsernameNotFoundException("User Not Found with user name: " +  username);
    }
}
