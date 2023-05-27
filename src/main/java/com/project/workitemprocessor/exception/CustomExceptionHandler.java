package com.project.workitemprocessor.exception;

import com.project.workitemprocessor.common.ResponseObject;
import com.project.workitemprocessor.common.ResponseStatus;
import com.project.workitemprocessor.controller.WorkItemController;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackageClasses = WorkItemController.class)
public class CustomExceptionHandler {

    @ExceptionHandler(value = WorkItemServerException.class)
    public ResponseEntity<ResponseObject<?>> handleWorkItemServerException(WorkItemServerException e) {

        log.error(e.getMessage(), e);

        ResponseObject<?> response =  ResponseObject.builder()
                .status(ResponseStatus.FAILED)
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(value = WorkItemNotFoundException.class)
    public ResponseEntity<ResponseObject<?>> handleWorkItemNotFoundException(WorkItemNotFoundException e) {

        log.error(e.getMessage(), e);

        ResponseObject<?> response = ResponseObject.builder()
                .status(ResponseStatus.FAILED)
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<ResponseObject<?>> handleConstraintViolationException(ConstraintViolationException e) {

        log.error(e.getMessage(), e);

        ResponseObject<?> response = ResponseObject.builder()
                .status(ResponseStatus.FAILED)
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseObject<?>> handleInvalidMethodArgumentException(MethodArgumentNotValidException e) {

        log.error(e.getMessage(), e);

        ResponseObject<?> response = ResponseObject.builder()
                .status(ResponseStatus.FAILED)
                .message(e.getBindingResult().getAllErrors().get(0).getDefaultMessage())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ResponseObject<?>> handleGenericException(Exception e) {

        log.error(e.getMessage(), e);

        ResponseObject<?> response = ResponseObject.builder()
                .status(ResponseStatus.FAILED)
                .message("An error occurred while processing your request. Please try again")
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
