package com.project.workitemprocessor.config;

import lombok.Getter;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class WorkItemProcessorConfig {

    @Value("${rabbitmq.exchanges.internal}")
    private String internalExchange;

    @Value("${rabbitmq.queues.work-item}")
    private String workItemQueue;

    @Value("${rabbitmq.routing-keys.internal-work-item}")
    private String internalWorkItemRoutingKey;

    @Bean
    public DirectExchange internalDirectExchange() {
        return new DirectExchange(this.internalExchange);
    }

    @Bean
    public Queue workItemQueue() {
        return new Queue(this.workItemQueue);
    }

    @Bean
    public Binding internalToWorkItemBinding() {
        return BindingBuilder
                .bind(workItemQueue())
                .to(internalDirectExchange())
                .with(this.internalWorkItemRoutingKey);
    }
}
