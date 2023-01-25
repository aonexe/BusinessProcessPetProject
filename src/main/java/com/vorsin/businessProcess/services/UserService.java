package com.vorsin.businessProcess.services;

import com.vorsin.businessProcess.dto.EmployeeUserRequest;
import com.vorsin.businessProcess.dto.EmployeeViewResponse;
import com.vorsin.businessProcess.models.User;
import com.vorsin.businessProcess.models.UserRole;
import com.vorsin.businessProcess.repositories.UserRepository;
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
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public List<EmployeeViewResponse> getUsers() {
        return userRepository.findAll().stream().map(this::convertToEmployeeViewResponse).collect(Collectors.toList());
    }

    @Transactional
    public void createUser(EmployeeUserRequest employeeUserRequest) {
        if (isUserPresent(employeeUserRequest.getUsername())) {
            //todo custom status
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        } else {
            User newEmployee = convertToEmployee(employeeUserRequest);
            enrichNewEmployee(newEmployee);
            userRepository.save(newEmployee);
        }
    }

    @Transactional
    public void updateUser(EmployeeUserRequest employeeUserRequest, String username) {
        if (isUserPresent(username)) {
            //todo одинаковые юзернеймы или емэйлы
            User employee = convertToEmployee(employeeUserRequest);
            enrichEmployee(employee, username);
            userRepository.save(employee);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


    @Transactional
    public void deleteUser(String username) {
        if (isUserPresent(username)) {
            User employee = userRepository.findByUsername(username).get();
            userRepository.deleteById(employee.getId());
        }
    }

    private boolean isUserPresent(String username) {
        Optional<User> employee = userRepository.findByUsername(username);
        return employee.isPresent();
    }

    private EmployeeViewResponse convertToEmployeeViewResponse(User employee) {
        return modelMapper.map(employee, EmployeeViewResponse.class);
    }

    private User convertToEmployee(EmployeeUserRequest employeeUserRequest) {
        User employee = modelMapper.map(employeeUserRequest, User.class);
        return employee;
    }


    //todo
    private void enrichNewEmployee(User newEmployee) {
        newEmployee.setCreatedAt(LocalDateTime.now());
        //todo
        newEmployee.setCreatedWho("ROLE_ADMIN");
        newEmployee.setUserRole(UserRole.USER);
    }

    private void enrichEmployee(User newEmployee, String username) {
        User oldEmployee = userRepository.findByUsername(username).get();
        newEmployee.setId(oldEmployee.getId());
        newEmployee.setUserRole(oldEmployee.getUserRole());
        newEmployee.setCreatedAt(oldEmployee.getCreatedAt());
        newEmployee.setCreatedWho(oldEmployee.getCreatedWho());
        //todo
        newEmployee.setUpdatedAt(LocalDateTime.now());
        newEmployee.setUpdatedWho(new User());
    }

}
