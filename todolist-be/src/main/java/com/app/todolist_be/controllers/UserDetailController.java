package com.app.todolist_be.controllers;

import com.app.todolist_be.dtos.GeneralResponse;
import com.app.todolist_be.dtos.UserDetailDto;
import com.app.todolist_be.services.UserDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-detail")
@RequiredArgsConstructor
public class UserDetailController {

    private final UserDetailService userDetailService;

    @PostMapping("/login")
    ResponseEntity<GeneralResponse<?>> login(@Valid @RequestBody UserDetailDto userDetailDto) {
        return ResponseEntity.ok(userDetailService.login(userDetailDto));
    }
}
