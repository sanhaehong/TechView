package com.sanhaehong.project.techview.service;

import com.sanhaehong.project.techview.repository.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
@SpringBootTest
public abstract class ServiceTest {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MockExamRepository mockExamRepository;

    @Autowired
    MockExamQuestionRepository mockExamQuestionRepository;

    @Autowired
    MockExamAnswerRepository mockExamAnswerRepository;

    @Autowired
    MockExamHistoryRepository mockExamHistoryRepository;

}
