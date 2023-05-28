package com.project.workitemprocessor.service;

import com.project.workitemprocessor.dto.CreateWorkItemDTO;
import com.project.workitemprocessor.dto.IdDTO;
import com.project.workitemprocessor.dto.WorkItemReportDTO;
import com.project.workitemprocessor.entity.WorkItem;

import java.util.List;

public interface WorkItemService {

    IdDTO createWorkItem(CreateWorkItemDTO dto);

    WorkItem getWorkItem(String itemId);

    void deleteWorkItem(String itemId);

    List<WorkItemReportDTO> getWorkItemReports();

    WorkItem saveWorkItem(WorkItem workItem);
}
