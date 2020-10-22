package com.todolist.todolist.errors;

import lombok.Getter;

import java.util.Date;
import java.util.List;

/**
 * Расширяет класс для изменённых сообщений об ошибке
 * Позволяет передать массив ошибок (напирмер, валидации)
 */
public class CustomErrorsResponse extends CustomErrorResponse{
    @Getter
    private List<String> errors;

    /**
     * Конструктор сообщения
     * @param timestamp - дата ошибки
     * @param status - код ошибки
     * @param error - общее описание ошибок
     * @param errors - массив ошибок
     */
    public CustomErrorsResponse(Date timestamp, int status, String error, List<String> errors) {
        super(timestamp, status, error);
        this.errors = errors;
    }

}
