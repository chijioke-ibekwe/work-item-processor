package com.project.workitemprocessor.controller;

import com.project.workitemprocessor.common.ResponseObject;
import com.project.workitemprocessor.common.ResponseStatus;
import com.project.workitemprocessor.dto.CreateWorkItemDTO;
import com.project.workitemprocessor.dto.IdDTO;
import com.project.workitemprocessor.dto.WorkItemReportDTO;
import com.project.workitemprocessor.entity.WorkItem;
import com.project.workitemprocessor.service.WorkItemService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/work-items")
@AllArgsConstructor
public class WorkItemController {

    private final WorkItemService workItemService;

    @PostMapping
    public ResponseObject<IdDTO> createWorkItem(@RequestBody @Valid CreateWorkItemDTO dto) {
        log.info("Creating work item with DTO::{}", dto);

        return ResponseObject.<IdDTO>builder()
                .status(ResponseStatus.SUCCESSFUL)
                .message("Work Item created successfully")
                .data(workItemService.createWorkItem(dto))
                .build();

    }

    @GetMapping("/{itemId}")
    public ResponseObject<WorkItem> getWorkItem(@PathVariable("itemId") String itemId) {

        return ResponseObject.<WorkItem>builder()
                .status(ResponseStatus.SUCCESSFUL)
                .data(workItemService.getWorkItem(itemId))
                .build();

    }

    @DeleteMapping("/{itemId}")
    public ResponseObject<?> deleteWorkItem(@PathVariable("itemId") String itemId) {
        log.info("Deleting work item with id::{}", itemId);

        workItemService.deleteWorkItem(itemId);

        return ResponseObject.builder()
                .status(ResponseStatus.SUCCESSFUL)
                .message("Work Item deleted successfully")
                .build();

    }

    @GetMapping("/report")
    public ResponseObject<List<WorkItemReportDTO>> getWorkItemReports() {

        return ResponseObject.<List<WorkItemReportDTO>>builder()
                .status(ResponseStatus.SUCCESSFUL)
                .data(workItemService.getWorkItemReports())
                .build();

    }
}
