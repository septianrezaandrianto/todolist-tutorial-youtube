package com.app.todolist_be.controllers;

import com.app.todolist_be.dtos.GeneralResponse;
import com.app.todolist_be.dtos.PaginationRequest;
import com.app.todolist_be.dtos.TodoDto;
import com.app.todolist_be.services.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping("/add")
    ResponseEntity<?> add(@Valid @RequestBody TodoDto todoDto) {
        return ResponseEntity.ok(todoService.add(todoDto));
    }

    @PostMapping("/paginate")
    ResponseEntity<?> getDataPaging(@RequestBody PaginationRequest paginationRequest) {
        return ResponseEntity.ok(todoService.getDataPaging(paginationRequest));
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<?> delete(@PathVariable(name = "id")String id) {
        return ResponseEntity.ok(todoService.delete(id));
    }

    @PutMapping("/update/{id}")
    ResponseEntity<?> update(@PathVariable(name = "id") String id) {
        return ResponseEntity.ok(todoService.update(id));
    }
}
