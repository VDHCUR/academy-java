package com.todolist.todolist.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Set;

/**
 * Представляет класс ошибки, связанной с попыткой изменить несуществующее или неизменяемое свойство
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UnsupportedFieldPatchException extends RuntimeException {
    /**
     * Конструктор ошибки
     * @param keys - свойства, которые пытались изменить
     */
    public UnsupportedFieldPatchException(Set<String> keys) {
        super("Field " + keys.toString() + " update is not allow.");
    }
}
