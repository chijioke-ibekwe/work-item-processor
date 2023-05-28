package com.project.workitemprocessor.config.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "rabbitmq")
@Data
public class RabbitmqProperties {

    private Exchanges exchanges;

    private Queues queues;

    private RoutingKeys routingKeys;

    private Integer maxConcurrentConsumers;

    @Data
    public static class Exchanges {

        private String internal;

        private String internalDl;
    }

    @Data
    public static class Queues {

        private String workItem;

        private String workItemDl;
    }

    @Data
    public static class RoutingKeys {

        private String internalWorkItem;

        private String internalDlWorkItemDl;
    }
}

