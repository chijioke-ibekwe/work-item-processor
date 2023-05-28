package com.project.workitemprocessor.config;

import com.project.workitemprocessor.config.property.RabbitmqProperties;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class RabbitmqConfig {

    private final ConnectionFactory connectionFactory;

    private final RabbitmqProperties rabbitmqProperties;

    @Bean
    public AmqpTemplate amqpTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jacksonConverter());
        return rabbitTemplate;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jacksonConverter());
        factory.setMaxConcurrentConsumers(rabbitmqProperties.getMaxConcurrentConsumers());

        return factory;
    }

    @Bean
    public MessageConverter jacksonConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public DirectExchange internalDirectExchange() {
        return new DirectExchange(rabbitmqProperties.getExchanges().getInternal());
    }

    @Bean
    public Queue workItemQueue() {

        return QueueBuilder.durable(rabbitmqProperties.getQueues().getWorkItem())
                .withArgument("x-dead-letter-exchange", rabbitmqProperties.getExchanges().getInternalDl())
                .withArgument("x-dead-letter-routing-key", rabbitmqProperties.getRoutingKeys()
                        .getInternalDlWorkItemDl())
                .build();
    }

    @Bean
    public Binding internalToWorkItemBinding() {
        return BindingBuilder
                .bind(workItemQueue())
                .to(internalDirectExchange())
                .with(rabbitmqProperties.getRoutingKeys().getInternalWorkItem());
    }

    @Bean
    public DirectExchange internalDeadLetterDirectExchange() {
        return new DirectExchange(rabbitmqProperties.getExchanges().getInternalDl());
    }

    @Bean
    public Queue workItemDeadLetterQueue() {
        return QueueBuilder.durable(rabbitmqProperties.getQueues().getWorkItemDl()).build();
    }

    @Bean
    public Binding internalDeadLetterToWorkItemDeadLetterBinding() {
        return BindingBuilder
                .bind(workItemDeadLetterQueue())
                .to(internalDeadLetterDirectExchange())
                .with(rabbitmqProperties.getRoutingKeys().getInternalDlWorkItemDl());
    }
}
