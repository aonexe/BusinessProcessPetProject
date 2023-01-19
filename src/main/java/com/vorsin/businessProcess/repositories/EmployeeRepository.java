package com.vorsin.businessProcess.repositories;


import com.vorsin.businessProcess.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Optional<Employee> findByUsername(String username);
    Optional<Employee> findByUsernameOrEmail(String username, String email);


}
