package com.sanhaehong.project.techview.domain.question;

import com.sanhaehong.project.techview.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity(name = "questions")
public class Question extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(length = 200, nullable = false)
    private String content;

    @Builder
    public Question(Category category, String content) {
        this.category = category;
        this.content = content;
    }
}
