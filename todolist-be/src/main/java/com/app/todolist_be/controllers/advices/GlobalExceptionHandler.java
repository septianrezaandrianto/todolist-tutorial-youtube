package com.app.todolist_be.controllers.advices;

import com.app.todolist_be.dtos.GeneralResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GeneralResponse<?>> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errorList = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        return new ResponseEntity<>(mappingError(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(),
                errorList), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    private GeneralResponse<?> mappingError(int respnseCode, String responseMessage, List<String> errorList) {
        return GeneralResponse.builder()
                .responseCode(respnseCode)
                .responseMessage(responseMessage)
                .errorList(errorList)
                .build();
    }
}
