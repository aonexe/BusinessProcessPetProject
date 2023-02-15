package com.vorsin.businessProcess.services;

import com.vorsin.businessProcess.dto.UserRequest;
import com.vorsin.businessProcess.dto.UserResponse;
import com.vorsin.businessProcess.exception.UserException;
import com.vorsin.businessProcess.models.Role;
import com.vorsin.businessProcess.models.User;
import com.vorsin.businessProcess.repositories.RoleRepository;
import com.vorsin.businessProcess.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    public List<UserResponse> getUsers() {
        return userRepository.findAll()
                .stream().map(user -> modelMapper.map(user, UserResponse.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public void createUser(UserRequest userRequest) {
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new UserException("User with this username already exists", HttpStatus.CONFLICT);
        } else if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new UserException("User with this email already exists", HttpStatus.CONFLICT);
        } else {
            Role roles = roleRepository.findByName("USER").get();
            var newUser = new User()
                    .firstName(userRequest.getFirstName())
                    .lastName(userRequest.getLastName())
                    .dateOfBirth(userRequest.getDateOfBirth())
                    .email(userRequest.getEmail())
                    .username(userRequest.getUsername())
                    .password(passwordEncoder.encode(userRequest.getPassword()))
                    .roles(Collections.singletonList(roles))
                    .createdAt(LocalDateTime.now())
                    // todo current user from auth
                    .createdWho(userRepository.findById(1).get());
            userRepository.save(newUser);
        }
    }

    @Transactional
    public void updateUser(int id, UserRequest userRequest) {
        if (userRepository.existsById(id)) {
            if (userRepository.existsByUsernameOrEmail(userRequest.getUsername(), userRequest.getEmail())) {
                throw new UserException("User already exists", HttpStatus.CONFLICT);
            }
            User user = userRepository.findById(id).get();
            modifyUser(user, userRequest.getFirstName(), userRequest.getLastName(), userRequest.getDateOfBirth(),
                    userRequest.getEmail(), userRequest.getUsername(), userRequest.getPassword());
            userRepository.save(user);
        } else {
            throw new UserException("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public void deleteUser(int id) {
        if (userRepository.existsById(id)) {
            //todo Key  is still referenced from table
            userRepository.deleteById(id);
        } else {
            throw new UserException("User not found", HttpStatus.NOT_FOUND);
        }
    }

    private void modifyUser(User user, String firstName, String lastName, Date dateOfBirth,
                            String email, String username, String password) {

        user.firstName(firstName);
        user.lastName(lastName);
        user.dateOfBirth(dateOfBirth);
        user.email(email);
        user.username(username);
        user.password(password);

        user.updatedAt(LocalDateTime.now());
        // todo current user from auth
        user.updatedWho(userRepository.findById(1).get());
    }
}
