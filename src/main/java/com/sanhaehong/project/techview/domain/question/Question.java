package com.sanhaehong.project.techview.domain.question;

import com.sanhaehong.project.techview.domain.BaseTimeEntity;
import com.sanhaehong.project.techview.domain.answer.Answer;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity(name = "questions")
public class Question extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(length = 200, nullable = false)
    private String content;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Answer> answers = new ArrayList<>();

    @Builder
    public Question(Category category, String content) {
        this.category = category;
        this.content = content;
    }
}
