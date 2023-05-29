package com.sanhaehong.project.techview.domain.answer;

import com.sanhaehong.project.techview.domain.BaseTimeEntity;
import com.sanhaehong.project.techview.domain.question.Question;
import com.sanhaehong.project.techview.domain.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity(name = "answers")
public class Answer extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User writer;

    public void update(String content) {
        this.content = content;
        this.question.getAnswers().remove(this);
    }

    @Builder
    public Answer(String content, Question question, User writer) {
        this.content = content;
        this.question = question;
        this.writer = writer;
    }
}
