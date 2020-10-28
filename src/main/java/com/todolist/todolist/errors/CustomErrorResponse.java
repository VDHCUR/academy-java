package com.todolist.todolist.errors;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * Представляет класс для изменённых сообщений об ошибке
 */
@Setter
@Getter
public class CustomErrorResponse implements Serializable {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss Z")
    private Date timestamp;
    private int status;
    private String message;

    /**
     * Конструктор сообщения
     * @param timestamp - дата ошибки
     * @param status - код ошибки
     * @param message - сообщение с описанием ошибки
     */
    public CustomErrorResponse(Date timestamp, int status, String message) {
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
    }
}
