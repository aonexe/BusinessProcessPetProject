package com.vorsin.businessProcess.controllers;

import com.vorsin.businessProcess.dto.StageRelationRequest;
import com.vorsin.businessProcess.dto.StageRelationResponse;
import com.vorsin.businessProcess.services.StageRelationService;
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
@RequestMapping("/stage-relations")
public class StageRelationController {

    private final StageRelationService stageRelationService;

    @Autowired
    public StageRelationController(StageRelationService stageRelationService) {
        this.stageRelationService = stageRelationService;
    }

    @GetMapping()
    public List<StageRelationResponse> getStageRelations() {
        return stageRelationService.getStageRelations();
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> createStage(@RequestBody @Valid StageRelationRequest stageRelationRequest) {
        //todo binding result
        stageRelationService.createStageRelation(stageRelationRequest);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> updateStage(@PathVariable("id") int id,
                                                  @RequestBody @Valid StageRelationRequest stageRelationRequest) {
        stageRelationService.updateStageRelation(id, stageRelationRequest);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteStage(@PathVariable("id") int id) {
        stageRelationService.deleteStageRelation(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
