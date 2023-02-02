package com.vorsin.businessProcess.services;

import com.vorsin.businessProcess.dto.StageRequest;
import com.vorsin.businessProcess.dto.StageResponse;
import com.vorsin.businessProcess.models.BusinessProcess;
import com.vorsin.businessProcess.models.Stage;
import com.vorsin.businessProcess.models.StageResultEnum;
import com.vorsin.businessProcess.repositories.BPRepository;
import com.vorsin.businessProcess.repositories.StageRepository;
import com.vorsin.businessProcess.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
        checkIfBusinessProcessExists(stageRequest.getBusinessProcessId());
        Stage newStage = modelMapper.map(stageRequest, Stage.class);
        initNewStage(newStage, stageRequest.getBusinessProcessId());
        stageRepository.save(newStage);
    }

    public void updateStage(int id, StageRequest stageRequest) {
        Optional<Stage> stage = stageRepository.findById(id);
        if (stage.isPresent()) {
            checkIfBusinessProcessExists(stageRequest.getBusinessProcessId());
            initStage(stage.get(), stageRequest.getBusinessProcessId(), stageRequest.getTitle());
            stageRepository.save(stage.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public void deleteStage(int id) {
        if (stageRepository.existsById(id)) {
            stageRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    private StageResponse convertToStageResponse(Stage stage) {

        return modelMapper.map(stage, StageResponse.class);
    }


    private void initNewStage(Stage stage, int businessProcessId) {

        stage.setBusinessProcess(bpRepository.findById(businessProcessId).get());

        stage.setCreatedAt(LocalDateTime.now());
        //todo current user from auth
        stage.setCreatedWho(userRepository.findById(2).get());
        stage.setStageResult(StageResultEnum.NOT_STARTED);
    }

    private void initStage(Stage stage, int businessProcessId, String title) {

        stage.setBusinessProcess(bpRepository.findById(businessProcessId).get());
        stage.setTitle(title);

        stage.setUpdatedAt(LocalDateTime.now());
        //todo current user from auth
        stage.setUpdatedWho(userRepository.findById(2).get());

    }

    private void checkIfBusinessProcessExists(int businessProcessId) {
        if (!bpRepository.existsById(businessProcessId)) {
            throw new RuntimeException("bp not found");
        }
    }

}

