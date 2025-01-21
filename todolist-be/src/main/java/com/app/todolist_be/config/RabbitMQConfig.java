package com.app.todolist_be.config;

import com.app.todolist_be.constants.AppConstant;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Exchange delayedExchange() {
        return ExchangeBuilder
                .directExchange(AppConstant.RabbitMQ.EXCHANGE_NAME)
                .durable(true)
                .withArgument("x-delayed-type", "direct")
                .build();
    }

    @Bean
    public Queue delayedQueue() {
        return QueueBuilder.durable(AppConstant.RabbitMQ.QUEUE_NAME).build();
    }

    @Bean
    public Binding binding(Queue delayedQueue, Exchange delayedExchange) {
        return BindingBuilder.bind(delayedQueue)
                .to(delayedExchange)
                .with(AppConstant.RabbitMQ.QUEUE_NAME)
                .noargs();
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        connectionFactory.setPort(8001);
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }
}
