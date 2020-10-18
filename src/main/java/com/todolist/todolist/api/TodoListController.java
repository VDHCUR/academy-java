package com.todolist.todolist.api;

import com.todolist.todolist.entity.TodoList;
import com.todolist.todolist.service.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Map;
import java.util.UUID;

@RequestMapping("/lists")
@RestController
public class TodoListController {

    private final TodoListService todoListService;

    @Autowired
    public TodoListController(TodoListService todoListService){
        this.todoListService = todoListService;
    }

    @PostMapping
    public Page<TodoList> getAllLists(@RequestBody Map<String, String> additionalData){
        int page = Integer.parseInt(additionalData.getOrDefault("page", "0"));
        return todoListService.getAllLists(page);
    }

    @GetMapping(path = "{id}")
    public TodoList getListById(@PathVariable("id") UUID id){
        return todoListService.getListById(id);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/create")
    public TodoList createList(@Valid @NotBlank @RequestBody TodoList todoList){
        return todoListService.createList(todoList);
    }

    @DeleteMapping(path = "{id}")
    public void deleteListById(@PathVariable("id") UUID id){
        todoListService.deleteListById(id);
    }

    @PatchMapping(path = "{id}")
    public TodoList updateListName(@RequestBody Map<String, String> updateData, @PathVariable("id") UUID id){
        return todoListService.updateListNameById(updateData, id);
    }
}
