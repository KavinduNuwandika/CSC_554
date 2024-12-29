package com.GSCOMP230.Student_Managment.Controllers;

import com.GSCOMP230.Student_Managment.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import com.GSCOMP230.Student_Managment.model.Course;
import com.GSCOMP230.Student_Managment.model.Enrollment;
import com.GSCOMP230.Student_Managment.service.EnrollmentService;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;



@Controller
public class StudentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private EnrollmentRepository enrollmentRepository;



    @GetMapping("/student/registered-courses")
    public String viewRegisteredCourses(@RequestParam Long studentId, Model model) {
        Set<Course> registeredCourses = enrollmentService.getRegisteredCoursesByStudentId(studentId);
        model.addAttribute("courses", registeredCourses);
        return "student/registered-courses"; // Return the appropriate view
    }

    @GetMapping("/student/view-results")
    public String viewResults(@RequestParam("id") Long userId, Model model) {
        // Fetch enrollments for the given student
        List<Enrollment> enrollments = enrollmentRepository.findByStudent_Id(userId);

        // Separate into marked and unmarked subjects
        List<Enrollment> markedSubjects = new ArrayList<>();
        List<Enrollment> unmarkedSubjects = new ArrayList<>();

        for (Enrollment enrollment : enrollments) {
            if (enrollment.getMarks() != null) {
                markedSubjects.add(enrollment);
            } else {
                unmarkedSubjects.add(enrollment);
            }
        }

        // Add attributes to the model for use in Thymeleaf template
        model.addAttribute("markedSubjects", markedSubjects);
        model.addAttribute("unmarkedSubjects", unmarkedSubjects);

        return "Student/view-results";  // Return the view template
    }


}