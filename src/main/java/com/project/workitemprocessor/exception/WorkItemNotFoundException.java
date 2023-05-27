package com.project.workitemprocessor.exception;

public class WorkItemNotFoundException extends RuntimeException {

    public WorkItemNotFoundException(String message) {
        super(message);
    }
}
