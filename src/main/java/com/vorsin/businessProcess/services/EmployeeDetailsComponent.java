//package com.vorsin.businessProcess.services;
//
//
//import com.vorsin.businessProcess.models.Employee;
//import com.vorsin.businessProcess.repositories.EmployeeRepository;
//import com.vorsin.businessProcess.security.EmployeeDetails;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Component;
//
//import java.util.Optional;
//
//@Component
//public class EmployeeDetailsComponent implements UserDetailsService {
//
//    private final EmployeeRepository employeeRepository;
//
//
//    public EmployeeDetailsComponent(EmployeeRepository employeeRepository) {
//        this.employeeRepository = employeeRepository;
//    }
//
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<Employee> person = employeeRepository.findByUsername(username);
//        if (person.isEmpty()) {
//            throw new UsernameNotFoundException("User not found");
//        }
//        return new EmployeeDetails(person.get());
//    }
//}
