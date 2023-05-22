package com.sanhaehong.project.techview.service;

import com.sanhaehong.project.techview.domain.mockexam.MockExam;
import com.sanhaehong.project.techview.domain.mockexam.MockExamQuestion;
import com.sanhaehong.project.techview.domain.question.Question;
import com.sanhaehong.project.techview.domain.user.User;
import com.sanhaehong.project.techview.repository.MockExamQuestionRepository;
import com.sanhaehong.project.techview.repository.MockExamRepository;
import com.sanhaehong.project.techview.repository.QuestionRepository;
import com.sanhaehong.project.techview.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MockExamService {

    private final MockExamRepository mockExamRepository;
    private final MockExamQuestionRepository mockExamQuestionRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;

    @Transactional
    public void addMockExam(Long userId, List<Long> questionIds) {
        List<Question> questions = questionIds.stream()
                .map(id -> questionRepository.findById(id)
                        .orElseThrow(() -> new NoSuchElementException("선택한 질문이 존재하지 않습니다")))
                .toList();
        User maker = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("모의고사 제작자 정보가 존재하지 않습니다"));
        MockExam mockExam = MockExam.builder().maker(maker).build();
        questions.stream()
                .map(question -> MockExamQuestion.builder().mockexam(mockExam).question(question).build())
                .forEach(mockExamQuestionRepository::save);
        mockExamRepository.save(mockExam);
    }
}
