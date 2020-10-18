package com.todolist.todolist.service;

import com.todolist.todolist.entity.Task;
import com.todolist.todolist.entity.TodoList;
import com.todolist.todolist.errors.NotFoundException;
import com.todolist.todolist.repository.TaskRepository;
import com.todolist.todolist.repository.TodoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final TodoListRepository todoListRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, TodoListRepository todoListRepository) {
        this.taskRepository = taskRepository;
        this.todoListRepository = todoListRepository;
    }

    public Task createTask(UUID listId, Task task){
        TodoList todolist = todoListRepository.findById(listId).orElseThrow(() -> new NotFoundException("List", listId));
        task.setTodoList(todolist);
        return taskRepository.save(task);
    }

    public Task markTaskAsDone(UUID id){
        return taskRepository.findById(id).map(task -> {
            task.markAsDone();
            return taskRepository.save(task);
        }).orElseThrow(() -> new NotFoundException("Task", id));
    }

    public void deleteTask(UUID id){
        if (taskRepository.existsById(id)){
            taskRepository.deleteById(id);
        } else {
            throw new NotFoundException("Task", id);
        }
    }
}
