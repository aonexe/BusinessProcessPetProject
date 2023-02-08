package com.vorsin.businessProcess.services;

import com.vorsin.businessProcess.dto.ActionRequest;
import com.vorsin.businessProcess.dto.ActionResponse;
import com.vorsin.businessProcess.exception.ActionException;
import com.vorsin.businessProcess.models.Action;
import com.vorsin.businessProcess.models.Stage;
import com.vorsin.businessProcess.models.User;
import com.vorsin.businessProcess.repositories.ActionRepository;
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
    private final ActionRepository actionRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ActionService(StageRepository stageRepository, UserRepository userRepository, ActionRepository actionRepository, ModelMapper modelMapper) {
        this.stageRepository = stageRepository;
        this.userRepository = userRepository;
        this.actionRepository = actionRepository;
        this.modelMapper = modelMapper;
    }

    public List<ActionResponse> getActions() {
        return actionRepository.findAll()
                .stream().map(action -> modelMapper.map(action, ActionResponse.class))
                .collect(Collectors.toList());
    }

    public void createAction(ActionRequest actionRequest) {
        Action newAction = modelMapper.map(actionRequest, Action.class);
        initNewAction(newAction, actionRequest.getStageId(), actionRequest.getTaskOwnerId());
        actionRepository.save(newAction);
    }

    public void updateAction(int id, ActionRequest actionRequest) {
        Optional<Action> action = actionRepository.findById(id);
        if (action.isPresent()) {
            modifyAction(action.get(), actionRequest.getStageId(), actionRequest.getTaskOwnerId());
            actionRepository.save(action.get());
        } else {
            throw new ActionException("Action not found", HttpStatus.NOT_FOUND);
        }
    }

    public void deleteAction(int id) {
        if (actionRepository.existsById(id)) {
            actionRepository.deleteById(id);
        } else {
            throw new ActionException("Action not found", HttpStatus.NOT_FOUND);
        }
    }

    private void initNewAction(Action newAction, int stageId, int taskOwnerId) {

        newAction.setStage(getStageIfExists(stageId));
        newAction.setTaskOwner(getUserIfExists(taskOwnerId));

        newAction.setCreatedAt(LocalDateTime.now());
        // todo current user from auth
        newAction.setCreatedWho(userRepository.findById(1).get());

        newAction.setActionResult(null);
    }

    private void modifyAction(Action action, int stageId, int taskOwnerId) {

        action.setStage(getStageIfExists(stageId));

        action.setTaskOwner(getUserIfExists(taskOwnerId));

        action.setUpdatedAt(LocalDateTime.now());
        //todo current user from auth
        action.setUpdatedWho(userRepository.findById(1).get());

    }

    private Stage getStageIfExists(int stageId) {
        Optional<Stage> stage = stageRepository.findById(stageId);
        if (stage.isEmpty()) {
            //todo
            throw new RuntimeException("stage not found");
        }
        return stage.get();
    }

    private User getUserIfExists(int userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            //todo
            throw new RuntimeException("user not found");
        }
        return user.get();
    }
}

