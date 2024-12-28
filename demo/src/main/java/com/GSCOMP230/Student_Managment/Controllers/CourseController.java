package com.GSCOMP230.Student_Managment.Controllers;

import com.GSCOMP230.Student_Managment.model.User;
import com.GSCOMP230.Student_Managment.model.Course;
import com.GSCOMP230.Student_Managment.repository.CourseRepository;
import com.GSCOMP230.Student_Managment.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

import java.util.List;

@Controller
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    // Show the create course form
    @GetMapping("/teacher/create-course")
    public String showCreateCourseForm(Model model) {
        return "redirect:/teacher/view-classes";
    }

    // Handle the form submission to create a course
    @PostMapping("/teacher/create-course")
    public String createClass(
            @RequestParam String classCode,
            @RequestParam String courseName,
            @RequestParam int academicYear,
            @RequestParam int credits,
            @RequestParam String mainSubject,
            HttpSession session,
            Model model) {
        try {
            Long teacherId = (Long) session.getAttribute("teacherId");
            if (teacherId == null) {
                model.addAttribute("error", "You must be logged in as a teacher to create a course.");
                return "redirect:/login"; // Redirect to login page if not logged in
            }

            // Fetch the teacher from the database
            User teacher = userRepository.findById(teacherId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid Teacher ID"));

            // Check if the course already exists with the same courseCode and academicYear
            boolean courseExists = courseRepository.existsByCourseCodeAndAcademicYear(classCode, academicYear);
            if (courseExists) {
                model.addAttribute("error", "A course with the same Course Code and Academic Year already exists.");
                return "redirect:/teacher/create-course"; // Redirect back to the creation page
            }

            // Create and save the new course
            Course newClass = new Course();
            newClass.setCourseCode(classCode);
            newClass.setCourseName(courseName);
            newClass.setMainSubject(mainSubject);
            newClass.setAcademicYear(academicYear);
            newClass.setCredits(credits);
            newClass.setTeacher(teacher);

            courseRepository.save(newClass);
            model.addAttribute("success", "Course created successfully!");

        } catch (Exception e) {
            model.addAttribute("error", "Error creating course: " + e.getMessage());
        }

        return "redirect:/teacher/view-classes"; // Redirect after course is created
    }

    // Show the classes assigned to the teacher
    @GetMapping("/teacher/view-classes")
    public String viewClasses(HttpSession session, Model model) {
        Long teacherId = (Long) session.getAttribute("teacherId");
        if (teacherId == null) {
            model.addAttribute("error", "Teacher not logged in.");
            return "error"; // Redirect to an error page or login page
        }

        // Fetch the teacher's courses
        User teacher = userRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Teacher ID"));

        List<Course> courses = courseRepository.findByTeacher(teacher);
        model.addAttribute("courses", courses);
        return "Teacher/View-Classes"; // Ensure path to the View-Classes page is correct
    }
}

