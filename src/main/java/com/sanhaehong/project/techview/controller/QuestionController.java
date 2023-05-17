package com.sanhaehong.project.techview.controller;

import com.sanhaehong.project.techview.controller.argument.LogInUser;
import com.sanhaehong.project.techview.controller.model.QuestionModel;
import com.sanhaehong.project.techview.domain.answer.Answer;
import com.sanhaehong.project.techview.domain.question.Category;
import com.sanhaehong.project.techview.domain.question.Question;
import com.sanhaehong.project.techview.dto.AddAnswerDto;
import com.sanhaehong.project.techview.dto.AddQuestionDto;
import com.sanhaehong.project.techview.dto.FindQuestionDto;
import com.sanhaehong.project.techview.security.SessionUser;
import com.sanhaehong.project.techview.service.AnswerService;
import com.sanhaehong.project.techview.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class QuestionController {

    private final QuestionService questionService;
    private final AnswerService answerService;

    @ModelAttribute("categories")
    public Category[] AddCategoryView() {
        return Category.values();
    }

    @GetMapping("/question/lists")
    public String findQuestion(@ModelAttribute(name = "question") FindQuestionDto findQuestionDto,
                               @PageableDefault Pageable pageable,
                               QuestionModel questionModel) {
        Page<Question> questionPages = questionService.findPageAll(pageable);
        questionModel.addQuestionPages(questionPages);
        return "question/question_list";
    }

    @PostMapping("/question/lists")
    public String searchQuestion(@ModelAttribute(name = "question") FindQuestionDto findQuestionDto,
                                 @PageableDefault Pageable pageable,
                                 QuestionModel questionModel) {
        Page<Question> questionPages = questionService.findPage(findQuestionDto, pageable);
        questionModel.addQuestionPages(questionPages);
        return "question/question_list";
    }

    @GetMapping("/question/add")
    public String addQuestion(@ModelAttribute(name = "question") AddQuestionDto addQuestionDto) {
        return "question/question_add";
    }

    @PostMapping("/question/add")
    public String addQuestion(@Valid @ModelAttribute(name = "question") AddQuestionDto addQuestionDto,
                              BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "question/question_add";
        }
        questionService.save(addQuestionDto);
        return "redirect:/question/lists?page=0";
    }

    @GetMapping("/question/view/{id}")
    public String viewQuestion(@PathVariable Long id,
                               @ModelAttribute("answerForm") AddAnswerDto addAnswerDto,
                               QuestionModel questionModel) {
        Question question = questionService.findQuestion(id);
        List<Answer> answers = questionService.findAnswers(id);
        questionModel.addQuestionAndAnswers(question, answers);
        return "question/question_view";
    }

    @PostMapping("/question/view/{questionId}")
    public String addAnswer(@PathVariable Long questionId,
                            @Valid @ModelAttribute("answerForm") AddAnswerDto addAnswerDto,
                            BindingResult bindingResult,
                            @LogInUser SessionUser user,
                            RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            return "question/question_view";
        }
        answerService.addAnswer(user.getId(), questionId, addAnswerDto.getContent());
        redirectAttributes.addAttribute("questionId", questionId);
        return "redirect:/question/view/{questionId}";
    }
}
