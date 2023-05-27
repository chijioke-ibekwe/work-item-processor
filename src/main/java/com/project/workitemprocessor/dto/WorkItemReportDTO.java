package com.project.workitemprocessor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkItemReportDTO {

    private Integer value;

    private Integer numberOfWorkItems;

    private Integer numberOfProcessedWorkItems;
}
