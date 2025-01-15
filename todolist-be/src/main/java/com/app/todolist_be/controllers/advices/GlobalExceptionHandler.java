package com.app.todolist_be.controllers.advices;

import com.app.todolist_be.dtos.GeneralResponse;
import com.app.todolist_be.handlers.DataExistException;
import com.app.todolist_be.handlers.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
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

    @ExceptionHandler(DataExistException.class)
    public ResponseEntity<?> handleDataExistException(DataExistException ex) {
        List<String> errorList = Collections.singletonList(ex.getMessage());
        return new ResponseEntity<>(mappingError(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT.getReasonPhrase(),
                errorList), new HttpHeaders(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException ex) {
        List<String> errorList = Collections.singletonList(ex.getMessage());
        return new ResponseEntity<>(mappingError(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(),
                errorList), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    private GeneralResponse<?> mappingError(int respnseCode, String responseMessage, List<String> errorList) {
        return GeneralResponse.builder()
                .responseCode(respnseCode)
                .responseMessage(responseMessage)
                .errorList(errorList)
                .build();
    }
}
