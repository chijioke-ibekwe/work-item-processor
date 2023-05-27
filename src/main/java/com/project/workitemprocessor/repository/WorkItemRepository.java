package com.project.workitemprocessor.repository;

import com.project.workitemprocessor.entity.WorkItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkItemRepository extends MongoRepository<WorkItem, String> {
}
