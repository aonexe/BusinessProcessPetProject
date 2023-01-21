package com.vorsin.businessProcess.controllers;


import com.vorsin.businessProcess.dto.EmployeeUserRequest;
import com.vorsin.businessProcess.dto.EmployeeViewResponse;
import com.vorsin.businessProcess.services.EmployeeService;
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
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<EmployeeViewResponse> getUsers() {
        return employeeService.getUsers();
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> createEmployee(@RequestBody @Valid EmployeeUserRequest employeeUserRequest,
                                                     BindingResult bindingResult) {

        //todo перенести логику в сервис
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        employeeService.createEmployee(employeeUserRequest);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/{username}")
    public ResponseEntity<HttpStatus> updateEmployee(@RequestBody @Valid EmployeeUserRequest employeeUserRequest,
                                                     @PathVariable("username") String username) {
        employeeService.updateEmployee(employeeUserRequest, username);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable("username") String username) {
        employeeService.deleteEmployee(username);
        return ResponseEntity.ok(HttpStatus.OK);
    }


}
