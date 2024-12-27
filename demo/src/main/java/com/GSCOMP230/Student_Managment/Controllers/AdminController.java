package com.GSCOMP230.Student_Managment.Controllers;

import com.GSCOMP230.Student_Managment.model.User;
import com.GSCOMP230.Student_Managment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;

@Controller
public class AdminController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/admin/home")
    public String showAdminHome(HttpSession session, Model model) {
        String email = (String) session.getAttribute("userEmail");
        if (email != null) {
            Optional<User> userOpt = userRepository.findByEmail(email);
            if (userOpt.isPresent() && userOpt.get().getRole().equals("Admin")) {
                model.addAttribute("user", userOpt.get());
                return "Admin/AdminHome";
            }
        }
        return "redirect:/login"; // Redirect to login if not authorized
    }


    @GetMapping("admin/userlist")
    public String showUserList(HttpSession session, Model model) {
        String email = (String) session.getAttribute("userEmail");
        if (email != null) {
            Optional<User> userOpt = userRepository.findByEmail(email);
            if (userOpt.isPresent() && userOpt.get().getRole().equals("Admin")) {
                model.addAttribute("user", userOpt.get());

                // Fetch the list of all users and add to the model
                List<User> userList = userRepository.findAll(); // Fetch all users
                model.addAttribute("userList", userList); // Add list of users to model

                return "Admin/UserList"; // Return the user list view
            }
        }
        return "redirect:/login"; // Redirect if not authorized or session not found
    }

}