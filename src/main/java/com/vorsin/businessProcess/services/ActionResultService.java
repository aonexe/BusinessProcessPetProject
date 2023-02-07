package com.vorsin.businessProcess.services;

import com.vorsin.businessProcess.dto.ActionResultRequest;
import com.vorsin.businessProcess.dto.ActionResultResponse;
import com.vorsin.businessProcess.models.ActionResult;
import com.vorsin.businessProcess.repositories.ActionResultRepository;
import com.vorsin.businessProcess.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ActionResultService {

    private final ActionResultRepository actionResultRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    @Autowired
    public ActionResultService( ActionResultRepository actionResultRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.actionResultRepository = actionResultRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public List<ActionResultResponse> getActionsResults() {
        return actionResultRepository.findAll()
                .stream().map(actionResult -> modelMapper.map(actionResult, ActionResultResponse.class))
                .collect(Collectors.toList());
    }


    public void createActionResult(ActionResultRequest actionResultRequest) {
        if (actionResultRepository.findByName(actionResultRequest.getName()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        ActionResult newActionResult = new ActionResult(actionResultRequest.getName());
        initNewActionResult(newActionResult);
        actionResultRepository.save(newActionResult);
    }

    public void updateActionResult(int id, ActionResultRequest actionResultRequest) {

        Optional<ActionResult> actionResult = actionResultRepository.findById(id);
        if (actionResult.isPresent()) {
            if (actionResultRepository.findByName(actionResultRequest.getName()).isPresent()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT);
            }
            modifyActionResult(actionResult.get(), actionResultRequest.getName());
            actionResultRepository.save(actionResult.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public void deleteActionResult(int id) {
        if (actionResultRepository.existsById(id)) {
            //todo Key (action_result_id)=(1) is still referenced from table "action".
            actionResultRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    private void initNewActionResult(ActionResult newActionResult) {

        newActionResult.setCreatedAt(LocalDateTime.now());
        //todo current user from auth
        newActionResult.setCreatedWho(userRepository.findById(1).get());
        //todo check
    }

    private void modifyActionResult(ActionResult actionResult, String name) {

        actionResult.setName(name);
        actionResult.setUpdatedAt(LocalDateTime.now());
        //todo current user from auth
        actionResult.setUpdatedWho(userRepository.findById(1).get());

    }

}
