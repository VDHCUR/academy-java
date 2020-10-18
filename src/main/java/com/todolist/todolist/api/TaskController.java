package com.todolist.todolist.api;

import com.todolist.todolist.entity.Task;
import com.todolist.todolist.entity.TodoList;
import com.todolist.todolist.repository.TodoListRepository;
import com.todolist.todolist.service.TaskService;
import com.todolist.todolist.service.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class TaskController {
    private final TaskService taskService;


    @Autowired
    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @PostMapping("/lists/{listId}")
    public Task createTask(@PathVariable("listId") UUID listId, @RequestBody Task task){
        return taskService.createTask(listId, task);
    }

    @PostMapping("/tasks/{id}/mark-done")
    public Task markTaskAsDone(@PathVariable("id") UUID id){
        return taskService.markTaskAsDone(id);
    }

    @DeleteMapping("/tasks/{id}")
    public void deleteTask(@PathVariable("id") UUID id){
        taskService.deleteTask(id);
    }
}
