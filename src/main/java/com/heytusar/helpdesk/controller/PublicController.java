package com.heytusar.helpdesk.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.heytusar.helpdesk.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PublicController {

    @Autowired
    private UserService userService;
    
    @RequestMapping("/")
    public String login(Model model, HttpServletRequest request) {
        /*
        model.addAttribute("greeting", request.getParameter("greeting"));
        List<String> tasks = new ArrayList<String>();
        tasks.add("Eat");
        tasks.add("Sleep");
        tasks.add("Code");
        model.addAttribute("tasks", tasks);
        */
        //String message = (String) request.getAttribute("message");
        String message = request.getParameter("message");
        System.out.println("message param ------------> " + message);
        if(message != null) {
            model.addAttribute("message", message);
        }
        return "loginPage";
    }

    @PostMapping("/authCheck")
    public ModelAndView authCheck(Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        Map result = userService.authCheck(request);
        System.out.println("result ---------------> " + result);
        if(result.get("status") == "success") {
            response.addCookie((Cookie) result.get("cookie"));
            return new ModelAndView("redirect:dashboard");
            //return "redirect:dashboard";
        }
        else {
            redirectAttributes.addAttribute("message", result.get("message"));
            return new ModelAndView("redirect:");
            //return "redirect:";
        }
    }   
}