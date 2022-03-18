package com.heytusar.helpdesk.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.heytusar.helpdesk.model.User;
import com.heytusar.helpdesk.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;
    
    @RequestMapping("/dashboard")
    public String dashboard(Model model, HttpServletRequest request) {
        User user = userService.getLoggedInUser(request);
        if(user != null) {
            model.addAttribute("email", user.getEmail());
            model.addAttribute("firstName", user.getFirstName());
            model.addAttribute("lastName", user.getLastName());
            return "dashboardPage";
        }
        else {
            return "redirect:";
        }
    }

    @RequestMapping("/logout")
    public String logout(Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals("helpdesk-email")) {
                cookie.setValue("");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
                redirectAttributes.addAttribute("message", "You are now logged out");
                break;
            }
        }
        return "redirect:";
    }
}