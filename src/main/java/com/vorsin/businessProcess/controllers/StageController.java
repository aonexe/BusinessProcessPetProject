package com.vorsin.businessProcess.controllers;

import com.vorsin.businessProcess.dto.StageDTO;
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
@RequestMapping("/bp/{id}")
public class StageController {

    private final StageService stageService;

    @Autowired
    public StageController(StageService stageService) {
        this.stageService = stageService;
    }

    @GetMapping()
    public List<StageDTO> getStages(@PathVariable int id) {
        return stageService.getStages(id);
    }

    @PostMapping("/new")
    public ResponseEntity<HttpStatus> createStage(@PathVariable int id,
                                                  @RequestBody @Valid StageDTO stageDTO) {

        stageService.createStage(id, stageDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/stage/{stageId}")
    public ResponseEntity<HttpStatus> updateUser(@PathVariable("id") int id,
                                                 @PathVariable("stageId") int stageId,
                                                 @RequestBody @Valid StageDTO stageDTO) {
        stageService.updateStage(stageDTO, id, stageId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/stage/{stageId}")
    public ResponseEntity<HttpStatus> deleteStage(@PathVariable("id") int id,
                                                  @PathVariable("stageId") int stageId) {
        stageService.deleteStage(id, stageId);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
