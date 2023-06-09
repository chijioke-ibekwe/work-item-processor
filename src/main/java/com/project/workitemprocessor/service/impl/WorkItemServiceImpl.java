package com.project.workitemprocessor.service.impl;

import com.project.workitemprocessor.enums.ProcessedStatus;
import com.project.workitemprocessor.event.WorkItemCreatedEvent;
import com.project.workitemprocessor.exception.WorkItemNotFoundException;
import com.project.workitemprocessor.exception.WorkItemServerException;
import com.project.workitemprocessor.dto.CreateWorkItemDTO;
import com.project.workitemprocessor.dto.IdDTO;
import com.project.workitemprocessor.dto.WorkItemReportDTO;
import com.project.workitemprocessor.entity.WorkItem;
import com.project.workitemprocessor.repository.WorkItemReportRepository;
import com.project.workitemprocessor.repository.WorkItemRepository;
import com.project.workitemprocessor.service.WorkItemService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class WorkItemServiceImpl implements WorkItemService {

    @Autowired
    private final WorkItemRepository workItemRepository;

    private final WorkItemReportRepository workItemReportRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Transactional
    public IdDTO createWorkItem(CreateWorkItemDTO dto) {

        WorkItem workItem = WorkItem.builder()
                .value(dto.getValue())
                .status(ProcessedStatus.PROCESSING)
                .build();

        WorkItem savedWorkItem = this.saveWorkItem(workItem);

        //emit work item event for processing
        this.applicationEventPublisher.publishEvent(new WorkItemCreatedEvent(savedWorkItem.getId()));

        return new IdDTO(savedWorkItem.getId());
    }

    @Override
    public WorkItem getWorkItem(String itemId) {

        return workItemRepository.findById(itemId)
                .orElseThrow(() -> new WorkItemNotFoundException(String.format("Work Item with id %s not found", itemId)));
    }

    @Override
    @Transactional
    public void deleteWorkItem(String itemId) {

        WorkItem workItem = this.getWorkItem(itemId);

        if(!workItem.getStatus().equals(ProcessedStatus.COMPLETED)) {
            workItemRepository.delete(workItem);
        } else {
            throw new WorkItemServerException(String.format("Work Item with id %s is already processed", itemId));
        }
    }

    @Override
    @Transactional
    public WorkItem saveWorkItem(WorkItem workItem) {

        return workItemRepository.save(workItem);
    }

    @Override
    public List<WorkItemReportDTO> getWorkItemReports() {

        return workItemReportRepository.groupWorkItemsByValue();
    }
}
