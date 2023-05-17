package com.sanhaehong.project.techview.service;

import com.sanhaehong.project.techview.domain.question.Category;
import com.sanhaehong.project.techview.domain.question.Question;
import com.sanhaehong.project.techview.dto.AddQuestionDto;
import com.sanhaehong.project.techview.dto.FindQuestionDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class QuestionServiceTest extends ServiceTest{

    @Autowired
    QuestionService questionService;

    @Test
    @DisplayName("유효한 질문 아이디라면 해당하는 질문을 찾고, 그렇지 않다면 오류를 던짐")
    void findQuestion() {
        assertThat(questionService.findQuestion(1L).getContent()).isEqualTo("가장 기억에 남는 정렬 알고리즘 1개를 골라 설명해보세요");
        assertThatThrownBy(() -> questionService.findQuestion(100L)).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("유효한 질문이라면 해당하는 답변을 찾고, 그렇지 않다면 오류를 던짐")
    void findAnswers() {
        assertThat(questionService.findAnswers(1L).size()).isEqualTo(2);
        assertThatThrownBy(() -> questionService.findAnswers(100L)).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("전체 질문 페이지로 받아오기")
    void findPageAll() {
        int pageSize = 10;
        Page<Question> page = questionService.findPageAll(PageRequest.of(0, pageSize));
        assertThat(page.getTotalPages()).isEqualTo(2);
        assertThat(page.getTotalElements()).isEqualTo(19);
    }

    @Test
    @DisplayName("검색한 질문 페이지로 받아오기")
    void findPage() {
        int pageSize = 10;
        Page<Question> page = questionService.findPage(new FindQuestionDto("", Category.OPERATION_SYSTEM), PageRequest.of(0, pageSize));
        assertThat(page.getTotalPages()).isEqualTo(1);
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