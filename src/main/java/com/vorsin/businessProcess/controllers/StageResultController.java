package com.vorsin.businessProcess.controllers;

import com.vorsin.businessProcess.dto.StageResultRequest;
import com.vorsin.businessProcess.dto.StageResultResponse;
import com.vorsin.businessProcess.services.StageResultService;
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
@RequestMapping("/stage-result")
public class StageResultController {

    private final StageResultService stageResultService;

    @Autowired
    public StageResultController(StageResultService stageResultService) {
        this.stageResultService = stageResultService;
    }

    @GetMapping()
    public List<StageResultResponse> getStagesResults() {
        return stageResultService.getStagesResults();
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> createStageResult(@RequestBody @Valid StageResultRequest stageResultRequest) {

        //todo binding result
        stageResultService.createStageResult(stageResultRequest);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> updateStageResult(@PathVariable("id") int id,
                                                  @RequestBody @Valid StageResultRequest stageResultRequest) {
        stageResultService.updateStageResult(id, stageResultRequest);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteStageResult(@PathVariable("id") int id) {
        stageResultService.deleteStageResult(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}


