package com.project.workitemprocessor.consumer;

import com.project.workitemprocessor.service.WorkItemService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class WorkItemConsumer {

    private final WorkItemService workItemService;

    @RabbitListener(queues = "${rabbitmq.queues.work-item}")
    public void consumeWorkItemCreatedEvent(@Payload String payload) {

        log.debug("Received work item created event with payload::{}", payload);

    }
}
