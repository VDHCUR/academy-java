package com.todolist.todolist.security.jwt;

import com.todolist.todolist.entity.Role;
import com.todolist.todolist.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    public JwtUserFactory() {
    }

    public static JwtUser create(User user){
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                true,
                user.getUpdatedAt(),
                mapToGrantedAuthority(user.getRoles())
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthority(List<Role> userRoles){
        return userRoles.stream().map(role ->
            new SimpleGrantedAuthority(role.getName())
        ).collect(Collectors.toList());
    }
}
