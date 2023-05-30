package com.project.workitemprocessor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/work-item-report")
public class ReportViewController {

    @GetMapping
    public String getWorkItemReportPage(){

        return "report";
    }
}
