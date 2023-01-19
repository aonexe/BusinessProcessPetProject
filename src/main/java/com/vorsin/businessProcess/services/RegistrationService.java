//package com.vorsin.businessProcess.services;
//
//import com.vorsin.businessProcess.models.Employee;
//import com.vorsin.businessProcess.repositories.EmployeeRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//public class RegistrationService {
//
//    private final EmployeeRepository personRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    @Autowired
//    public RegistrationService(EmployeeRepository personRepository, PasswordEncoder passwordEncoder) {
//        this.personRepository = personRepository;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    public void register(Employee person) {
//        person.setPassword(passwordEncoder.encode(person.getPassword()));
//        person.setRole("ROLE_USER");
//        personRepository.save(person);
//    }
//}
