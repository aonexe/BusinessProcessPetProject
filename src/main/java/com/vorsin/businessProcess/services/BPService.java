package com.vorsin.businessProcess.services;

import com.vorsin.businessProcess.dto.BPRequest;
import com.vorsin.businessProcess.dto.BPResponse;
import com.vorsin.businessProcess.exception.BusinessProcessException;
import com.vorsin.businessProcess.models.BusinessProcess;
import com.vorsin.businessProcess.repositories.BPRepository;
import com.vorsin.businessProcess.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        String requestTitle = bpRequest.getTitle().trim();
        if (requestTitle.isBlank()) {
            throw new BusinessProcessException("Title is empty");
        }
        if (bpRepository.existsByTitle(bpRequest.getTitle())) {
            throw new BusinessProcessException("Title already exists", HttpStatus.CONFLICT);
        } else {
            BusinessProcess newBusinessProcess = modelMapper.map(bpRequest, BusinessProcess.class);
            newBusinessProcess.setTitle(requestTitle);
            initNewBusinessProcess(newBusinessProcess);
            bpRepository.save(newBusinessProcess);
        }
    }

    @Transactional
    public void updateBusinessProcess(int id, BPRequest bpRequest) {
        if (bpRepository.existsById(id)) {
            if (bpRepository.existsByTitle(bpRequest.getTitle().trim())) {
                throw new BusinessProcessException("Title already exists", HttpStatus.CONFLICT);
            }
            BusinessProcess businessProcess = bpRepository.findById(id).get();
            modifyBusinessProcess(businessProcess, bpRequest.getTitle());
            bpRepository.save(businessProcess);
        } else {
            throw new BusinessProcessException("Business process not found", HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public void deleteBusinessProcess(int id) {
        if (bpRepository.existsById(id)) {
            bpRepository.deleteById(id);
        } else {
            throw new BusinessProcessException("Business process not found", HttpStatus.NOT_FOUND);
        }
    }


    private void initNewBusinessProcess(BusinessProcess newBusinessProcess) {
        newBusinessProcess.setCreatedAt(LocalDateTime.now());

        //todo current user from auth
        newBusinessProcess.setCreatedWho(userRepository.findById(1).get());
    }

    private void modifyBusinessProcess(BusinessProcess businessProcess, String title) {

        businessProcess.setTitle(title);

        businessProcess.setUpdatedAt(LocalDateTime.now());
        //todo current user from auth
        businessProcess.setUpdatedWho(userRepository.findById(1).get());
    }

}
