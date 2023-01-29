package com.vorsin.businessProcess.services;

import com.vorsin.businessProcess.dto.UserRequest;
import com.vorsin.businessProcess.dto.UserResponse;
import com.vorsin.businessProcess.models.User;
import com.vorsin.businessProcess.models.UserRoleEnum;
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
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream().map(this::convertToUserViewResponse).collect(Collectors.toList());
    }

    @Transactional
    public void createUser(UserRequest userRequest) {
        if (userRepository.findByUsernameOrEmail(userRequest.getUsername(), userRequest.getEmail()).isPresent()) {
            //todo custom status
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        } else {
            User newUser = modelMapper.map(userRequest, User.class);
            initNewUser(newUser);
            userRepository.save(newUser);
        }
    }

    @Transactional
    public void updateUser(UserRequest userRequest, int id) {
        if (userRepository.findById(id).isPresent()) {
            if (userRepository.findByUsernameOrEmail(userRequest.getUsername(), userRequest.getEmail()).isPresent()){
                throw new ResponseStatusException(HttpStatus.CONFLICT);
            }
            User user = userRepository.findById(id).get();
            initUser(user, userRequest);
            userRepository.save(user);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public void deleteUser(int id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        }  else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    private UserResponse convertToUserViewResponse(User user) {
        return modelMapper.map(user, UserResponse.class);
    }


    private void initNewUser(User newUser) {
        newUser.setCreatedAt(LocalDateTime.now());
        //todo current user from auth
        newUser.setCreatedWho(userRepository.findById(2).get());
        newUser.setUserRole(UserRoleEnum.USER);
    }

    private void initUser(User user, UserRequest userRequest) {

        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setDateOfBirth(userRequest.getDateOfBirth());
        user.setEmail(userRequest.getEmail());
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());

        user.setUpdatedAt(LocalDateTime.now());
        //todo current user from auth
        user.setUpdatedWho(userRepository.findById(2).get());
    }
}
