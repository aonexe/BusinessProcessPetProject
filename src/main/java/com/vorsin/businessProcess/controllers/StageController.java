package com.vorsin.businessProcess.controllers;

import com.vorsin.businessProcess.dto.StageRequest;
import com.vorsin.businessProcess.dto.StageResponse;
import com.vorsin.businessProcess.services.StageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/stages")
public class StageController {

    private final StageService stageService;

    @Autowired
    public StageController(StageService stageService) {
        this.stageService = stageService;
    }

    @GetMapping()
    public List<StageResponse> getStages() {
        return stageService.getStages();
    }

    @PostMapping("/new")
    public ResponseEntity<HttpStatus> createStage(@RequestBody @Valid StageRequest stageRequest) {

        //todo binding result
        stageService.createStage(stageRequest);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> updateStage(@PathVariable("id") int id,
                                                  @RequestBody @Valid StageRequest stageRequest) {
        stageService.updateStage(id, stageRequest);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteStage(@PathVariable("id") int id) {
        stageService.deleteStage(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
