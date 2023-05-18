package com.sanhaehong.project.techview.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MockExamController {

    @GetMapping("/mockexam")
    public String mockExam() {
        return "mockexam/mockexam";
    }
}
