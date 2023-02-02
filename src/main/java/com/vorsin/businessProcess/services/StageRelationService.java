package com.vorsin.businessProcess.services;

import com.vorsin.businessProcess.dto.StageRelationRequest;
import com.vorsin.businessProcess.dto.StageRelationResponse;
import com.vorsin.businessProcess.models.Stage;
import com.vorsin.businessProcess.models.StageRelation;
import com.vorsin.businessProcess.repositories.StageRelationRepository;
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
public class StageRelationService {

    private final StageRelationRepository stageRelationRepository;
    private final StageRepository stageRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public StageRelationService(StageRelationRepository stageRelationRepository, StageRepository stageRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.stageRelationRepository = stageRelationRepository;
        this.stageRepository = stageRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public List<StageRelationResponse> getStageRelations() {
        return stageRelationRepository.findAll().stream().map(this::convertToStageRelationResponse)
                .collect(Collectors.toList());
    }

    public void createStageRelation(StageRelationRequest stageRelationRequest) {

        // Проверяем, что Stages существуют
        checkIfStagesExists(stageRelationRequest.getFromStageId(), stageRelationRequest.getToStageId());

        StageRelation stageRelation = modelMapper.map(stageRelationRequest, StageRelation.class);
        initNewStageRelation(stageRelation, stageRelationRequest);
        stageRelationRepository.save(stageRelation);
    }

    public void updateStageRelation(int id, StageRelationRequest stageRelationRequest) {

        // Проверяем, что Stages существуют
        checkIfStagesExists(stageRelationRequest.getFromStageId(), stageRelationRequest.getToStageId());

        Optional<StageRelation> stageRelation = stageRelationRepository.findById(id);
        if (stageRelation.isPresent()) {
            initStage(stageRelation.get(), stageRelationRequest);
            stageRelationRepository.save(stageRelation.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public void deleteStageRelation(int id) {
        if (stageRelationRepository.existsById(id)) {
            stageRelationRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    private StageRelationResponse convertToStageRelationResponse(StageRelation stageRelation) {
        return modelMapper.map(stageRelation, StageRelationResponse.class);
    }


    private void initNewStageRelation(StageRelation stageRelation, StageRelationRequest stageRelationRequest) {

        // Присваеваем сгенерированный Title
        stageRelation.setTitle
                (generateTitle(stageRelationRequest.getFromStageId(), stageRelationRequest.getToStageId()));

        stageRelation.setFromStage(getStage(stageRelationRequest.getFromStageId()));
        stageRelation.setToStage(getStage(stageRelationRequest.getToStageId()));

        stageRelation.setCreatedAt(LocalDateTime.now());
        //todo current user from auth
        stageRelation.setCreatedWho(userRepository.findById(2).get());
    }

    private void initStage(StageRelation stageRelation, StageRelationRequest stageRelationRequest) {

        // Обновляем Title
        stageRelation.setTitle
                (generateTitle(stageRelationRequest.getFromStageId(), stageRelationRequest.getToStageId()));

        stageRelation.setFromStage(getStage(stageRelationRequest.getFromStageId()));
        stageRelation.setToStage(getStage(stageRelationRequest.getToStageId()));

        stageRelation.setUpdatedAt(LocalDateTime.now());
        //todo current user from auth
        stageRelation.setUpdatedWho(userRepository.findById(2).get());

    }

    private Stage getStage(int stageId) {
        return stageRepository.findById(stageId).get();
    }
    private void checkIfStagesExists(int fromStageId, int toStageId) {
        if (!stageRepository.existsById(fromStageId)) {
            //todo
            throw new RuntimeException("fromstage not found");
        }
        if (!stageRepository.existsById(toStageId)) {
            //todo
            throw new RuntimeException("tostage  not found");
        }
    }

    private String generateTitle(int fromStageId, int toStageId) {
        String fromStageTitle = stageRepository.findById(fromStageId).get().getTitle();
        String toStageTitle = stageRepository.findById(toStageId).get().getTitle();


        return "From " + fromStageTitle + " to " + toStageTitle;

    }
}
