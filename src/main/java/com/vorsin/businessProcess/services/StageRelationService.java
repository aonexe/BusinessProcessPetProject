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
import java.util.ArrayList;
import java.util.Arrays;
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

    public List<StageRelationResponse> getStagesRelations() {
        return stageRelationRepository.findAll()
                .stream().map(stageRelation -> modelMapper.map(stageRelation, StageRelationResponse.class))
                .collect(Collectors.toList());
    }

    public void createStageRelation(StageRelationRequest stageRelationRequest) {

        // Проверяем, что связь не зацикливается на этапе
        checkIfStagesDifferent(stageRelationRequest.getFromStageId(),stageRelationRequest.getToStageId());

        // Проверяем, что такой связи нет
        checkIfStageRelationExists(stageRelationRequest.getFromStageId(),stageRelationRequest.getToStageId());

        // Проверяем, что Stages существуют
        List<Integer> stagesId = new ArrayList<>
                (Arrays.asList(stageRelationRequest.getFromStageId(), stageRelationRequest.getToStageId()));
        checkIfStagesExists(stagesId);

        // Проверяем, что Stages из одного БП
        checkIfStagesFromDifferentBP(stageRelationRequest.getFromStageId(), stageRelationRequest.getToStageId());

        StageRelation stageRelation = modelMapper.map(stageRelationRequest, StageRelation.class);
        initNewStageRelation(stageRelation, stageRelationRequest.getTitle(),
                stageRelationRequest.getFromStageId(), stageRelationRequest.getToStageId());

        stageRelationRepository.save(stageRelation);
    }

    public void updateStageRelation(int id, StageRelationRequest stageRelationRequest) {

        Optional<StageRelation> stageRelation = stageRelationRepository.findById(id);
        if (stageRelation.isPresent()) {

            // Проверяем, что связь не зацикливается на этапе
            checkIfStagesDifferent(stageRelationRequest.getFromStageId(),stageRelationRequest.getToStageId());

            //Проверяем, что такой связи нет
            checkIfStageRelationExists(stageRelationRequest.getFromStageId(),stageRelationRequest.getToStageId());

            // Проверяем, что Stages существуют
            List<Integer> stagesId = new ArrayList<>
                    (Arrays.asList(stageRelationRequest.getFromStageId(), stageRelationRequest.getToStageId()));
            checkIfStagesExists(stagesId);

            // Проверяем, что Stages из одного БП
            checkIfStagesFromDifferentBP(stageRelationRequest.getFromStageId(), stageRelationRequest.getToStageId());

            modifyStageRelation(stageRelation.get(), stageRelationRequest.getTitle(),
                    stageRelationRequest.getFromStageId(), stageRelationRequest.getToStageId());
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

    private void initNewStageRelation(StageRelation newStageRelation,
                                      String title, int fromStageId, int toStageId) {

        // Присваеваем сгенерированный Title, если он не указан
        if (title == null) {

            newStageRelation.setTitle
                    (generateTitle(fromStageId, toStageId));
        } else {
            newStageRelation.setTitle(title);
        }

        newStageRelation.setFromStage(getStage(fromStageId));
        newStageRelation.setToStage(getStage(toStageId));

        newStageRelation.setCreatedAt(LocalDateTime.now());
        //todo current user from auth
        newStageRelation.setCreatedWho(userRepository.findById(1).get());
    }

    private void modifyStageRelation(StageRelation stageRelation, String title, int fromStageId, int toStageId) {

        // Обновляем Title, если указали новый
        if (title != null && !title.equals(stageRelation.getTitle())) {
            stageRelation.setTitle(title);
        }

        stageRelation.setFromStage(getStage(fromStageId));
        stageRelation.setToStage(getStage(toStageId));

        stageRelation.setUpdatedAt(LocalDateTime.now());
        //todo current user from auth
        stageRelation.setUpdatedWho(userRepository.findById(1).get());

    }

    private Stage getStage(int stageId) {
        return stageRepository.findById(stageId).get();
    }

    private void checkIfStagesExists(List<Integer> listID) {
        for (int id : listID) {
            if (!stageRepository.existsById(id)) {
                //todo
                throw new RuntimeException("Stage not found");
            }
        }
    }

    private void checkIfStagesDifferent(int fromStageId, int toStageId) {
        if (fromStageId== toStageId)
            //todo custom exception
            throw new RuntimeException("same stage");
    }

    private void checkIfStagesFromDifferentBP(int fromStageId, int toStageId) {

        int bpIdFromStage = stageRepository.findProcessIdByStageId(fromStageId);
        int bpIdToStage = stageRepository.findProcessIdByStageId(toStageId);

        if (bpIdFromStage != bpIdToStage) {
            //todo
            throw new RuntimeException("stages from different bp!!!");
        }
    }

    private void checkIfStageRelationExists(int fromStageId, int toStageId) {
        //todo
       if(stageRelationRepository.findStageRelationIfExists(fromStageId, toStageId).isPresent())
           //todo custom exception
           throw new ResponseStatusException(HttpStatus.CONFLICT);
    }

    private String generateTitle(int fromStageId, int toStageId) {
        String fromStageTitle = stageRepository.findById(fromStageId).get().getTitle();
        String toStageTitle = stageRepository.findById(toStageId).get().getTitle();

        return "From " + fromStageTitle + " to " + toStageTitle;
    }

}
