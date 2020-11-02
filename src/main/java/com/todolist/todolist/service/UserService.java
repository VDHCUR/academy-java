package com.todolist.todolist.service;

import com.todolist.todolist.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    User register(User user);

    List<User> getAll();

    User findByUsername(String name);

    User findById (UUID id);

    void deleteUser(UUID id);
}
