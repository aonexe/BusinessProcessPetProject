package com.vorsin.businessProcess.services;

import com.vorsin.businessProcess.dto.StageDTO;
import com.vorsin.businessProcess.models.BusinessProcess;
import com.vorsin.businessProcess.models.Stage;
import com.vorsin.businessProcess.models.StageResultEnum;
import com.vorsin.businessProcess.repositories.BusinessProcessRepository;
import com.vorsin.businessProcess.repositories.StageRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StageService {

    private final BusinessProcessRepository businessProcessRepository;
    private final StageRepository stageRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public StageService(BusinessProcessRepository businessProcessRepository,
                        StageRepository stageRepository, ModelMapper modelMapper) {
        this.businessProcessRepository = businessProcessRepository;
        this.stageRepository = stageRepository;
        this.modelMapper = modelMapper;
    }

    public List<StageDTO> getStages(int businessProcessId) {
        var allStages = stageRepository.findAllByBusinessProcess_Id(businessProcessId);

        return allStages.stream().map(this::convertToStageDTO)
                .collect(Collectors.toList());
    }

    public void createStage(int businessProcessId, StageDTO stageDTO) {
        Stage stage = convertToStage(stageDTO);
        enrichNewStage(businessProcessId, stage);
        stageRepository.save(stage);
    }

    public void updateStage(StageDTO stageDTO, int businessProcessId, int stageId) {

        //todo пресент в отдельный метод
        Optional<Stage> stage = stageRepository.findByBusinessProcess_IdAndId(businessProcessId, stageId);
        if (stage.isPresent()) {
            //todo смена тайтла и апдейт в отдельный метод
            stage.get().setTitle(stageDTO.getTitle());
            stageRepository.save(stage.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


    public void deleteStage(int businessProcessId, int stageId) {
        //todo пресент в отдельный метод
        Optional<Stage> stage = stageRepository.findByBusinessProcess_IdAndId(businessProcessId, stageId);
        if (stage.isPresent()){
            stageRepository.delete(stage.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    private  StageDTO convertToStageDTO(Stage stage) {
        return modelMapper.map(stage, StageDTO.class);
    }
    private Stage convertToStage(StageDTO stageDTO) {
        return modelMapper.map(stageDTO, Stage.class);
    }

    private void enrichNewStage(int businessProcessId, Stage stage) {
        Optional<BusinessProcess> businessProcess = businessProcessRepository.findById(businessProcessId);
        if (businessProcess.isPresent()) {
            stage.setStageResult(StageResultEnum.NOT_STARTED);
            stage.setBusinessProcess(businessProcess.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
