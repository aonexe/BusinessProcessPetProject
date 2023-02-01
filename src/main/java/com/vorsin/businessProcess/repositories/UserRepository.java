package com.vorsin.businessProcess.repositories;

import com.vorsin.businessProcess.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {



   boolean existsByUsernameOrEmail(String username, String email);
}
