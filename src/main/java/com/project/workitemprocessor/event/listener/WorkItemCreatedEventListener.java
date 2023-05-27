package com.project.workitemprocessor.event.listener;

import com.project.workitemprocessor.config.WorkItemProcessorConfig;
import com.project.workitemprocessor.dto.IdDTO;
import com.project.workitemprocessor.event.WorkItemCreatedEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@AllArgsConstructor
public class WorkItemCreatedEventListener {

    private final AmqpTemplate amqpTemplate;

    private final WorkItemProcessorConfig config;

    @Async
    @TransactionalEventListener(fallbackExecution = true)
    public void publishWorkItemCreatedEvent(WorkItemCreatedEvent event) {

        String exchange = config.getInternalExchange();
        String routingKey = config.getInternalWorkItemRoutingKey();
        IdDTO message = new IdDTO(event.getWorkItemId());

        log.info("Publishing to {} with routing key {}. Payload {}", exchange, routingKey, message);
        amqpTemplate.convertAndSend(exchange, routingKey, message);
        log.info("Published to {} with routing key {}. Payload {}", exchange, routingKey, message);
    }
}
