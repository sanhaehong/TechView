package com.sanhaehong.project.techview.domain.mockexam;

import com.sanhaehong.project.techview.domain.question.Question;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
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
}
