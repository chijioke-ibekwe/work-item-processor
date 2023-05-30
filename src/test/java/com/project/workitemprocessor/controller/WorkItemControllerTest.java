package com.project.workitemprocessor.controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.project.workitemprocessor.dto.CreateWorkItemDTO;
import com.project.workitemprocessor.dto.IdDTO;
import com.project.workitemprocessor.service.WorkItemService;
import com.project.workitemprocessor.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WorkItemController.class)
class WorkItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WorkItemService workItemService;

    private final TestUtil testUtil = new TestUtil();

    private final ObjectMapper objectMapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT).setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);;


    @Test
    void testCreateWorkItem() throws Exception {

        IdDTO response = new IdDTO("aQweFGHDwdsefSvbbg");

        when(workItemService.createWorkItem(any())).thenReturn(response);

        this.mockMvc.perform(post("/api/v1/work-items")
                .content(objectMapper.writeValueAsString(CreateWorkItemDTO.builder().value(8)))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESSFUL"))
                .andExpect(jsonPath("$.message").value("Work Item created successfully"))
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.data.id").value("aQweFGHDwdsefSvbbg"));

    }

    @Test
    void testCreateWorkItem_whenValueIsInvalid() throws Exception {

        this.mockMvc.perform(post("/api/v1/work-items")
                        .content(objectMapper.writeValueAsString(CreateWorkItemDTO.builder().value(20)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.status").value("FAILED"))
                .andExpect(jsonPath("$.message").value("Value must not be greater than 10"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testGetWorkItem() throws Exception {

        when(workItemService.getWorkItem(any())).thenReturn(testUtil.getWorkItem());

        this.mockMvc.perform(get("/api/v1/work-items/{itemId}", "aQweFGHDwdsefSvbbg"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESSFUL"))
                .andExpect(jsonPath("$.data.id").value("aQweFGHDwdsefSvbbg"))
                .andExpect(jsonPath("$.data.value").value(4))
                .andExpect(jsonPath("$.data.status").value("COMPLETED"))
                .andExpect(jsonPath("$.data.result").value(16));
    }

    @Test
    void testDeleteWorkItem() throws Exception {

        this.mockMvc.perform(delete("/api/v1/work-items/{itemId}", "aQweFGHDwdsefSvbbg"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESSFUL"))
                .andExpect(jsonPath("$.message").value("Work Item deleted successfully"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testGetWorkItemReports() throws Exception {

        when(workItemService.getWorkItemReports()).thenReturn(testUtil.getWorkItemReports());

        this.mockMvc.perform(get("/api/v1/work-items/report"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESSFUL"))
                .andExpect(jsonPath("$.data[0].value").value(4))
                .andExpect(jsonPath("$.data[0].numberOfWorkItems").value(5))
                .andExpect(jsonPath("$.data[0].numberOfProcessedWorkItems").value(3));
    }
}