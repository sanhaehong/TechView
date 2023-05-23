package com.sanhaehong.project.techview.domain.mockexam;

import com.sanhaehong.project.techview.domain.question.Question;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Getter
public class MockExamQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "mockexam_id")
    private MockExam mockexam;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @Builder
    public MockExamQuestion(MockExam mockexam, Question question) {
        this.mockexam = mockexam;
        this.question = question;
    }
}
