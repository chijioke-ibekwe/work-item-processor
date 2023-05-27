package com.project.workitemprocessor.common;


public enum ResponseStatus {

    SUCCESSFUL("Successful"),

    FAILED("Failed");

    private final String value;


    ResponseStatus(String value)
    {
        this.value = value;
    }


    public String getValue()
    {
        return value;
    }
}
