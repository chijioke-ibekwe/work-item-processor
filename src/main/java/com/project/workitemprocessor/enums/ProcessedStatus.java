package com.project.workitemprocessor.enums;

public enum ProcessedStatus {

    PROCESSING("Processing"),

    COMPLETED("Completed");

    private String value;

    ProcessedStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
