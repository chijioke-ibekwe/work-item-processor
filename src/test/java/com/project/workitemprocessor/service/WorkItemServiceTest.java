package com.project.workitemprocessor.service;

import com.project.workitemprocessor.dto.CreateWorkItemDTO;
import com.project.workitemprocessor.dto.IdDTO;
import com.project.workitemprocessor.entity.WorkItem;
import com.project.workitemprocessor.enums.ProcessedStatus;
import com.project.workitemprocessor.event.WorkItemCreatedEvent;
import com.project.workitemprocessor.exception.WorkItemServerException;
import com.project.workitemprocessor.repository.WorkItemReportRepository;
import com.project.workitemprocessor.repository.WorkItemRepository;
import com.project.workitemprocessor.service.impl.WorkItemServiceImpl;
import com.project.workitemprocessor.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

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

    private final TestUtil testUtil = new TestUtil();

    @Test
    void testCreateWorkItem() {

        CreateWorkItemDTO dto = CreateWorkItemDTO.builder()
                .value(5)
                .build();

        ArgumentCaptor<WorkItem> workItemCaptor = ArgumentCaptor.forClass(WorkItem.class);

        when(workItemRepository.save(workItemCaptor.capture())).thenReturn(testUtil.getWorkItem());

        IdDTO result = workItemService.createWorkItem(dto);

        WorkItem workItemValue = workItemCaptor.getValue();

        assertThat(workItemValue.getValue()).isEqualTo(5);
        assertThat(workItemValue.getStatus()).isEqualTo(ProcessedStatus.PROCESSING);
        assertThat(workItemValue.getResult()).isEqualTo(null);
        assertThat(result.id()).isEqualTo(testUtil.getWorkItem().getId());
    }

    @Test
    void testDeleteWorkItem() {

        WorkItem workItem = testUtil.getWorkItem();
        workItem.setStatus(ProcessedStatus.PROCESSING);

        ArgumentCaptor<WorkItem> workItemCaptor = ArgumentCaptor.forClass(WorkItem.class);

        when(workItemRepository.findById(any())).thenReturn(Optional.of(workItem));

        workItemService.deleteWorkItem("frsadtw24564w22fc");

        verify(workItemRepository, times(1)).delete(workItemCaptor.capture());
        assertThat(workItemCaptor.getValue()).isEqualTo(workItem);
    }

    @Test
    void testDeleteWorkItem_whenWorkItemStatusIsCompleted() {

        when(workItemRepository.findById(any())).thenReturn(Optional.of(testUtil.getWorkItem()));

        assertThatThrownBy(() -> workItemService.deleteWorkItem("frsadtw24564w22fc"))
                .isInstanceOf(WorkItemServerException.class)
                .hasMessage("Work Item with id frsadtw24564w22fc is already processed");
    }
}
