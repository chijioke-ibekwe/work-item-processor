package com.project.workitemprocessor.consumer;

import com.project.workitemprocessor.config.property.RabbitmqProperties;
import com.project.workitemprocessor.dto.IdDTO;
import com.project.workitemprocessor.entity.WorkItem;
import com.project.workitemprocessor.enums.ProcessedStatus;
import com.project.workitemprocessor.service.WorkItemService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@AllArgsConstructor
public class WorkItemConsumer {

    private final WorkItemService workItemService;

    @Transactional
    @RabbitListener(queues = "${rabbitmq.queues.work-item}")
    public void consumeWorkItemCreatedEvent(IdDTO payload) {

        log.info("Received work item created event with payload::{}", payload);

        WorkItem workItem = workItemService.getWorkItem(payload.id());
        Integer value = workItem.getValue();

        log.info("Processing work item with id {}", workItem.getId());

        //delay for some milliseconds
        try {
            Thread.sleep(value * 3000);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }

        //square the value of work item to generate result
        Integer result = value * value;

        workItem.setResult(result);
        workItem.setStatus(ProcessedStatus.COMPLETED);

        workItemService.saveWorkItem(workItem);
        log.info("Processed work item with id {}", workItem.getId());
    }

}
