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
import java.util.Date;
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
        return userRepository.findAll()
                .stream().map(user -> modelMapper.map(user, UserResponse.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public void createUser(UserRequest userRequest) {
        if (userRepository.existsByUsernameOrEmail(userRequest.getUsername(), userRequest.getEmail())) {
            //todo custom status
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        } else {
            User newUser = modelMapper.map(userRequest, User.class);
            initNewUser(newUser);
            userRepository.save(newUser);
        }
    }

    @Transactional
    public void updateUser(int id, UserRequest userRequest) {
        if (userRepository.existsById(id)) {
            if (userRepository.existsByUsernameOrEmail(userRequest.getUsername(), userRequest.getEmail())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT);
            }
            User user = userRepository.findById(id).get();
            modifyUser(user, userRequest.getFirstName(), userRequest.getLastName(), userRequest.getDateOfBirth(),
                    userRequest.getEmail(), userRequest.getUsername(), userRequest.getPassword());
            userRepository.save(user);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public void deleteUser(int id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    private void initNewUser(User newUser) {
        newUser.setCreatedAt(LocalDateTime.now());
        //todo current user from auth
        newUser.setCreatedWho(userRepository.findById(2).get());
        newUser.setUserRole(UserRoleEnum.USER);
    }

    private void modifyUser(User user, String firstName, String lastName, Date dateOfBirth,
                            String email, String username, String password) {

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setDateOfBirth(dateOfBirth);
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);

        user.setUpdatedAt(LocalDateTime.now());
        //todo current user from auth
        user.setUpdatedWho(userRepository.findById(2).get());
    }
}
