package com.GSCOMP230.Student_Managment.Controllers;

import com.GSCOMP230.Student_Managment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@Controller
public class TeacherController {

    @Autowired
    private UserRepository userRepository;

    // Correcting the method syntax by adding parentheses and a return type
    @GetMapping("/teacher/create-class")
    public String createCourse() {
        return "Teacher/create-class";

    }
}
