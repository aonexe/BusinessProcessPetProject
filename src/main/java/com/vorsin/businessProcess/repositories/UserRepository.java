package com.vorsin.businessProcess.repositories;

import com.vorsin.businessProcess.models.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {

   boolean existsByUsernameOrEmail(String username, String email);

   boolean existsByUsername(String username);
   boolean existsByEmail(String email);
}
