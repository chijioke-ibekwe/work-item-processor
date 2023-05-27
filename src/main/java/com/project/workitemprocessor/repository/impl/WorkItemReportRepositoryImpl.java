package com.project.workitemprocessor.repository.impl;

import com.project.workitemprocessor.dto.WorkItemReportDTO;
import com.project.workitemprocessor.entity.WorkItem;
import com.project.workitemprocessor.repository.WorkItemReportRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Repository
@AllArgsConstructor
public class WorkItemReportRepositoryImpl implements WorkItemReportRepository {

    public final MongoTemplate mongoTemplate;

    public List<WorkItemReportDTO> groupWorkItemsByValue() {

        ConditionalOperators.Cond condOperation = ConditionalOperators.when(Criteria.where("status").is("COMPLETED"))
                .then(1)
                .otherwise(0);

        GroupOperation groupOperation = group("value")
                .first("value").as("value")
                .count().as("numberOfWorkItems")
                .sum(condOperation).as("numberOfProcessedWorkItems");

        TypedAggregation<WorkItem> aggregation = Aggregation.newAggregation(WorkItem.class, groupOperation);

        return mongoTemplate.aggregate(aggregation, WorkItem.class, WorkItemReportDTO.class).getMappedResults();
    }

}
