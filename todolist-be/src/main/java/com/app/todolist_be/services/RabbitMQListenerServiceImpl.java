package com.app.todolist_be.services;

import com.app.todolist_be.constants.AppConstant;
import com.app.todolist_be.dtos.Msg;
import com.app.todolist_be.dtos.WaGatewayRequest;
import com.app.todolist_be.entities.Todo;
import com.app.todolist_be.repositories.TodoRepository;
import com.app.todolist_be.rests.WaGatewayRest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitMQListenerServiceImpl implements RabbitMQListenerService {

    private final TodoRepository todoRepository;
    private final WaGatewayRest waGatewayRest;

    @Override
    @RabbitListener(queues = AppConstant.RabbitMQ.QUEUE_NAME)
    public void consumeMessageWithDelay(Message message) {
        String receivedMessage = new String(message.getBody());
        processMessage(receivedMessage);
    }

    @SneakyThrows
    private void processMessage(String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        Msg msg = objectMapper.readValue(message, Msg.class);
        String waNumber = msg.getWaNumber().replaceFirst("^\\+62", "0");
        WaGatewayRequest waGatewayRequest = WaGatewayRequest.builder()
                .countryCode("62")
                .target(waNumber)
                .build();

        Optional<Todo> existTodo =todoRepository.findById(msg.getId());
        if (existTodo.isPresent() && existTodo.get().getStatus().equals(AppConstant.Status.CREATED)) {
            if (msg.getRemark().equals(AppConstant.Status.DONE)) {
                Date now = Date.from(LocalDateTime.now().atZone(ZoneId.of("UTC")).toInstant());
                existTodo.get().setStatus(AppConstant.Status.DONE);
                existTodo.get().setModifiedDate(now);
                todoRepository.save(existTodo.get());
            }

            try {
                waGatewayRequest.setMessage(msg.getMessage());
                waGatewayRest.sendMessage(waGatewayRequest);
            } catch (Exception e) {}
        }
    }
}
