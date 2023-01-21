package com.vorsin.businessProcess.controllers;

import com.vorsin.businessProcess.dto.BusinessProcessDTO;
import com.vorsin.businessProcess.dto.EmployeeUserRequest;
import com.vorsin.businessProcess.models.BusinessProcess;
import com.vorsin.businessProcess.services.BusinessProcessService;
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

import java.util.List;

@RestController
@RequestMapping("/bp")
public class BusinessProcessController {

    private final BusinessProcessService businessProcessService;

    @Autowired
    public BusinessProcessController(BusinessProcessService businessProcessService) {
        this.businessProcessService = businessProcessService;
    }

    @GetMapping
    public List<BusinessProcessDTO> getBusinessProcess() {
        return businessProcessService.getBusinessProcess();
    }

    @PostMapping("/new")
    public ResponseEntity<HttpStatus> createBusinessProcess(@RequestBody @Valid BusinessProcessDTO businessProcessDTO,
                                                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            //todo throw response
            System.out.println("error create bp!!");
        }
        businessProcessService.createBusinessProcess(businessProcessDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> updateBusinessProcess(@RequestBody @Valid BusinessProcessDTO businessProcessDTO,
                                                            @PathVariable("id") int id) {
        businessProcessService.updateBusinessProcess(businessProcessDTO, id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteBusinessProcess(@PathVariable("id") int id) {
        businessProcessService.deleteBusinessProcess(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }





}
