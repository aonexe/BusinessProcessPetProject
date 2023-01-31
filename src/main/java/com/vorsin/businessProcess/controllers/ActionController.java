package com.vorsin.businessProcess.controllers;

import com.vorsin.businessProcess.dto.ActionRequest;
import com.vorsin.businessProcess.dto.ActionResponse;
import com.vorsin.businessProcess.services.ActionService;
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
@RequestMapping("/actions")
public class ActionController {

    private final ActionService actionService;

    @Autowired
    public ActionController(ActionService actionService) {
        this.actionService = actionService;
    }

    @GetMapping()
    public List<ActionResponse> getStages() {
        return actionService.getActions();
    }

    @PostMapping("/new")
    public ResponseEntity<HttpStatus> createAction(@RequestBody @Valid ActionRequest actionRequest) {

        //todo binding result
        actionService.createAction(actionRequest);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> updateAction(@PathVariable("id") int id,
                                                  @RequestBody @Valid ActionRequest actionRequest) {
        actionService.updateAction(id, actionRequest);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteStage(@PathVariable("id") int id) {
        actionService.deleteAction(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}

