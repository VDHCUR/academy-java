package com.todolist.todolist.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

/**
 * Представляет класс ошибки, связанной с отсутствием ресурса с указанным идентификатором
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException{

    /**
     * Конструктор ошибки
     * @param entityName - название ресурса
     * @param id - указанный идентификатор
     */
    public NotFoundException(String entityName, UUID id){
        super(entityName + " id not found: " + id.toString());
    }

    public NotFoundException(String entityName, String propertyName, String property){
        super(entityName + " with this " + propertyName + " not found: " + property);
    }

}
