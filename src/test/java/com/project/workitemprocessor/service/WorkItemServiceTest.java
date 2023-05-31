package com.project.workitemprocessor.service;

import com.project.workitemprocessor.dto.CreateWorkItemDTO;
import com.project.workitemprocessor.repository.WorkItemReportRepository;
import com.project.workitemprocessor.repository.WorkItemRepository;
import com.project.workitemprocessor.service.impl.WorkItemServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = WorkItemServiceImpl.class)
class WorkItemServiceTest {

    @Autowired
    private WorkItemService workItemService;

    @MockBean
    private WorkItemReportRepository workItemReportRepository;

    @MockBean
    private ApplicationEventPublisher applicationEventPublisher;

    @MockBean
    private WorkItemRepository workItemRepository;

    @Test
    void testCreateWorkItem() {

        CreateWorkItemDTO dto = CreateWorkItemDTO.builder()
                .value(5)
                .build();


    }
}
