package com.sanhaehong.project.techview.dto;

import com.sanhaehong.project.techview.domain.answer.Answer;
import com.sanhaehong.project.techview.domain.question.Question;
import com.sanhaehong.project.techview.domain.user.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddAnswerDto {

    @NotBlank(message = "답변을 작성해주세요")
    private String content;

    public Answer toEntity(Question question, User writer) {
        return Answer.builder()
                .question(question)
                .content(content)
                .writer(writer)
                .build();
    }
}
