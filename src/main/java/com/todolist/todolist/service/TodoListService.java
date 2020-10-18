package com.todolist.todolist.service;

import com.todolist.todolist.entity.TodoList;
import com.todolist.todolist.errors.ListIncorrectNameException;
import com.todolist.todolist.errors.NotFoundException;
import com.todolist.todolist.errors.ListUnsupportedFieldPatchException;
import com.todolist.todolist.repository.TodoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class TodoListService {

    private final TodoListRepository todoListRepository;

    @Autowired
    public TodoListService(TodoListRepository todoListRepository){
        this.todoListRepository = todoListRepository;
    }

    public Page<TodoList> getAllLists(int page){
        return todoListRepository.findAll(PageRequest.of(page, 10));
    }

    public TodoList getListById(UUID id){
        return todoListRepository.findById(id).orElseThrow(() -> new NotFoundException("List", id));
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
        }).orElseThrow(() -> new NotFoundException("List", id));
    }

    public void deleteListById(UUID id){
        if (todoListRepository.existsById(id)){
            todoListRepository.deleteById(id);
        } else {
            throw new NotFoundException("List", id);
        }
    }
}
