package com.project.workitemprocessor.repository;

import com.project.workitemprocessor.config.MongoConfig;
import com.project.workitemprocessor.dto.WorkItemReportDTO;
import com.project.workitemprocessor.entity.WorkItem;
import com.project.workitemprocessor.enums.ProcessedStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@ContextConfiguration(classes = WorkItemReportRepositoryImpl.class)
class WorkItemReportRepositoryTest {

    @Autowired
    private WorkItemReportRepository workItemReportRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void testGroupWorkItemsByValue() {

        WorkItem itemOne = WorkItem.builder().value(5).status(ProcessedStatus.COMPLETED).result(25).build();
        WorkItem itemTwo = WorkItem.builder().value(8).status(ProcessedStatus.PROCESSING).result(null).build();
        WorkItem itemThree = WorkItem.builder().value(5).status(ProcessedStatus.PROCESSING).result(null).build();
        WorkItem itemFour = WorkItem.builder().value(8).status(ProcessedStatus.COMPLETED).result(64).build();
        WorkItem itemFive = WorkItem.builder().value(8).status(ProcessedStatus.COMPLETED).result(64).build();
        WorkItem itemSix = WorkItem.builder().value(5).status(ProcessedStatus.PROCESSING).result(null).build();

        Arrays.asList(itemOne, itemTwo, itemThree, itemFour, itemFive, itemSix)
                .forEach(r -> mongoTemplate.save(r, "work_item_test"));

        List<WorkItemReportDTO> result = workItemReportRepository.groupWorkItemsByValue();

        System.out.println(result);
        assertThat(result.size()).isEqualTo(2);

    }
}
