package com.sanhaehong.project.techview.repository;

import com.sanhaehong.project.techview.domain.mockexam.MockExamHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MockExamHistoryRepository extends JpaRepository<MockExamHistory, Long> {
}
