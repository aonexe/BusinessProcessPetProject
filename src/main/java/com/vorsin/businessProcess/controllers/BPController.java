package com.vorsin.businessProcess.controllers;

import com.vorsin.businessProcess.dto.BPResponse;
import com.vorsin.businessProcess.dto.BPRequest;
import com.vorsin.businessProcess.services.BPService;
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
public class BPController {

    private final BPService bpService;

    @Autowired
    public BPController(BPService bpService) {
        this.bpService = bpService;
    }

    @GetMapping
    public List<BPResponse> getBusinessProcesses() {
        return bpService.getBusinessProcesses();
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> createBusinessProcess(@RequestBody @Valid BPRequest bpRequest,
                                                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            //todo throw response
            System.out.println("error create bp!!");
        }
        bpService.createBusinessProcess(bpRequest);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> updateBusinessProcess( @PathVariable("id") int id,
                                                             @RequestBody @Valid BPRequest bpRequest) {
        bpService.updateBusinessProcess(bpRequest, id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteBusinessProcess(@PathVariable("id") int id) {
        bpService.deleteBusinessProcess(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
