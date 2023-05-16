package com.sanhaehong.project.techview.intercepter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class NavbarInterceptor implements HandlerInterceptor {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String userURI = request.getRequestURI();
        if(userURI.contains("question")) {
            modelAndView.addObject("navURI", "question");
        }
        if(userURI.contains("mockexam")) {
            modelAndView.addObject("navURI", "mockexam");
        }
    }
}
