package com.vorsin.businessProcess.services;


import com.vorsin.businessProcess.dto.BusinessProcessDTO;
import com.vorsin.businessProcess.models.BusinessProcess;
import com.vorsin.businessProcess.models.User;
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
        return businessProcessRepository.findAll().stream().map(this::convertToBusinessProcessDTO).collect(Collectors.toList());
    }


    @Transactional
    public void createBusinessProcess(BusinessProcessDTO businessProcessDTO) {
        if (isBusinessProcessPresent(businessProcessDTO.getTitle())) {
            //todo custom status
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        } else {
            BusinessProcess newBusinessProcess = convertToBusinessProcess(businessProcessDTO);
            enrichNewBusinessProcess(newBusinessProcess);
            businessProcessRepository.save(newBusinessProcess);
        }
    }

    @Transactional
    public void updateBusinessProcess(BusinessProcessDTO businessProcessDTO, int id) {
        if (isBusinessProcessPresent(id)) {

            //todo одинаковые тайтлы 500
            BusinessProcess businessProcess = convertToBusinessProcess(businessProcessDTO, id);
            enrichBusinessProcess(businessProcess, id);
            businessProcessRepository.save(businessProcess);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public void deleteBusinessProcess(int id) {
        if (isBusinessProcessPresent(id)) {
            BusinessProcess businessProcess = businessProcessRepository.findById(id).get();
            businessProcessRepository.deleteById(businessProcess.getId());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    private boolean isBusinessProcessPresent(int id) {
        Optional<BusinessProcess> businessProcess = businessProcessRepository.findById(id);
        return businessProcess.isPresent();
    }

    private boolean isBusinessProcessPresent(String title) {
        Optional<BusinessProcess> businessProcess = businessProcessRepository.findByTitle(title);
        return businessProcess.isPresent();
    }


    private BusinessProcessDTO convertToBusinessProcessDTO(BusinessProcess businessProcess) {
        return modelMapper.map(businessProcess, BusinessProcessDTO.class);
    }

    private BusinessProcess convertToBusinessProcess(BusinessProcessDTO businessProcessDTO) {
        return modelMapper.map(businessProcessDTO, BusinessProcess.class);
    }

    private BusinessProcess convertToBusinessProcess(BusinessProcessDTO businessProcessDTO, int id) {
        BusinessProcess businessProcess = convertToBusinessProcess(businessProcessDTO);

        return businessProcess;
    }

    private void enrichNewBusinessProcess(BusinessProcess businessProcess) {
        businessProcess.setCreatedAt(LocalDateTime.now());

        //todo
        businessProcess.setCreatedWho("ROLE_ADMIN");
    }

    private void enrichBusinessProcess(BusinessProcess newBusinessProcess, int id) {
        BusinessProcess oldBusinessProcess = businessProcessRepository.findById(id).get();
        newBusinessProcess.setId(oldBusinessProcess.getId());
        newBusinessProcess.setCreatedAt(oldBusinessProcess.getCreatedAt());
        newBusinessProcess.setCreatedWho(oldBusinessProcess.getCreatedWho());

        //todo
        newBusinessProcess.setUpdatedAt(LocalDateTime.now());
        newBusinessProcess.setUpdatedWho(new User());
    }

}
