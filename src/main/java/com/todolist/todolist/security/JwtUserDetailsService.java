package com.todolist.todolist.security;

import com.todolist.todolist.entity.User;
import com.todolist.todolist.security.jwt.JwtUser;
import com.todolist.todolist.security.jwt.JwtUserFactory;
import com.todolist.todolist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);

        JwtUser jwtUser = JwtUserFactory.create(user);

        return null;
    }
}
