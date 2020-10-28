package com.todolist.todolist.dto;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


@Data
public class TodoListRequestDTO implements Serializable {
    @NotBlank(message = "Field 'name' should not be blank")
    @Length(max = 255, message = "Name of the list should be 255 symbols or less")
    private String name;
}
