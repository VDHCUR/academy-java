package com.todolist.todolist.service;

import com.todolist.todolist.entity.Role;
import com.todolist.todolist.entity.User;
import com.todolist.todolist.errors.NotFoundException;
import com.todolist.todolist.repository.RoleRepository;
import com.todolist.todolist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(User user) {
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);

        return userRepository.save(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByUsername(String name) {
        return userRepository.findByUsername(name).orElseThrow(
                () -> new NotFoundException("User", "username", name)
        );
    }

    @Override
    public User findById(UUID id) {
        return userRepository.findById(id).orElseThrow(()->new NotFoundException("User", id));
    }

    @Override
    public void deleteUser(UUID id) {
        if (userRepository.existsById(id)){
            userRepository.deleteById(id);
        } else {
            throw new NotFoundException("User", id);
        }
    }
}
