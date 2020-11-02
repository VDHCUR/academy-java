package com.todolist.todolist.dto;

import lombok.Data;

@Data
public class AuthenticationResponseDTO {
    private String username;
    private String token;
}
