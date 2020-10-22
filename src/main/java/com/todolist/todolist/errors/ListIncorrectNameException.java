package com.todolist.todolist.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Представляет класс ошибки, связанной с некорректным вводом имени
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ListIncorrectNameException extends RuntimeException {
    /**
     * Конструктор ошибки
     */
    public ListIncorrectNameException(){
        super("Name should not be blank");
    }
}
