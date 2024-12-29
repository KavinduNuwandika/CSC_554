package com.GSCOMP230.Student_Managment.service;

import com.GSCOMP230.Student_Managment.model.User;
import com.GSCOMP230.Student_Managment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public User getUserByPhone(String phone) {
        return userRepository.findByPhone(phone).orElse(null);
    }
    public boolean isEmailOrPhoneExist(String email, String phone) {
        return userRepository.existsByEmail(email) || userRepository.existsByPhone(phone);
    }
}
