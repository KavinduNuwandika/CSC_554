package com.example.demo.repository;
import com.example.demo.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmailAndPassword(String email, String password);
    User findByPhoneAndPassword(String phone, String password);
    Optional<User> findByRole(String role);

}