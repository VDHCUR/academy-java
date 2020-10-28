package com.todolist.todolist.errors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.stream.Collectors;


/**
 * Представляет класс, получающий ошибки контроллеров и возвращающий соответсвтующие сообщение, в зависимости от ошибки
 */
@ControllerAdvice()
public class TodoExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(InvalidRequestTypesException.class)
    protected ResponseEntity<Object> handleInvalidRequestTypes(InvalidRequestTypesException ex){
        return new ResponseEntity<>(
                new CustomErrorsResponse(new Date(),
                        HttpStatus.BAD_REQUEST.value(),
                        ex.getMessage(),
                        ex.getErrors()),
                HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<CustomErrorResponse> handleNotFound(Exception ex){
        return new ResponseEntity<>(
                new CustomErrorResponse(new Date(),
                        HttpStatus.NOT_FOUND.value(),
                        ex.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmptyRequestBodyException.class)
    protected ResponseEntity<CustomErrorResponse> handleEmptyRequestBody(Exception ex){
        return new ResponseEntity<>(
                new CustomErrorResponse(new Date(),
                        HttpStatus.BAD_REQUEST.value(),
                        ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request)
    {
        return new ResponseEntity<>(
                new CustomErrorResponse(new Date(),
                        status.value(),
                        "Request body is missing or not readable"),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex){
        return new ResponseEntity<>(
                new CustomErrorResponse(new Date(),
                        HttpStatus.BAD_REQUEST.value(),
                        ex.getName().substring(0,1).toUpperCase()
                                + ex.getName().substring(1)
                                + " should be "
                                + ex.getRequiredType().getSimpleName() + " type"),
                HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object>
    handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                 HttpHeaders headers,
                                 HttpStatus status, WebRequest request) {

        //Get all fields errors
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        return new ResponseEntity<>(
                new CustomErrorsResponse(new Date(),
                        HttpStatus.BAD_REQUEST.value(),
                        "Validation failed",
                        errors),
                HttpStatus.BAD_REQUEST);
    }

    private static String getCurrentUTCTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z", Locale.UK);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(new Date());
    }
}
