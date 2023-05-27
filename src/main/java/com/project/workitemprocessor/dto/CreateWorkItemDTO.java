package com.project.workitemprocessor.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateWorkItemDTO {

    @NotNull(message = "Value is required")
    @Min(value = 1, message = "Value must not be less than 1")
    @Max(value = 10, message = "Value must not be greater than 10")
    private Integer value;
}
