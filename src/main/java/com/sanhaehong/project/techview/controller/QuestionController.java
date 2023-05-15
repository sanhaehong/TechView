package com.sanhaehong.project.techview.controller;

import com.sanhaehong.project.techview.domain.question.Category;
import com.sanhaehong.project.techview.dto.QuestionFormDto;
import com.sanhaehong.project.techview.domain.question.Question;
import com.sanhaehong.project.techview.domain.question.QuestionRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class QuestionController {

    private final QuestionRepository questionRepository;

    @GetMapping("/question/lists")
    public String questionList(@PageableDefault Pageable pageable, Model model) {
        Page<Question> questionPage = questionRepository.findAll(pageable);
        model.addAttribute("questions", questionPage.stream().toList());
        model.addAttribute("totalQuestion", questionPage.getTotalElements());
        model.addAttribute("totalPage", questionPage.getTotalPages());
        return "question_list";
    }

    @GetMapping("/question/add")
    public String addQuestionForm(@ModelAttribute(name = "question") QuestionFormDto questionFormDto, Model model) {
        model.addAttribute("categories", Category.values());
        return "question_add";
    }

    @PostMapping("/question/add")
    public String addQuestion(@Valid @ModelAttribute(name = "question") QuestionFormDto questionFormDto,
                              BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("categories", Category.values());
            return "question_add";
        }
        questionRepository.save(questionFormDto.toEntity());
        System.out.println("go?");
        return "redirect:/question/lists?page=0";
    }
}
