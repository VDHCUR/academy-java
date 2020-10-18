package com.todolist.todolist.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ListNotFoundException extends RuntimeException{

    public ListNotFoundException(UUID id){
        super("List id not found: " + id.toString());
    }

}