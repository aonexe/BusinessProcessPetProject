package com.vorsin.businessProcess.services;

import com.vorsin.businessProcess.dto.EmployeeUserRequest;
import com.vorsin.businessProcess.dto.EmployeeViewResponse;
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
    public void createEmployee(EmployeeUserRequest employeeUserRequest) {
        if (isEmployeePresent(employeeUserRequest.getUsername())) {
            //todo custom status
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        } else {
            Employee newEmployee = convertToEmployee(employeeUserRequest);
            enrichNewEmployee(newEmployee);
            employeeRepository.save(newEmployee);
        }
    }

    @Transactional
    public void updateEmployee(EmployeeUserRequest employeeUserRequest, String username) {
        if (isEmployeePresent(username)) {
            //todo одинаковые юзернеймы или емэйлы
            Employee employee = convertToEmployee(employeeUserRequest);
            enrichEmployee(employee, username);
            employeeRepository.save(employee);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


    @Transactional
    public void deleteEmployee(String username) {
        if (isEmployeePresent(username)) {
            Employee employee = employeeRepository.findByUsername(username).get();
            employeeRepository.deleteById(employee.getId());
        }
    }

    private boolean isEmployeePresent(String username) {
        Optional<Employee> employee = employeeRepository.findByUsername(username);
        return employee.isPresent();
    }

    private EmployeeViewResponse convertToEmployeeViewResponse(Employee employee) {
        return modelMapper.map(employee, EmployeeViewResponse.class);
    }

    private Employee convertToEmployee(EmployeeUserRequest employeeUserRequest) {
        Employee employee = modelMapper.map(employeeUserRequest, Employee.class);
        return employee;
    }


    //todo
    private void enrichNewEmployee(Employee newEmployee) {
        newEmployee.setCreatedAt(LocalDateTime.now());
        //todo
        newEmployee.setCreatedWho("ROLE_ADMIN");
        newEmployee.setRole("ROLE_USER");
    }

    private void enrichEmployee(Employee newEmployee, String username) {
        Employee oldEmployee = employeeRepository.findByUsername(username).get();
        newEmployee.setId(oldEmployee.getId());
        newEmployee.setRole(oldEmployee.getRole());
        newEmployee.setCreatedAt(oldEmployee.getCreatedAt());
        newEmployee.setCreatedWho(oldEmployee.getCreatedWho());
        //todo
        newEmployee.setUpdatedAt(LocalDateTime.now());
        newEmployee.setUpdatedWho("Updater");
    }

}
