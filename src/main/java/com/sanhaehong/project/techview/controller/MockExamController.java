package com.sanhaehong.project.techview.controller;

import com.sanhaehong.project.techview.controller.argument.LogInUser;
import com.sanhaehong.project.techview.domain.mockexam.MockExam;
import com.sanhaehong.project.techview.domain.mockexam.MockExamHistory;
import com.sanhaehong.project.techview.domain.mockexam.MockExamQuestion;
import com.sanhaehong.project.techview.domain.question.Category;
import com.sanhaehong.project.techview.domain.question.Question;
import com.sanhaehong.project.techview.dto.AddMockExamDto;
import com.sanhaehong.project.techview.security.SessionUser;
import com.sanhaehong.project.techview.service.MockExamService;
import com.sanhaehong.project.techview.service.QuestionService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/mockexam")
public class MockExamController {

    private final HttpSession httpSession;
    private final QuestionService questionService;
    private final MockExamService mockExamService;

    @ModelAttribute("categories")
    public Category[] AddCategoryView() {
        return Category.values();
    }

    @GetMapping
    public String mockexam() {
        return "mockexam/mockexam";
    }

    @GetMapping("/create")
    public String createMockExam(@RequestParam(required = false) String searchContent,
                               @RequestParam(required = false) Category searchCategory,
                               @ModelAttribute(name = "mockexam") AddMockExamDto addMockExamDto,
                               @PageableDefault Pageable pageable,
                               Model model) {
        List<Long> questionCheck = (List<Long>) httpSession.getAttribute("questionCheck");
        if(questionCheck == null) {
            questionCheck = new ArrayList<>();
            httpSession.setAttribute("questionCheck", questionCheck);
        }
        Page<Question> questionPages;
        if(searchContent == null && searchCategory == null) {
            questionPages = questionService.findPageAll(pageable);
        }
        else {
            questionPages = questionService.findPage(searchContent, searchCategory, pageable);
        }
        model.addAttribute("questions", questionPages.stream().toList());
        model.addAttribute("totalQuestion", questionPages.getTotalElements());
        model.addAttribute("totalPage", questionPages.getTotalPages());
        model.addAttribute("selectedQuestion", questionCheck);
        return "mockexam/mockexam_create";
    }

    @PostMapping("/create/select")
    public String addQuestion(@RequestParam Long id) {
        List<Long> questionCheck = (List<Long>) httpSession.getAttribute("questionCheck");
        questionCheck.add(id);
        httpSession.setAttribute("questionCheck", questionCheck);
        return "redirect:/mockexam/create";
    }

    @PostMapping("/create/delete")
    public String removeQuestion(@RequestParam Long id) {
        List<Long> questionCheck = (List<Long>) httpSession.getAttribute("questionCheck");
        questionCheck.remove(id);
        httpSession.setAttribute("questionCheck", questionCheck);
        return "redirect:/mockexam/create";
    }

    @PostMapping("/create")
    public String createMockExam(@Valid @ModelAttribute(name = "mockexam") AddMockExamDto addMockExamDto,
                                 BindingResult bindingResult,
                                 @LogInUser SessionUser user,
                                 RedirectAttributes redirectAttributes) {
        List<Long> questionCheck = (List<Long>) httpSession.getAttribute("questionCheck");
        if(questionCheck.size() == 0) {
            redirectAttributes.addFlashAttribute("selectedQuestion", questionCheck);
            redirectAttributes.addFlashAttribute("questionCheckError", "질문을 1개 이상 선택해주세요");
            return "redirect:/mockexam/create";
        }
        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("blankTitleError", "제목을 입력해주세요");
            return "redirect:/mockexam/create";
        }
        mockExamService.addMockExam(user.getId(), addMockExamDto.getTitle(), addMockExamDto.getInformation(), questionCheck);
        return "mockexam/mockexam";
    }

    @GetMapping("/select/lists")
    public String selectMockExam(@PageableDefault Pageable pageable,
                               Model model) {
        Page<MockExam> mockExamPages = mockExamService.findPageAll(pageable);
        model.addAttribute("mockExams", mockExamPages.stream().toList());
        model.addAttribute("totalExam", mockExamPages.getTotalElements());
        model.addAttribute("totalPage", mockExamPages.getTotalPages());
        return "mockexam/mockexam_list";
    }

    @GetMapping("/select/exam/{mockExamId}/ready")
    public String readyMockExam(@PathVariable Long mockExamId,
                                @LogInUser SessionUser user,
                                Model model) {
        MockExamHistory mockExamHistory = mockExamService.startMockExam(mockExamId, user.getId());
        httpSession.setAttribute("examId", mockExamHistory.getId());
        model.addAttribute("userId", user.getId());
        return "mockexam/mockexam_ready";
    }

    @GetMapping("/process/{problemId}")
    public String processMockExam(@PathVariable Integer problemId,
                                @LogInUser SessionUser user,
                                Model model) {
        Long examId = (Long) httpSession.getAttribute("examId");
        if(examId == null) {
            return "redirect:/";
        }
        try {
            MockExamQuestion question = mockExamService.findQuestion(examId, problemId);
            model.addAttribute("question", question.getQuestion().getContent());
            model.addAttribute("problemId", problemId);
            return "mockexam/mockexam_question";
        } catch (IndexOutOfBoundsException e) {
            return "redirect:/";
        }
    }

    @PostMapping(value = "/process/{problemId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public void processAnswer(@PathVariable Integer problemId,
                                                @RequestParam("audio") MultipartFile audio,
                                                Model model) throws IOException {
    }



}
