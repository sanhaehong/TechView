package com.sanhaehong.project.techview.service;

import com.sanhaehong.project.techview.domain.answer.Answer;
import com.sanhaehong.project.techview.domain.question.Question;
import com.sanhaehong.project.techview.domain.user.User;
import com.sanhaehong.project.techview.repository.AnswerRepository;
import com.sanhaehong.project.techview.repository.QuestionRepository;
import com.sanhaehong.project.techview.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
@Transactional
public class AnswerService {

    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public Answer addAnswer(Long userId, Long questionId, String content) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new NoSuchElementException("질문이 존재하지 않습니다"));
        User writer = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("유저 정보가 존재하지 않습니다"));
        Answer answer = Answer.builder().question(question)
                .writer(writer)
                .content(content)
                .build();
        answerRepository.save(answer);
        question.getAnswers().add(answer);
        return answer;
    }

    public void deleteAnswer(Long answerId) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new NoSuchElementException("답변이 존재하지 않습니다"));
        answer.getQuestion().getAnswers().remove(answer);
        answerRepository.delete(answer);
    }

    @Transactional
    public Answer updateAnswer(Long answerId, String content) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new NoSuchElementException("답변이 존재하지 않습니다"));
        answer.update(content);
        return answer;
    }
}
