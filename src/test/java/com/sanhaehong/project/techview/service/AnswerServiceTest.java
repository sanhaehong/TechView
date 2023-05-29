package com.sanhaehong.project.techview.service;

import com.sanhaehong.project.techview.domain.answer.Answer;
import com.sanhaehong.project.techview.domain.question.Category;
import com.sanhaehong.project.techview.domain.question.Question;
import com.sanhaehong.project.techview.domain.user.Role;
import com.sanhaehong.project.techview.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class AnswerServiceTest extends ServiceTest{

    @Autowired
    private AnswerService answerService;

    private User testUser;
    private Question testQuestion;
    private Answer testAnswer;


    @BeforeEach
    void testInit() {
        testUser = User.builder().name("철수").email("test1@gmail.com").picture("https://cdn-icons-png.flaticon.com/128/236/236831.png").role(Role.USER).build();
        userRepository.save(testUser);

        testQuestion = Question.builder().category(Category.ALGORITHM).content("질문").build();
        questionRepository.save(testQuestion);

        testAnswer = Answer.builder().content("답변").writer(testUser).question(testQuestion).build();
        answerRepository.save(testAnswer);
    }

    @Test
    @DisplayName("답변 추가")
    void addAnswer() {
        Answer answer = answerService.addAnswer(testUser.getId(), testQuestion.getId(), "추가한 답변");
        Answer addAnswer = answerRepository.findById(answer.getId()).get();
        assertThat(answer.getContent()).isEqualTo(addAnswer.getContent());
    }

    @Test
    @DisplayName("답변 수정")
    void updateAnswer() {
        answerService.updateAnswer(testAnswer.getId(), "수정한 답변");
        Answer updateAnswer = answerRepository.findById(testAnswer.getId()).get();
        assertThat(updateAnswer.getContent()).isEqualTo("수정한 답변");
    }

    @Test
    @DisplayName("답변 삭제")
    void removeAnswer() {
        answerService.deleteAnswer(testAnswer.getId());
        assertThat(testQuestion.getAnswers().contains(testAnswer)).isFalse();
    }

}
