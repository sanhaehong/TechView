package com.sanhaehong.project.techview.domain.mockexam;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity(name = "mockexamanswers")
@Getter
public class MockExamAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mockexamanswer_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "mockexamhistory_id")
    private MockExamHistory mockExamHistory;

    @ManyToOne
    @JoinColumn(name = "mockexamquestion_id")
    private MockExamQuestion mockExamQuestion;

    @Column(nullable = false)
    private String answerIndexDBId;

    @Builder
    public MockExamAnswer(MockExamHistory mockExamHistory, MockExamQuestion mockExamQuestion, String answerIndexDBId) {
        this.mockExamHistory = mockExamHistory;
        this.mockExamQuestion = mockExamQuestion;
        this.answerIndexDBId = answerIndexDBId;
    }
}
