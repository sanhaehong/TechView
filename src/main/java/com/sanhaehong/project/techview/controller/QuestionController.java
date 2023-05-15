package com.sanhaehong.project.techview.controller;

import com.sanhaehong.project.techview.annotation.LogInUser;
import com.sanhaehong.project.techview.config.security.SessionUser;
import com.sanhaehong.project.techview.domain.answer.Answer;
import com.sanhaehong.project.techview.domain.answer.AnswerRepository;
import com.sanhaehong.project.techview.domain.question.Category;
import com.sanhaehong.project.techview.domain.user.User;
import com.sanhaehong.project.techview.domain.user.UserRepository;
import com.sanhaehong.project.techview.dto.AddAnswerDto;
import com.sanhaehong.project.techview.dto.AddQuestionDto;
import com.sanhaehong.project.techview.domain.question.Question;
import com.sanhaehong.project.techview.domain.question.QuestionRepository;
import com.sanhaehong.project.techview.dto.SearchQuestionDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class QuestionController {

    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final AnswerRepository answerRepository;

    @ModelAttribute("categories")
    public Category[] AddCategoryView() {
        return Category.values();
    }

    @GetMapping("/question/lists")
    public String questionList(@ModelAttribute(name = "question") SearchQuestionDto searchQuestionDto, @PageableDefault Pageable pageable, Model model) {
        Page<Question> questionPage = questionRepository.findAll(pageable);
        model.addAttribute("questions", questionPage.stream().toList());
        model.addAttribute("totalQuestion", questionPage.getTotalElements());
        model.addAttribute("totalPage", questionPage.getTotalPages());
        return "question_list";
    }

    @PostMapping("/question/lists")
    public String searchQuestion(@ModelAttribute(name = "question") SearchQuestionDto searchQuestionDto, @PageableDefault Pageable pageable, Model model) {
        Page<Question> questionPage = questionRepository.findByContentAndCategory(searchQuestionDto.getContent(), searchQuestionDto.getCategory(), pageable);
        model.addAttribute("questions", questionPage.stream().toList());
        model.addAttribute("totalQuestion", questionPage.getTotalElements());
        model.addAttribute("totalPage", questionPage.getTotalPages());
        return "question_list";
    }

    @GetMapping("/question/add")
    public String addQuestionForm(@ModelAttribute(name = "question") AddQuestionDto addQuestionDto) {
        return "question_add";
    }

    @PostMapping("/question/add")
    public String addQuestion(@Valid @ModelAttribute(name = "question") AddQuestionDto addQuestionDto,
                              BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "question_add";
        }
        questionRepository.save(addQuestionDto.toEntity());
        System.out.println("go?");
        return "redirect:/question/lists?page=0";
    }

    @GetMapping("/question/view/{id}")
    public String viewQuestion(@PathVariable Long id,
                               @ModelAttribute("answerForm") AddAnswerDto addAnswerDto,
                               Model model) {
        Question question = questionRepository.findById(id).get();
        List<Answer> answers = question.getAnswers();
        model.addAttribute("question", question);
        model.addAttribute("answers", answers);
        return "question_view";
    }

    @PostMapping("/question/view/{id}")
    public String addAnswer(@PathVariable Long id,
                            @Valid @ModelAttribute("answerForm") AddAnswerDto addAnswerDto,
                            BindingResult bindingResult,
                            @LogInUser SessionUser user,
                            RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            return "question_view";
        }
        User writer = userRepository.findById(user.getId()).get();
        Question question = questionRepository.findById(id).get();
        Answer answer = addAnswerDto.toEntity(question, writer);
        question.getAnswers().add(answer);
        answerRepository.save(answer);
        questionRepository.save(question);
        redirectAttributes.addAttribute("questionId", id);
        return "redirect:/question/view/{questionId}";
    }
}
