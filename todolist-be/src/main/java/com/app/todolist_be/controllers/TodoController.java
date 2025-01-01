package com.app.todolist_be.controllers;

import com.app.todolist_be.dtos.GeneralResponse;
import com.app.todolist_be.dtos.TodoDto;
import com.app.todolist_be.services.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping("/add")
    ResponseEntity<?> add(@Valid @RequestBody TodoDto todoDto) {
        return ResponseEntity.ok(todoService.add(todoDto));
    }
}
