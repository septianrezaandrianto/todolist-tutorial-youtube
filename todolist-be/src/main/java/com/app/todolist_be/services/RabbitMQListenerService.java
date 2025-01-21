package com.app.todolist_be.services;

import org.springframework.amqp.core.Message;

public interface RabbitMQListenerService {

    void consumeMessageWithDelay(Message message);
}
