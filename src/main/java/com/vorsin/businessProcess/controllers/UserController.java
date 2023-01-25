package com.vorsin.businessProcess.controllers;


import com.vorsin.businessProcess.dto.EmployeeUserRequest;
import com.vorsin.businessProcess.dto.EmployeeViewResponse;
import com.vorsin.businessProcess.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<EmployeeViewResponse> getUsers() {
        return userService.getUsers();
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> createUser(@RequestBody @Valid EmployeeUserRequest employeeUserRequest,
                                                     BindingResult bindingResult) {

        //todo перенести логику в сервис
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        userService.createUser(employeeUserRequest);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/{username}")
    public ResponseEntity<HttpStatus> updateEmployee(@RequestBody @Valid EmployeeUserRequest employeeUserRequest,
                                                     @PathVariable("username") String username) {
        userService.updateUser(employeeUserRequest, username);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable("username") String username) {
        userService.deleteUser(username);
        return ResponseEntity.ok(HttpStatus.OK);
    }


}
