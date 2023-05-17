package com.sanhaehong.project.techview.controller.model;

import com.sanhaehong.project.techview.domain.answer.Answer;
import com.sanhaehong.project.techview.domain.question.Question;
import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;

import java.util.List;

public class QuestionModel extends ModelMap {

    public ModelMap addQuestionPages(Page<Question> questionPage) {
        addAttribute("questions", questionPage.stream().toList());
        addAttribute("totalQuestion", questionPage.getTotalElements());
        addAttribute("totalPage", questionPage.getTotalPages());
        return this;
    }

    public ModelMap addQuestionAndAnswers(Question question, List<Answer> answers) {
        addAttribute("question", question);
        addAttribute("answers", answers);
        return this;
    }
}
