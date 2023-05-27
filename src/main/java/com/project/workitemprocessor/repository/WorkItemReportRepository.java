package com.project.workitemprocessor.repository;

import com.project.workitemprocessor.dto.WorkItemReportDTO;

import java.util.List;

public interface WorkItemReportRepository {

    List<WorkItemReportDTO> groupWorkItemsByValue();
}
