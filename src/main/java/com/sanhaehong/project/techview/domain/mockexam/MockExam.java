package com.sanhaehong.project.techview.domain.mockexam;

import com.sanhaehong.project.techview.domain.BaseTimeEntity;
import com.sanhaehong.project.techview.domain.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity(name = "mockexams")
@Getter
public class MockExam extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mockexam_id")
    private Long id;

    private String title;

    private String information;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User maker;

    @OneToMany(mappedBy = "mockexam")
    private List<MockExamQuestion> questions = new ArrayList<>();

    @Builder
    public MockExam(String title, String information, User maker) {
        this.title = title;
        this.information = information;
        this.maker = maker;
    }
}
