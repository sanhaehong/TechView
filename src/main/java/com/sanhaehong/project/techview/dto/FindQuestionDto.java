package com.sanhaehong.project.techview.dto;

import com.sanhaehong.project.techview.domain.question.Category;
import lombok.Data;

@Data
public class FindQuestionDto {

    private final String content;
    private final Category category;
}
