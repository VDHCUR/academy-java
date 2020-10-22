package com.todolist.todolist.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Представляет класс ошибки, связанной с отправкой пустого тела в запросе
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EmptyRequestBodyException extends RuntimeException {
    /**
     * Конструктор ошибки
     */
    public EmptyRequestBodyException(){
        super("Request body for this request should not be empty");
    }
}
