package com.logintegra.wsbbugtracker.security;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SecurityController {

    @RequestMapping("/login")
    ModelAndView login() {
        return new ModelAndView("security/login");
    }

    @PostMapping(path = "/")
    String signIn() {
        return "redirect:issue/index";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "security/login";
    }
}
