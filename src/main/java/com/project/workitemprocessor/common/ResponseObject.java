package com.project.workitemprocessor.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseObject <T>{

    private ResponseStatus status;

    private String message;

    private T data;
}
