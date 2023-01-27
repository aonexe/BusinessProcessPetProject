package com.vorsin.businessProcess.services;

import com.vorsin.businessProcess.dto.UserRequest;
import com.vorsin.businessProcess.dto.UserViewResponse;
import com.vorsin.businessProcess.models.User;
import com.vorsin.businessProcess.models.UserRoleEnum;
import com.vorsin.businessProcess.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
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
        if (userRepository.findByUsername(userRequest.getUsername()).isPresent()) {
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
            //todo одинаковые юзернеймы или емэйлы
            User user = userRepository.findById(id).get();
            initUser(user, userRequest);
            userRepository.save(user);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public void deleteUser(int id) {
        if (isUserPresent(id)) {
            userRepository.deleteById(id);
        }
    }

    private boolean isUserPresent(int id) {
        Optional<User> employee = userRepository.findById(id);
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
        newUser.setUserRole(UserRoleEnum.USER);
    }

    private void initUser(User user, UserRequest userRequest) {

        Class<?> objClass = userRequest.getClass();
        for (Field field : objClass.getDeclaredFields()) {
            // Invoke the getter method on the UserRequest object.
            Object objField;
            try {
                objField = new PropertyDescriptor(field.getName(),
                        UserRequest.class).getReadMethod().invoke(userRequest);
            } catch (IllegalAccessException | InvocationTargetException | IntrospectionException e) {
                throw new RuntimeException(e);
            }

            // Invoke the setter method on the User object.
            try {
                new PropertyDescriptor(field.getName(), User.class)
                        .getWriteMethod().invoke(user, objField);
            } catch (IllegalAccessException | InvocationTargetException | IntrospectionException e) {
                throw new RuntimeException(e);
            }
        }

        user.setUpdatedAt(LocalDateTime.now());
        //todo current user from auth
        user.setUpdatedWho(userRepository.findByUsername("aonexe").get());
    }
}
