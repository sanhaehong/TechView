package com.sanhaehong.project.techview.dto;

import com.sanhaehong.project.techview.domain.question.Category;
import com.sanhaehong.project.techview.domain.question.Question;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class QuestionFormDto {

    @NotBlank(message = "질문을 작성해주세요")
    private String content;

    private Category category;

    public Question toEntity() {
        return Question.builder()
                .category(category)
                .content(content)
                .build();
    }
}
