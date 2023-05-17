package com.sanhaehong.project.techview.dto;

import com.sanhaehong.project.techview.domain.question.Category;
import lombok.Data;

@Data
public class FindQuestionDto {

    private String content;

    private Category category;
}
