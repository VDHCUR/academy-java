package com.todolist.todolist.api;

import com.todolist.todolist.dto.AuthenticationRequestDTO;
import com.todolist.todolist.dto.AuthenticationResponseDTO;
import com.todolist.todolist.entity.User;
import com.todolist.todolist.security.jwt.JwtTokenProvider;
import com.todolist.todolist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("/auth/login")
    public AuthenticationResponseDTO login(@RequestBody AuthenticationRequestDTO requestDto) {
        try {
            String username = requestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));

            User user = userService.findByUsername(username);

            String token = jwtTokenProvider.createToken(username, user.getRoles());

            AuthenticationResponseDTO responseDTO = new AuthenticationResponseDTO();
            responseDTO.setUsername(username);
            responseDTO.setToken(token);

            return responseDTO;
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
