package com.sanhaehong.project.techview.service;

import com.sanhaehong.project.techview.domain.answer.Answer;
import com.sanhaehong.project.techview.domain.question.Category;
import com.sanhaehong.project.techview.domain.question.Question;
import com.sanhaehong.project.techview.dto.AddQuestionDto;
import com.sanhaehong.project.techview.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class QuestionService {

    private final QuestionRepository questionRepository;

    public Question findQuestion(Long id) {
        return questionRepository.findById(id).orElseThrow(() -> new NoSuchElementException("질문이 존재하지 않습니다"));
    }

    public List<Answer> findAnswers(Long id) {
        return findQuestion(id).getAnswers();
    }

    public Page<Question> findPageAll(Pageable pageable) {
        return questionRepository.findAll(pageable);
    }

    public Page<Question> findPage(String content, Category category, Pageable pageable) {
        return questionRepository.findByContentAndCategory(content, category, pageable);
    }

    @Transactional
    public Question save(AddQuestionDto addQuestionDto) {
        return questionRepository.save(addQuestionDto.toEntity());
    }

}
