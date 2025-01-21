package com.app.todolist_be.services;

import com.app.todolist_be.entities.Todo;

public interface RabbitMQProducerService {
    void sendMessageDelay(Todo todo);
}
