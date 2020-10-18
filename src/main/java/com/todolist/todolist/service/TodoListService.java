package com.todolist.todolist.service;

import com.todolist.todolist.entity.TodoList;
import com.todolist.todolist.errors.ListIncorrectNameException;
import com.todolist.todolist.errors.ListNotFoundException;
import com.todolist.todolist.errors.ListUnsupportedFieldPatchException;
import com.todolist.todolist.repository.TodoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class TodoListService {

    private TodoListRepository todoListRepository;

    @Autowired
    public TodoListService(TodoListRepository todoListRepository){
        this.todoListRepository = todoListRepository;
    }

    public Page<TodoList> getAllLists(int page){
        return todoListRepository.findAll(PageRequest.of(page, 10));
    }

    public TodoList getListById(UUID id){
        return todoListRepository.findById(id).orElseThrow(() -> new ListNotFoundException(id));
    }

    public TodoList createList(TodoList todoList){
        return todoListRepository.save(todoList);
    }

    public TodoList updateListNameById(Map<String, String> updateData, UUID id){
        return todoListRepository.findById(id).map(x -> {
            if (!updateData.containsKey("name")){
                throw new ListUnsupportedFieldPatchException(updateData.keySet());
            }
            String name = updateData.get("name");
            if (!name.trim().isEmpty()) {
                x.setName(name);
                return todoListRepository.save(x);
            } else {
                throw new ListIncorrectNameException();
            }
        }).orElseThrow(() -> new ListNotFoundException(id));
    }

    public void deleteListById(UUID id){
        todoListRepository.deleteById(id);
    }


}
