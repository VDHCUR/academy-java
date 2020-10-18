package com.todolist.todolist.repository;

import com.todolist.todolist.entity.TodoList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TodoListRepository extends JpaRepository<TodoList, UUID> {

    Page<TodoList> findAll(Pageable pageable);

    Optional<TodoList> findById (UUID id);
}
