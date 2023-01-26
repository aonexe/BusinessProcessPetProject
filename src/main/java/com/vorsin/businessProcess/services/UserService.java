package com.vorsin.businessProcess.services;

import com.vorsin.businessProcess.dto.UserRequest;
import com.vorsin.businessProcess.dto.UserViewResponse;
import com.vorsin.businessProcess.models.User;
import com.vorsin.businessProcess.models.UserRole;
import com.vorsin.businessProcess.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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

    public List<UserViewResponse> getUsers() {
        return userRepository.findAll().stream().map(this::convertToUserViewResponse).collect(Collectors.toList());
    }

    @Transactional
    public void createUser(UserRequest userRequest) {
        if (isUserPresent(userRequest.getUsername())) {
            //todo custom status
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        } else {
            User newUser = modelMapper.map(userRequest, User.class);
            initNewUser(newUser);
            userRepository.save(newUser);
        }
    }

    @Transactional
    public void updateUser(UserRequest userRequest, String username) {
        if (isUserPresent(username)) {
            //todo одинаковые юзернеймы или емэйлы
            User user = userRepository.findByUsername(username).get();
            initUser(user, userRequest);
            userRepository.save(user);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


    @Transactional
    public void deleteUser(String username) {
        if (isUserPresent(username)) {
            User employee = userRepository.findByUsername(username).get();
            userRepository.deleteById(employee.getId());
        }
    }

    private boolean isUserPresent(String username) {
        Optional<User> employee = userRepository.findByUsername(username);
        return employee.isPresent();
    }

    private UserViewResponse convertToUserViewResponse(User user) {
        return modelMapper.map(user, UserViewResponse.class);
    }


    //todo
    private void initNewUser(User newUser) {
        newUser.setCreatedAt(LocalDateTime.now());
        //todo
        newUser.setCreatedWho("ROLE_ADMIN");
        newUser.setUserRole(UserRole.USER);
    }

    private void initUser(User user, UserRequest userRequest) {
        for (Field u: userRequest.getClass().getDeclaredFields()) {
            if (u!=null) {
                System.out.println(u.toString());
            }
        }

        //todo
        user.setUpdatedAt(LocalDateTime.now());
        user.setUpdatedWho(new User());
    }

}
