package com.vorsin.businessProcess.services;

import com.vorsin.businessProcess.dto.EmployeeViewResponse;
import com.vorsin.businessProcess.dto.EmployeeRegistrationRequest;
import com.vorsin.businessProcess.models.Employee;
import com.vorsin.businessProcess.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public List<EmployeeViewResponse> getUsers() {
        return employeeRepository.findAll().stream().map(this::convertToEmployeeViewResponse).collect(Collectors.toList());
    }

    @Transactional
    public void createEmployee(EmployeeRegistrationRequest employeeRegistrationRequest) {
        String username = employeeRegistrationRequest.getUsername();
        String email = employeeRegistrationRequest.getEmail();

        Optional<Employee> employee = employeeRepository.findByUsernameOrEmail(username, email);
        if (employee.isPresent()) {
            //todo custom status
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        } else {
            Employee newEmployee = convertToEmployee(employeeRegistrationRequest);
            enrichEmployee(newEmployee);
            employeeRepository.save(newEmployee);
        }


    }

    @Transactional
    public void deleteEmployee(String username) {
        Optional<Employee> employee = employeeRepository.findByUsername(username);
        if (employee.isPresent()) {
            employeeRepository.deleteById(employee.get().getId());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    private EmployeeViewResponse convertToEmployeeViewResponse(Employee employee) {
        return modelMapper.map(employee, EmployeeViewResponse.class);
    }

    private Employee convertToEmployee(EmployeeRegistrationRequest employeeRegistrationRequest) {
        return  modelMapper.map(employeeRegistrationRequest, Employee.class);
    }

    private void enrichEmployee(Employee employee) {
        employee.setCreatedAt(LocalDateTime.now());
        employee.setCreatedWho("ROLE_ADMIN");
        employee.setRole("ROLE_USER");
    }
}
