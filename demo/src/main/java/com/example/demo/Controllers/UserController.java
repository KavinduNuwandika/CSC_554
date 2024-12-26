package com.example.demo.Controllers;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String homePage() {
        return "HomePage";
    }

    @GetMapping("/Homepage")
    public String homepage() {
        return "homepage"; // Return homepage view
    }


    @GetMapping("/SignUp")
    public String dataEntryPage(Model model) {
        model.addAttribute("user", new User());
        return "SignUp";
    }

    @GetMapping("/loginPage")
    public String loginPage() {
        return "Login"; // Return login page view
    }

    @PostMapping("/submit")
    public String submitForm(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/";
    }
}
