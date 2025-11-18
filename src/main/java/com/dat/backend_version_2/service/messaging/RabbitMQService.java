package com.dat.backend_version_2.service.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service wrapper for RabbitMQ operations with comprehensive error handling
 * Ensures that RabbitMQ errors are properly propagated and don't cause undefined behavior
 */
@Service
@Slf4j
public class RabbitMQService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * Sends a message to RabbitMQ with proper error handling
     *
     * @param exchange the exchange to send to
     * @param routingKey the routing key
     * @param message the message to send
     * @param headers additional headers
     * @throws RabbitMQException if sending fails
     */
    public void sendMessage(String exchange, String routingKey, Object message,
                           java.util.Map<String, Object> headers) throws RabbitMQException {
        try {
            log.debug("Sending message to exchange: {}, routingKey: {}, message: {}",
                     exchange, routingKey, message);

            if (headers != null && !headers.isEmpty()) {
                rabbitTemplate.convertAndSend(exchange, routingKey, message, messagePostProcessor -> {
                    headers.forEach((key, value) -> messagePostProcessor.getMessageProperties()
                            .getHeaders().put(key, value));
                    return messagePostProcessor;
                });
            } else {
                rabbitTemplate.convertAndSend(exchange, routingKey, message);
            }

            log.debug("Successfully sent message to RabbitMQ");

        } catch (AmqpException e) {
            log.error("Failed to send message to RabbitMQ. Exchange: {}, RoutingKey: {}, Error: {}",
                     exchange, routingKey, e.getMessage(), e);
            throw new RabbitMQException("Failed to send message to RabbitMQ: " + e.getMessage(), e);
        } catch (Exception e) {
            log.error("Unexpected error while sending message to RabbitMQ. Exchange: {}, RoutingKey: {}, Error: {}",
                     exchange, routingKey, e.getMessage(), e);
            throw new RabbitMQException("Unexpected error sending message to RabbitMQ: " + e.getMessage(), e);
        }
    }

    /**
     * Sends a message to RabbitMQ without additional headers
     */
    public void sendMessage(String exchange, String routingKey, Object message) throws RabbitMQException {
        sendMessage(exchange, routingKey, message, null);
    }

    /**
     * Custom exception for RabbitMQ operations
     */
    public static class RabbitMQException extends Exception {
        public RabbitMQException(String message) {
            super(message);
        }

        public RabbitMQException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
