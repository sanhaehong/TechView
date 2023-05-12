package com.sanhaehong.project.techview.dto;

import com.sanhaehong.project.techview.domain.question.Category;
import com.sanhaehong.project.techview.domain.question.Question;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QuestionFormDto {

    @NotBlank(message = "질문을 작성해주세요")
    private String content;

    @NotNull(message = "카테고리를 선택해주세요")
    private Category category;

    public Question toEntity() {
        return Question.builder()
                .category(category)
                .content(content)
                .build();
    }
}
