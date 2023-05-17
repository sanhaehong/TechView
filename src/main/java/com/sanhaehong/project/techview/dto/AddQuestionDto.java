package com.sanhaehong.project.techview.dto;

import com.sanhaehong.project.techview.domain.question.Category;
import com.sanhaehong.project.techview.domain.question.Question;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddQuestionDto {

    @NotBlank(message = "질문을 작성해주세요")
    private final String content;

    private final Category category;

    public Question toEntity() {
        return Question.builder()
                .category(category)
                .content(content)
                .build();
    }
}
