package com.vorsin.businessProcess.services;


import com.vorsin.businessProcess.dto.BPRequest;
import com.vorsin.businessProcess.dto.BPResponse;
import com.vorsin.businessProcess.models.BusinessProcess;
import com.vorsin.businessProcess.repositories.BPRepository;
import com.vorsin.businessProcess.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BPService {

    private final BPRepository bpRepository;
    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public BPService(BPRepository bpRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.bpRepository = bpRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public List<BPResponse> getBusinessProcesses() {
        return bpRepository.findAll().stream().map(this::convertToBPResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void createBusinessProcess(BPRequest bpRequest) {
        if (bpRepository.existsByTitle(bpRequest.getTitle())) {
            //todo custom exception
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        } else {
            BusinessProcess newBusinessProcess = modelMapper.map(bpRequest, BusinessProcess.class);
            initNewBusinessProcess(newBusinessProcess);
            bpRepository.save(newBusinessProcess);
        }
    }

    @Transactional
    public void updateBusinessProcess(BPRequest bpRequest, int id) {
        if (bpRepository.existsById(id)) {
            //todo custom exception
            if (bpRepository.existsByTitle(bpRequest.getTitle())){
                throw new ResponseStatusException(HttpStatus.CONFLICT);
            }
            BusinessProcess businessProcess = bpRepository.findById(id).get();
            initBusinessProcess(businessProcess, bpRequest);
            bpRepository.save(businessProcess);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public void deleteBusinessProcess(int id) {
        if (bpRepository.existsById(id)) {
            bpRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


    private BPResponse convertToBPResponse(BusinessProcess businessProcess) {
        return modelMapper.map(businessProcess, BPResponse.class);
    }

    private void initNewBusinessProcess(BusinessProcess businessProcess) {
        businessProcess.setCreatedAt(LocalDateTime.now());

        //todo current user from auth
        businessProcess.setCreatedWho(userRepository.findById(2).get());
    }

    private void initBusinessProcess(BusinessProcess businessProcess, BPRequest bpRequest) {

        //todo check if this title in db
        businessProcess.setTitle(bpRequest.getTitle());

        //todo
        businessProcess.setUpdatedAt(LocalDateTime.now());
        //todo current user from auth
        businessProcess.setUpdatedWho(userRepository.findById(2).get());
    }

}
