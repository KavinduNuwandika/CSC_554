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
        return "Login"; // Return login page
    }

    @PostMapping("/login")
    public String loginUser(
            @RequestParam("identifier") String identifier, // Can be email or phone
            @RequestParam("password") String password,
            Model model) {

        // Try to find the user by email and password, then by phone and password
        User user = userRepository.findByEmailAndPassword(identifier, password);

        if (user == null) {
            user = userRepository.findByPhoneAndPassword(identifier, password); // Check phone if email fails
        }

        if (user != null) {
            switch (user.getRole()) {
                case "Admin":
                    return "AdminHome"; // Admin dashboard
                case "Teacher":
                    return "TeacherDashboard"; // Teacher dashboard
                case "Student":
                    return "StudentResults"; // Student can only see results
                default:
                    model.addAttribute("error", "Invalid credentials.");
                    return "LoginError"; // If role is not set or invalid
            }
        } else {
            model.addAttribute("error", "Invalid credentials. Please try again.");
            return "LoginError"; // Stay on login page with an error message
        }
    }
}
