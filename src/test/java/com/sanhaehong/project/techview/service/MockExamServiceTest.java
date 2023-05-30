package com.sanhaehong.project.techview.service;

import com.sanhaehong.project.techview.domain.mockexam.MockExam;
import com.sanhaehong.project.techview.domain.mockexam.MockExamQuestion;
import com.sanhaehong.project.techview.domain.question.Category;
import com.sanhaehong.project.techview.domain.question.Question;
import com.sanhaehong.project.techview.domain.user.Role;
import com.sanhaehong.project.techview.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MockExamServiceTest extends ServiceTest{

    private User testUser;
    private Question testQuestion;
    private MockExam testMockExam;

    @Autowired
    private MockExamService mockExamService;

    @BeforeEach
    void testInit() {
        testUser = User.builder().name("철수").email("test1@gmail.com").picture("https://cdn-icons-png.flaticon.com/128/236/236831.png").role(Role.USER).build();
        userRepository.save(testUser);

        testQuestion = Question.builder().category(Category.ALGORITHM).content("질문").build();
        questionRepository.save(testQuestion);
        
        testMockExam = MockExam.builder().title("모의고사").information("모의고사 입니다").maker(testUser).build();
        mockExamRepository.save(testMockExam);
        mockExamQuestionRepository.save(MockExamQuestion.builder().question(testQuestion).mockexam(testMockExam).build());
    }

    @Test
    @DisplayName("모의고사 생성")
    void addMockExam() {
        mockExamService.addMockExam(testUser.getId(), "모의고사", "모의고사 입니다", List.of(testQuestion.getId()));
        MockExam mockExam = mockExamService.findMockExamPageAll(Pageable.ofSize(10)).stream().findFirst().get();
        assertThat(mockExam.getTitle()).isEqualTo("모의고사");
        assertThat(mockExam.getInformation()).isEqualTo("모의고사 입니다");
    }
}
