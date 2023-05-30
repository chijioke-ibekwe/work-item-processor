package com.project.workitemprocessor.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.project.workitemprocessor.dto.serializers.CustomLocalDateTimeSerializer;
import com.project.workitemprocessor.enums.ProcessedStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("work_items")
public class WorkItem {

    @Id
    private String id;

    @JsonIgnore
    @CreatedDate
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @Field(name = "created_on")
    protected LocalDateTime createdOn;

    @JsonIgnore
    @LastModifiedDate
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @Field(name = "updated_on")
    protected LocalDateTime updatedOn;

    private Integer value;

    private ProcessedStatus status;

    private Integer result;
}
