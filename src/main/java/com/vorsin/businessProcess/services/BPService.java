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
        return bpRepository.findAll()
                .stream().map(businessProcess -> modelMapper.map(businessProcess, BPResponse.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public void createBusinessProcess(BPRequest bpRequest) {
        if (bpRequest.getTitle().trim().equals("")) {

            //todo
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
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
    public void updateBusinessProcess(int id, BPRequest bpRequest) {
        if (bpRepository.existsById(id)) {
            //todo custom exception
            if (bpRepository.existsByTitle(bpRequest.getTitle())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT);
            }
            BusinessProcess businessProcess = bpRepository.findById(id).get();
            modifyBusinessProcess(businessProcess, bpRequest.getTitle());
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


    private void initNewBusinessProcess(BusinessProcess newBusinessProcess) {
        newBusinessProcess.setCreatedAt(LocalDateTime.now());

        //todo current user from auth
        newBusinessProcess.setCreatedWho(userRepository.findById(2).get());
    }

    private void modifyBusinessProcess(BusinessProcess businessProcess, String title) {

        businessProcess.setTitle(title);

        //todo
        businessProcess.setUpdatedAt(LocalDateTime.now());
        //todo current user from auth
        businessProcess.setUpdatedWho(userRepository.findById(2).get());
    }

}
