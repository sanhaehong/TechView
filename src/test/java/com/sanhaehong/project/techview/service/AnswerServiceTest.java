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


    @BeforeEach
    void testInit() {
        testUser = User.builder().name("철수").email("test1@gmail.com").picture("https://cdn-icons-png.flaticon.com/128/236/236831.png").role(Role.USER).build();
        userRepository.save(testUser);

        testQuestion = Question.builder().category(Category.ALGORITHM).content("가장 기억에 남는 정렬 알고리즘 1개를 골라 설명해보세요").build();
        questionRepository.save(testQuestion);

    }

    @Test
    @DisplayName("답변 추가하기")
    void addAnswer() {
        Answer testAnswer = answerRepository.save(Answer.builder().writer(testUser).question(testQuestion)
                .content("Heap 정렬은 최대/최소 힙 트리를 사용하여 요소를 정렬하는 알고리즘입니다.\n" +
                        "n개의 요소들로 힙 트리를 구성한 후, 요소를 순차적으로 제거하여 정렬된 배열을 생성합니다.\n" +
                        "힙 정렬의 시간 복잡도는 최상/최악의 경우 모두 O(n log n) 입니다.").build());
        answerRepository.flush();
        assertThat(answerRepository.findAll().get(0).getId()).isEqualTo(testAnswer.getId());
    }

}
