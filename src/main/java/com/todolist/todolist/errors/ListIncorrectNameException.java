package com.todolist.todolist.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Set;


@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ListIncorrectNameException extends RuntimeException {
    public ListIncorrectNameException(){
        super("Name should not be blank");
    }
}
