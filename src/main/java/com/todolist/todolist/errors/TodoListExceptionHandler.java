package com.todolist.todolist.errors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice()
public class TodoListExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<CustomErrorResponse> handleListNotFound(Exception ex){
        return new ResponseEntity<>(
                new CustomErrorResponse(new Date(),
                        HttpStatus.NOT_FOUND.value(),
                        ex.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ListUnsupportedFieldPatchException.class)
    protected ResponseEntity<CustomErrorResponse> handleListUnsupportedFieldPatch(Exception ex){
        return new ResponseEntity<>(
                new CustomErrorResponse(new Date(),
                        HttpStatus.BAD_REQUEST.value(),
                        ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ListIncorrectNameException.class)
    protected ResponseEntity<Object> handleListIncorrectName(Exception ex){
        Map<String, Object> body = new LinkedHashMap<>();

        body.put("timestamp", getCurrentUTCTime());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        body.put("errors", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object>
    handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                 HttpHeaders headers,
                                 HttpStatus status, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();

        body.put("timestamp", getCurrentUTCTime());
        body.put("status", status.value());

        //Get all fields errors
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, headers, status);
    }

    private static String getCurrentUTCTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z", Locale.UK);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(new Date());
    }
}
