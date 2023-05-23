package com.sanhaehong.project.techview.service;

import com.sanhaehong.project.techview.domain.mockexam.MockExam;
import com.sanhaehong.project.techview.domain.mockexam.MockExamAnswer;
import com.sanhaehong.project.techview.domain.mockexam.MockExamHistory;
import com.sanhaehong.project.techview.domain.mockexam.MockExamQuestion;
import com.sanhaehong.project.techview.domain.question.Question;
import com.sanhaehong.project.techview.domain.user.User;
import com.sanhaehong.project.techview.repository.*;
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
public class MockExamService {

    private final MockExamRepository mockExamRepository;
    private final MockExamQuestionRepository mockExamQuestionRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final MockExamHistoryRepository mockExamHistoryRepository;
    private final MockExamAnswerRepository mockExamAnswerRepository;

    @Transactional
    public void addMockExam(Long userId, String title, String information, List<Long> questionIds) {
        List<Question> questions = questionIds.stream()
                .map(id -> questionRepository.findById(id)
                        .orElseThrow(() -> new NoSuchElementException("선택한 질문이 존재하지 않습니다")))
                .toList();
        User maker = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("모의고사 제작자 정보가 존재하지 않습니다"));
        MockExam mockExam = MockExam.builder().title(title).information(information).maker(maker).build();
        questions.stream()
                .map(question -> MockExamQuestion.builder().mockexam(mockExam).question(question).build())
                .forEach(mockExamQuestionRepository::save);
        mockExamRepository.save(mockExam);
    }

    public Page<MockExam> findMockExamPageAll(Pageable pageable) {
        return mockExamRepository.findAll(pageable);
    }

    public Page<MockExamHistory> findMockExamHistoryPageAll(Pageable pageable) { return mockExamHistoryRepository.findAll(pageable); }



    public MockExamHistory startMockExam(Long mockExamId, Long userId) {
        MockExam mockExam = mockExamRepository.findById(mockExamId)
                .orElseThrow(() -> new NoSuchElementException("모의고사 정보가 존재하지 않습니다"));
        User taker = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("유저 정보가 존재하지 않습니다"));
        MockExamHistory mockExamHistory = MockExamHistory.builder().mockExam(mockExam).taker(taker).build();
        mockExamHistoryRepository.save(mockExamHistory);
        return mockExamHistory;
    }

    public MockExamQuestion findQuestion(Long mockExamId, Integer problemSeq) {
        MockExam mockExam = mockExamRepository.findById(mockExamId)
                .orElseThrow(() -> new NoSuchElementException("모의고사 정보가 존재하지 않습니다"));
        return mockExam.getQuestions().get(problemSeq);
    }

    public void saveAnswer(Long examHistoryId, Integer problemId, String answerIndexDBId) {
        MockExamHistory mockExamHistory = mockExamHistoryRepository.findById(examHistoryId)
                .orElseThrow(() -> new NoSuchElementException("모의고사 응시 정보가 존재하지 않습니다"));
        MockExamQuestion question = mockExamHistory.getMockExam().getQuestions().get(problemId);

        mockExamAnswerRepository.save(MockExamAnswer.builder().mockExamHistory(mockExamHistory)
                .mockExamQuestion(question)
                .answerIndexDBId(answerIndexDBId)
                .build());
    }
}
