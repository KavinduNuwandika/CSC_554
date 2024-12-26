package com.example.demo.Controllers;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String showLoginPage() {
        return "Login";
    }

    @PostMapping("/login")
    public String loginUser(
            @RequestParam("identifier") String email,
            @RequestParam("password") String password,
            Model model) {

        User user = userRepository.findByEmailAndPassword(email, password);

        if (user != null) {
            return "Home"; // Redirect to homepage.html
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "LoginError"; // Stay on login.html with an error message
        }
    }
}
