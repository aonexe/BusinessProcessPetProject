package com.vorsin.businessProcess.controllers;

import com.vorsin.businessProcess.dto.ActionResultRequest;
import com.vorsin.businessProcess.dto.ActionResultResponse;
import com.vorsin.businessProcess.services.ActionResultService;
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
@RequestMapping("/action-result")
public class ActionResultController {

    private final ActionResultService actionResultService;

    @Autowired
    public ActionResultController(ActionResultService actionResultService) {
        this.actionResultService = actionResultService;
    }

    @GetMapping()
    public List<ActionResultResponse> getActionsResults() {
        return actionResultService.getActionsResults();
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> createActionsResult(@RequestBody @Valid ActionResultRequest actionResultRequest) {

        //todo binding result
        actionResultService.createActionResult(actionResultRequest);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> updateActionResult(@PathVariable("id") int id,
                                                        @RequestBody @Valid ActionResultRequest actionResultRequest) {
        actionResultService.updateActionResult(id, actionResultRequest);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteActionResult(@PathVariable("id") int id) {
        actionResultService.deleteActionResult(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}



