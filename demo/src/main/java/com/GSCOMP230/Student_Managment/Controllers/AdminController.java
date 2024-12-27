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

    // Show the user list with optional role filter
    @GetMapping("/admin/userlist")
    public String showUserList(@RequestParam(name = "roleFilter", required = false) String roleFilter,
                               HttpSession session, Model model) {
        String email = (String) session.getAttribute("userEmail");
        if (email != null) {
            Optional<User> userOpt = userRepository.findByEmail(email);
            if (userOpt.isPresent() && userOpt.get().getRole().equals("Admin")) {
                model.addAttribute("user", userOpt.get());

                List<User> userList;
                if (roleFilter != null && !roleFilter.isEmpty()) {
                    userList = userRepository.findByRole(roleFilter); // Find users by role
                    model.addAttribute("roleFilter", roleFilter); // Keep the selected filter value in the model
                } else {
                    userList = userRepository.findAll(); // Fetch all users if no filter
                }

                model.addAttribute("userList", userList); // Add user list to the model
                return "Admin/UserList"; // Return the user list view
            }
        }
        return "redirect:/login"; // Redirect if not authorized or session not found
    }

    // Handle role update from the form submission
    @PostMapping("/admin/updateUserRoles")
    public String updateUserRoles(@RequestParam("userId") Long userId,
                                  @RequestParam("role") String newRole,
                                  HttpSession session) {
        String email = (String) session.getAttribute("userEmail");
        if (email != null) {
            Optional<User> userOpt = userRepository.findById(userId);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                user.setRole(newRole); // Update the role
                userRepository.save(user); // Save the updated user
            }
        }
        return "redirect:/admin/userlist"; // Redirect back to user list page
    }

    // Handle user deletion from the form submission
    @PostMapping("/admin/deleteUser")
    public String deleteUser(@RequestParam("userId") Long userId, HttpSession session) {
        String email = (String) session.getAttribute("userEmail");
        if (email != null) {
            Optional<User> userOpt = userRepository.findById(userId);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                userRepository.delete(user); // Delete the user
            }
        }
        return "redirect:/admin/userlist"; // Redirect back to user list page
    }
}
