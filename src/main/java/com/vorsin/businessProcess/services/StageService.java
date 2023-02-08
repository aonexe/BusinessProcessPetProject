package com.vorsin.businessProcess.services;

import com.vorsin.businessProcess.dto.StageRequest;
import com.vorsin.businessProcess.dto.StageResponse;
import com.vorsin.businessProcess.exception.BusinessProcessException;
import com.vorsin.businessProcess.exception.StageException;
import com.vorsin.businessProcess.models.Stage;
import com.vorsin.businessProcess.repositories.BPRepository;
import com.vorsin.businessProcess.repositories.StageRepository;
import com.vorsin.businessProcess.repositories.StageResultRepository;
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
public class StageService {

    private final StageRepository stageRepository;
    private final StageResultRepository stageResultRepository;
    private final UserRepository userRepository;
    private final BPRepository bpRepository;
    private final ModelMapper modelMapper;


    @Autowired
    public StageService(StageRepository stageRepository, StageResultRepository stageResultRepository, UserRepository userRepository, BPRepository bpRepository, ModelMapper modelMapper) {
        this.stageRepository = stageRepository;
        this.stageResultRepository = stageResultRepository;
        this.userRepository = userRepository;
        this.bpRepository = bpRepository;
        this.modelMapper = modelMapper;
    }

    public List<StageResponse> getStages() {
        return stageRepository.findAll()
                .stream().map(stage -> modelMapper.map(stage, StageResponse.class))
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
            modifyStage(stage.get(), stageRequest.getBusinessProcessId(), stageRequest.getTitle());
            stageRepository.save(stage.get());
        } else {
            throw new StageException("Stage not found", HttpStatus.NOT_FOUND);
        }
    }

    public void deleteStage(int id) {
        if (stageRepository.existsById(id)) {
            stageRepository.deleteById(id);
        } else {
            throw new StageException("Stage not found", HttpStatus.NOT_FOUND);
        }
    }

    private void initNewStage(Stage newStage, int businessProcessId) {

        newStage.setBusinessProcess(bpRepository.findById(businessProcessId).get());

        newStage.setCreatedAt(LocalDateTime.now());
        //todo current user from auth
        newStage.setCreatedWho(userRepository.findById(1).get());
        //todo check
        newStage.setStageResult(stageResultRepository.findByName("NOT_STARTED").get());
    }

    private void modifyStage(Stage stage, int businessProcessId, String title) {

        stage.setBusinessProcess(bpRepository.findById(businessProcessId).get());
        stage.setTitle(title);

        stage.setUpdatedAt(LocalDateTime.now());
        //todo current user from auth
        stage.setUpdatedWho(userRepository.findById(1).get());

    }

    private void checkIfBusinessProcessExists(int businessProcessId) {
        if (!bpRepository.existsById(businessProcessId)) {
            throw new BusinessProcessException("Business process not found", HttpStatus.NOT_FOUND);
        }
    }

}

