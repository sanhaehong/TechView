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
@Entity(name = "mockexamhistories")
@Getter
public class MockExamHistory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mockexamhistory_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User taker;

    @ManyToOne
    @JoinColumn(name = "mockexam_id")
    private MockExam mockExam;

    @OneToMany(mappedBy = "mockExamHistory")
    private List<MockExamAnswer> mockExamAnswers = new ArrayList<>();

    @Builder
    public MockExamHistory(User taker, MockExam mockExam) {
        this.taker = taker;
        this.mockExam = mockExam;
    }
}
