package com.sanhaehong.project.techview.controller;

import com.sanhaehong.project.techview.annotation.LogInUser;
import com.sanhaehong.project.techview.config.security.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IndexController {

    @GetMapping("/")
    public String index(Model model, @LogInUser SessionUser user) {
        if(user != null) {
            model.addAttribute("userName", user.getName());
            model.addAttribute("userPicture", user.getPicture());
        }
        return "index";
    }
}
