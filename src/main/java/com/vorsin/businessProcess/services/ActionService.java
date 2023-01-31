package com.vorsin.businessProcess.services;

import com.vorsin.businessProcess.dto.ActionRequest;
import com.vorsin.businessProcess.dto.ActionResponse;
import com.vorsin.businessProcess.models.Action;
import com.vorsin.businessProcess.models.Stage;
import com.vorsin.businessProcess.models.User;
import com.vorsin.businessProcess.repositories.ActionRepository;
import com.vorsin.businessProcess.repositories.BPRepository;
import com.vorsin.businessProcess.repositories.StageRepository;
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
public class ActionService {

    private final StageRepository stageRepository;
    private final UserRepository userRepository;
    private final BPRepository bpRepository;

    private final ActionRepository actionRepository;
    private final ModelMapper modelMapper;


    @Autowired
    public ActionService(StageRepository stageRepository, UserRepository userRepository, BPRepository bpRepository, ActionRepository actionRepository, ModelMapper modelMapper) {
        this.stageRepository = stageRepository;
        this.userRepository = userRepository;
        this.bpRepository = bpRepository;
        this.actionRepository = actionRepository;
        this.modelMapper = modelMapper;
    }

    public List<ActionResponse> getActions() {
        return actionRepository.findAll().stream().map(this::convertToActionResponse)
                .collect(Collectors.toList());
    }

    public void createAction(ActionRequest actionRequest) {
        Action action = modelMapper.map(actionRequest, Action.class);
        initNewAction(action, actionRequest);
        actionRepository.save(action);
    }

    public void updateAction(int id, ActionRequest actionRequest) {
        Optional<Action> action = actionRepository.findById(id);
        if (action.isPresent()) {
            initAction(action.get(), actionRequest);
            actionRepository.save(action.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public void deleteAction(int id) {
        if (actionRepository.existsById(id)) {
            actionRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    private ActionResponse convertToActionResponse(Action action) {
        return modelMapper.map(action, ActionResponse.class);
    }


    private void initNewAction(Action action, ActionRequest actionRequest) {

        Stage stage = checkIfStageExists(actionRequest);
        action.setStage(stage);

        User taskOwner = checkIfUserExists(actionRequest);
        action.setTaskOwner(taskOwner);

        action.setCreatedAt(LocalDateTime.now());
        //todo current user from auth
        action.setCreatedWho(userRepository.findById(2).get());
        action.setActionResult(null);
    }

    private void initAction(Action action, ActionRequest actionRequest) {

        Stage stage = checkIfStageExists(actionRequest);
        action.setStage(stage);

        User taskOwner = checkIfUserExists(actionRequest);
        action.setTaskOwner(taskOwner);

        action.setUpdatedAt(LocalDateTime.now());
        //todo current user from auth
        action.setUpdatedWho(userRepository.findById(2).get());

    }

    private Stage checkIfStageExists(ActionRequest actionRequest) {
        Optional<Stage> stage = stageRepository.findById(actionRequest.getStageId());
        if (stage.isEmpty()) {
            //todo
            throw new RuntimeException("stage not found");
        }
        return stage.get();
    }

    private User checkIfUserExists(ActionRequest actionRequest) {
        Optional<User> user = userRepository.findById(actionRequest.getTaskOwnerId());
        if (user.isEmpty()) {
            //todo
            throw new RuntimeException("user not found");
        }
        return user.get();
    }

}

