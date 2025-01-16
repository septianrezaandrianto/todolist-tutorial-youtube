package com.app.todolist_be.controllers;

import com.app.todolist_be.dtos.WaGatewayRequest;
import com.app.todolist_be.rests.WaGatewayRest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wa")
@RequiredArgsConstructor
public class WaGatewayController {

    private final WaGatewayRest waGatewayRest;

    @PostMapping("/send-message")
    ResponseEntity<?> sendMessage(@Valid @RequestBody WaGatewayRequest waGatewayRequest) {
        return ResponseEntity.ok(waGatewayRest.sendMessage(waGatewayRequest));
    }
}
