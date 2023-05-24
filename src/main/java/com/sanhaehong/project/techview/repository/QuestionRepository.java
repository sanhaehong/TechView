package com.sanhaehong.project.techview.repository;

import com.sanhaehong.project.techview.domain.question.Category;
import com.sanhaehong.project.techview.domain.question.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("SELECT q FROM questions q WHERE (:content IS NULL OR q.content LIKE %:content%) AND (:category IS NULL OR q.category = :category)")
    Page<Question> findByContentAndCategory(@Param("content") String content, @Param("category") Category category, Pageable pageable);

}
