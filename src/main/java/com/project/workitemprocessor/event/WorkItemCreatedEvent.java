package com.project.workitemprocessor.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class WorkItemCreatedEvent extends ApplicationEvent {

    private final String workItemId;

    public WorkItemCreatedEvent(String workItemId) {
        super(workItemId);
        this.workItemId = workItemId;
    }
}
