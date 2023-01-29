package com.vorsin.businessProcess.services;

import com.vorsin.businessProcess.dto.StageRequest;
import com.vorsin.businessProcess.dto.StageResponse;
import com.vorsin.businessProcess.dto.UserResponse;
import com.vorsin.businessProcess.models.BusinessProcess;
import com.vorsin.businessProcess.models.Stage;
import com.vorsin.businessProcess.models.StageResultEnum;
import com.vorsin.businessProcess.repositories.BPRepository;
import com.vorsin.businessProcess.repositories.StageRepository;
import com.vorsin.businessProcess.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StageService {


    private final StageRepository stageRepository;
    private final UserRepository userRepository;
    private final BPRepository bpRepository;

    private final ModelMapper modelMapper;


    @Autowired
    public StageService(StageRepository stageRepository, UserRepository userRepository, BPRepository bpRepository, ModelMapper modelMapper) {
        this.stageRepository = stageRepository;
        this.userRepository = userRepository;
        this.bpRepository = bpRepository;
        this.modelMapper = modelMapper;
    }

    public List<StageResponse> getStages() {
        return stageRepository.findAll().stream().map(this::convertToStageResponse)
                .collect(Collectors.toList());
    }

    public void createStage(StageRequest stageRequest) {
        Stage stage = modelMapper.map(stageRequest, Stage.class);
        initNewStage(stage, stageRequest);
        stageRepository.save(stage);
    }

    public void updateStage(int id, StageRequest stageRequest) {
        Optional<Stage> stage = stageRepository.findById(id);
        if (stage.isPresent()) {
            initStage(stage.get(), stageRequest);
            stageRepository.save(stage.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public void deleteStage(int id) {
        if (stageRepository.findById(id).isPresent()) {
            stageRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    private StageResponse convertToStageResponse(Stage stage) {

        return modelMapper.map(stage, StageResponse.class);
    }


    private void initNewStage(Stage stage, StageRequest stageRequest) {

        BusinessProcess businessProcess = checkIfBusinessProcessExists(stageRequest);
        stage.setBusinessProcess(businessProcess);

        stage.setCreatedAt(LocalDateTime.now());
        //todo current user from auth
        stage.setCreatedWho(userRepository.findById(2).get());
        stage.setStageResult(StageResultEnum.NOT_STARTED);
    }

    private void initStage(Stage stage, StageRequest stageRequest) {

        BusinessProcess businessProcess = checkIfBusinessProcessExists(stageRequest);
        stage.setBusinessProcess(businessProcess);

        stage.setTitle(stageRequest.getTitle());

        stage.setUpdatedAt(LocalDateTime.now());
        //todo current user from auth
        stage.setUpdatedWho(userRepository.findById(2).get());

    }

    private BusinessProcess checkIfBusinessProcessExists(StageRequest stageRequest) {
        Optional<BusinessProcess> businessProcess = bpRepository.findById(stageRequest.getBusinessProcessId());
        if (businessProcess.isEmpty()) {
            //todo
            throw new RuntimeException("bp not found");
        }
        return businessProcess.get();
    }

}

