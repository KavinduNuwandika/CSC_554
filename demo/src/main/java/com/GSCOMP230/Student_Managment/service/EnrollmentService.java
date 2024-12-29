package com.GSCOMP230.Student_Managment.service;

import com.GSCOMP230.Student_Managment.model.Enrollment;
import com.GSCOMP230.Student_Managment.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public Enrollment findById(Long id) {
        return enrollmentRepository.findById(id).orElse(null);
    }

    public Enrollment save(Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }
}
