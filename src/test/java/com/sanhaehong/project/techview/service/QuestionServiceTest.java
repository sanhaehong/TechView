package com.sanhaehong.project.techview.service;

import com.sanhaehong.project.techview.domain.answer.Answer;
import com.sanhaehong.project.techview.domain.question.Category;
import com.sanhaehong.project.techview.domain.question.Question;
import com.sanhaehong.project.techview.domain.user.Role;
import com.sanhaehong.project.techview.domain.user.User;
import com.sanhaehong.project.techview.dto.AddQuestionDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class QuestionServiceTest extends ServiceTest {

    @Autowired
    private QuestionService questionService;

    private Question testQuestion;

    @BeforeEach
    void testInit() {
        User testUser = User.builder().name("철수").email("test1@gmail.com").picture("https://cdn-icons-png.flaticon.com/128/236/236831.png").role(Role.USER).build();
        userRepository.save(testUser);

        testQuestion = Question.builder().category(Category.ALGORITHM).content("가장 기억에 남는 정렬 알고리즘 1개를 골라 설명해보세요").build();
        questionRepository.save(testQuestion);
        questionRepository.save(Question.builder().category(Category.NETWORK).content("www.google.com 에 접속할 때 일어나는 일을 설명해보세요").build());
        questionRepository.save(Question.builder().category(Category.DATABASE).content("ACID가 뭔지 설명해보세요").build());
        questionRepository.save(Question.builder().category(Category.DATA_STRUCTURE).content("배열과 List의 차이점을 설명해보세요").build());
        questionRepository.save(Question.builder().category(Category.SECURITY).content("OAuth가 뭔지 설명해보세요").build());
        questionRepository.save(Question.builder().category(Category.JAVA).content("equals()와 ==는 어떤 차이점이 있는지 설명해보세요").build());
        questionRepository.save(Question.builder().category(Category.JAVA).content("JAVA의 GC가 뭔지 설명하고, 동작 방식에 대해 간단히 설명해보세요").build());
        questionRepository.save(Question.builder().category(Category.OPERATION_SYSTEM).content("캐시의 지역성이 뭔지 설명해보세요").build());
        questionRepository.save(Question.builder().category(Category.ALGORITHM).content("시간 복잡도와 공간 복잡도의 차이점을 설명해보세요").build());
        questionRepository.save(Question.builder().category(Category.DATA_STRUCTURE).content("해시 테이블의 작동 원리를 설명해보세요").build());
        questionRepository.save(Question.builder().category(Category.SECURITY).content("SQL 인젝션 공격이 무엇인지 설명해보세요").build());

        answerRepository.save(Answer.builder().writer(testUser).question(testQuestion)
                .content("Heap 정렬은 최대/최소 힙 트리를 사용하여 요소를 정렬하는 알고리즘입니다.\n" +
                        "n개의 요소들로 힙 트리를 구성한 후, 요소를 순차적으로 제거하여 정렬된 배열을 생성합니다.\n" +
                        "힙 정렬의 시간 복잡도는 최상/최악의 경우 모두 O(n log n) 입니다.").build());
    }

    @Test
    @DisplayName("질문이 존재하면 질문을 찾고, 그렇지 않다면 오류를 던짐")
    void findQuestion() {
        assertThat(questionService.findQuestion(testQuestion.getId()).getContent()).isEqualTo("가장 기억에 남는 정렬 알고리즘 1개를 골라 설명해보세요");
        assertThatThrownBy(() -> questionService.findQuestion(-1L)).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("질문이 존재하면 해당하는 답변을 찾고, 그렇지 않다면 오류를 던짐")
    void findAnswers() {
        assertThat(questionService.findAnswers(testQuestion.getId()).size()).isEqualTo(1);
        assertThatThrownBy(() -> questionService.findAnswers(-1L)).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("전체 질문 페이지로 받아오기")
    void findPageAll() {
        Page<Question> page = questionService.findPageAll(PageRequest.of(0, 10));
        System.out.println(page.getTotalElements());
        assertThat(page.getTotalElements()).isEqualTo(11);
    }

    @Test
    @DisplayName("카테고리로 검색한 질문 페이지로 받아오기")
    void findPageByCategory() {
        Page<Question> page = questionService.findPage(null, Category.OPERATION_SYSTEM, PageRequest.of(0, 10));
        assertThat(page.getTotalElements()).isEqualTo(1);
    }

    @Test
    @DisplayName("검색어로 검색한 질문 페이지로 받아오기")
    void findPageByName() {
        Page<Question> page = questionService.findPage("배열", null, PageRequest.of(0, 10));
        assertThat(page.getTotalElements()).isEqualTo(1);
    }

    @Test
    @DisplayName("카테고리, 검색어로 검색한 질문 페이지로 받아오기")
    void findPageByCategoryAndName() {
        Page<Question> page = questionService.findPage("JAVA", Category.JAVA, PageRequest.of(0, 10));
        assertThat(page.getTotalElements()).isEqualTo(1);
    }

    @Test
    @DisplayName("질문 저장")
    void save() {
        questionService.save(new AddQuestionDto("DHCP란 무엇인가요?", Category.NETWORK));
        int pageSize = 10;
        Page<Question> page = questionRepository.findByContentAndCategory("DHCP란 무엇인가요?", Category.NETWORK, PageRequest.of(0, pageSize));
        assertThat(page.getTotalElements()).isEqualTo(1);
    }
}