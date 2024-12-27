package com.GSCOMP230.Student_Managment.Controllers;

import com.GSCOMP230.Student_Managment.model.User;
import com.GSCOMP230.Student_Managment.service.UserService;
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
    @PostMapping("/SignUp")
    public String signUp(@ModelAttribute("user") User user, Model model) {
        // Check if email or phone already exists
        if (userService.isEmailOrPhoneExist(user.getEmail(), user.getPhone())) {
            model.addAttribute("error", "Email or Phone number already exists!");
            return "SignUp"; // Return to the same page with the error
        }

        // Save the user if no error
        userService.saveUser(user);
        return "redirect:/login"; // Redirect to login after successful signup
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
