package com.sanhaehong.project.techview.domain.mockexam;

import com.sanhaehong.project.techview.domain.user.User;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity(name = "mockexams")
public class MockExam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mockexam_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User maker;

    @OneToMany(mappedBy = "mockexam")
    private List<MockExamQuestion> questions = new ArrayList<>();


}
