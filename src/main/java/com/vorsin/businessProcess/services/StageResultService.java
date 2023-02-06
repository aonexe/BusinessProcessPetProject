package com.vorsin.businessProcess.services;

import com.vorsin.businessProcess.dto.StageResultRequest;
import com.vorsin.businessProcess.dto.StageResultResponse;
import com.vorsin.businessProcess.models.StageResult;
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
public class StageResultService {

    private final StageResultRepository stageResultRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    @Autowired
    public StageResultService(StageResultRepository stageResultRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.stageResultRepository = stageResultRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public List<StageResultResponse> getStagesResults() {
        return stageResultRepository.findAll()
                .stream().map(stageResult -> modelMapper.map(stageResult, StageResultResponse.class))
                .collect(Collectors.toList());
    }


    public void createStageResult(StageResultRequest stageResultRequest) {
        if (stageResultRepository.findByName(stageResultRequest.getName()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        StageResult newStageResult = new StageResult(stageResultRequest.getName());
        initNewStageResult(newStageResult);
        stageResultRepository.save(newStageResult);
    }

    public void updateStageResult(int id, StageResultRequest stageResultRequest) {

        Optional<StageResult> stageResult = stageResultRepository.findById(id);
        if (stageResult.isPresent()) {
            if (stageResultRepository.findByName(stageResultRequest.getName()).isPresent()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT);
            }
            modifyStageResult(stageResult.get(), stageResultRequest.getName());
            stageResultRepository.save(stageResult.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public void deleteStageResult(int id) {
        if (stageResultRepository.existsById(id)) {
            //todo Key (stage_result_id)=(1) is still referenced from table "stage".
            stageResultRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    private void initNewStageResult(StageResult newStageResult) {

        newStageResult.setCreatedAt(LocalDateTime.now());
        //todo current user from auth
        newStageResult.setCreatedWho(userRepository.findById(1).get());
        //todo check
    }

    private void modifyStageResult(StageResult stageResult, String name) {

        stageResult.setName(name);
        stageResult.setUpdatedAt(LocalDateTime.now());
        //todo current user from auth
        stageResult.setUpdatedWho(userRepository.findById(1).get());

    }

}
