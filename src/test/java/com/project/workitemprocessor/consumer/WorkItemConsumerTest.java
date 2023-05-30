package com.project.workitemprocessor.consumer;

import com.project.workitemprocessor.dto.IdDTO;
import com.project.workitemprocessor.entity.WorkItem;
import com.project.workitemprocessor.enums.ProcessedStatus;
import com.project.workitemprocessor.service.WorkItemService;
import com.project.workitemprocessor.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WorkItemConsumer.class})
class WorkItemConsumerTest {

    @MockBean
    private WorkItemService workItemService;

    @Autowired
    private WorkItemConsumer workItemConsumer;

    private final TestUtil testUtil = new TestUtil();

    @Test
    void testConsumeWorkItemCreatedEvent() throws Exception {

        IdDTO dto = new IdDTO("aQweFGHDwdsefSvbbg");
        ArgumentCaptor<WorkItem> argumentCaptor = ArgumentCaptor.forClass(WorkItem.class);

        WorkItem workItem = testUtil.getWorkItem();
        workItem.setStatus(ProcessedStatus.PROCESSING);
        workItem.setResult(null);

        when(workItemService.getWorkItem(any())).thenReturn(workItem);

        workItemConsumer.consumeWorkItemCreatedEvent(dto);

        verify(workItemService, times(1)).saveWorkItem(argumentCaptor.capture());
        WorkItem result = argumentCaptor.getValue();

        assertThat(result.getStatus()).isEqualTo(ProcessedStatus.COMPLETED);
        assertThat(result.getResult()).isEqualTo(16);

    }
}