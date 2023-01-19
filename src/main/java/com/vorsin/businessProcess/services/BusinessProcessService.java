package com.vorsin.businessProcess.services;


import com.vorsin.businessProcess.dto.BusinessProcessDTO;
import com.vorsin.businessProcess.models.BusinessProcess;
import com.vorsin.businessProcess.repositories.BusinessProcessRepository;
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
public class BusinessProcessService {

    private final BusinessProcessRepository businessProcessRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public BusinessProcessService(BusinessProcessRepository businessProcessRepository, ModelMapper modelMapper) {
        this.businessProcessRepository = businessProcessRepository;
        this.modelMapper = modelMapper;
    }

    public List<BusinessProcessDTO> getBusinessProcess() {
        return businessProcessRepository.findAll().stream().map(this::convertToBusinessProcessViewResponse).collect(Collectors.toList());
    }


    @Transactional
    public void createBusinessProcess(BusinessProcessDTO businessProcessDTO) {
        Optional<BusinessProcess> businessProcess = businessProcessRepository.findByTitle(businessProcessDTO.getTitle());
        if (businessProcess.isPresent()) {
            //todo custom status
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        } else {
            BusinessProcess newBusinessProcess = convertToBusinessProcess(businessProcessDTO);
            enrichBusinessProcess(newBusinessProcess);
            businessProcessRepository.save(newBusinessProcess);
        }
    }

    @Transactional
    public void deleteBusinessProcess(int id) {
        Optional<BusinessProcess> businessProcess = businessProcessRepository.findById(id);
        if (businessProcess.isPresent()) {
            businessProcessRepository.deleteById(businessProcess.get().getId());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    private BusinessProcessDTO convertToBusinessProcessViewResponse(BusinessProcess businessProcess) {
        return modelMapper.map(businessProcess, BusinessProcessDTO.class);
    }

    private BusinessProcess convertToBusinessProcess(BusinessProcessDTO businessProcessDTO) {
        return modelMapper.map(businessProcessDTO, BusinessProcess.class);
    }

    private void enrichBusinessProcess(BusinessProcess businessProcess) {
        businessProcess.setCreatedAt(LocalDateTime.now());
        businessProcess.setCreatedWho("ROLE_ADMIN");
    }
}
