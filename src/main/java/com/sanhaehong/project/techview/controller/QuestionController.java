package com.sanhaehong.project.techview.controller;

import com.sanhaehong.project.techview.domain.question.Question;
import com.sanhaehong.project.techview.domain.question.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class QuestionController {

    private final QuestionRepository questionRepository;

    @GetMapping("/question/lists")
    public String question(@PageableDefault Pageable pageable, Model model) {
        Page<Question> questionPage = questionRepository.findAll(pageable);
        model.addAttribute("questions", questionPage.stream().toList());
        model.addAttribute("totalQuestion", questionPage.getTotalElements());
        model.addAttribute("totalPage", questionPage.getTotalPages());
        return "question";
    }

}
