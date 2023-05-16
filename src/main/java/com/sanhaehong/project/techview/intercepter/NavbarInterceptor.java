package com.sanhaehong.project.techview.intercepter;

import com.sanhaehong.project.techview.security.SessionUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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

        HttpSession httpSession = request.getSession(false);
        if(httpSession != null) {
            SessionUser user = (SessionUser) httpSession.getAttribute("user");
            modelAndView.addObject("userName", user.getName());
            modelAndView.addObject("userPicture", user.getPicture());
        }
    }
}
