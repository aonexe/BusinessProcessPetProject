package com.vorsin.businessProcess.controllers;


import com.vorsin.businessProcess.dto.EmployeeViewResponse;
import com.vorsin.businessProcess.dto.EmployeeRegistrationRequest;
import com.vorsin.businessProcess.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


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
    public ResponseEntity<HttpStatus> createEmployee(@RequestBody @Valid EmployeeRegistrationRequest employeeDTO,
                                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getModel());
        }

        employeeService.createEmployee(employeeDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable("username") String username) {
        employeeService.deleteEmployee(username);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
