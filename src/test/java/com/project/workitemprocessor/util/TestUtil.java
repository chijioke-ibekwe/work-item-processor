package com.project.workitemprocessor.util;

import com.project.workitemprocessor.dto.WorkItemReportDTO;
import com.project.workitemprocessor.entity.WorkItem;
import com.project.workitemprocessor.enums.ProcessedStatus;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestUtil {

    public WorkItem getWorkItem() {

        return WorkItem.builder()
                .id("aQweFGHDwdsefSvbbg")
                .value(4)
                .status(ProcessedStatus.COMPLETED)
                .result(16)
                .build();
    }

    public List<WorkItemReportDTO> getWorkItemReports() {

        return Collections.singletonList(WorkItemReportDTO.builder()
                .value(4)
                .numberOfWorkItems(5)
                .numberOfProcessedWorkItems(3)
                .build());
    }
}
