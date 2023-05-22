package com.sanhaehong.project.techview.repository;

import com.sanhaehong.project.techview.domain.mockexam.MockExamQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MockExamQuestionRepository extends JpaRepository<MockExamQuestion, Long> {
}
