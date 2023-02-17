package com.vorsin.businessProcess.services;

import com.vorsin.businessProcess.dto.UserRequest;
import com.vorsin.businessProcess.dto.UserResponse;
import com.vorsin.businessProcess.exception.UserException;
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
import java.util.Optional;
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
        return userRepository.findAllEnabledUser()
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
            var newUser = new User();
            newUser.setFirstName(userRequest.getFirstName());
            newUser.setLastName(userRequest.getLastName());
            newUser.setDateOfBirth(userRequest.getDateOfBirth());
            newUser.setEmail(userRequest.getEmail());
            newUser.setUsername(userRequest.getUsername());
            newUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            newUser.setRoles(Collections.singletonList(roleRepository.findByName("USER").get()));
            newUser.setCreatedAt(LocalDateTime.now());
            // todo current user from auth
            newUser.setCreatedWho(userRepository.findById(1).get());

            userRepository.save(newUser);
        }
    }

    @Transactional
    public void updateUser(int id, UserRequest userRequest) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            if (!user.get().getUsername().equalsIgnoreCase(userRequest.getUsername())) {
                if (userRepository.existsByUsername(userRequest.getUsername())) {
                    throw new UserException("This username is already taken", HttpStatus.CONFLICT);
                }
            }
            if (!user.get().getEmail().equalsIgnoreCase(userRequest.getEmail())) {
                if (userRepository.existsByEmail(userRequest.getEmail())) {
                    throw new UserException("This email is already taken", HttpStatus.CONFLICT);
                }
            }

            modifyUser(user.get(), userRequest.getFirstName(), userRequest.getLastName(), userRequest.getDateOfBirth(),
                    userRequest.getEmail(), userRequest.getUsername(), userRequest.getPassword());
            userRepository.save(user.get());
        } else {
            throw new UserException("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public void deleteUser(int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            if (user.get().isEnabled()) {
                userRepository.disableUserById(id);
            } else {
                throw new UserException("User not found", HttpStatus.NOT_FOUND);
            }
            //todo Key  is still referenced from table
        } else {
            throw new UserException("User not found", HttpStatus.NOT_FOUND);
        }
    }

    private void modifyUser(User user, String firstName, String lastName, Date dateOfBirth,
                            String email, String username, String password) {

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setDateOfBirth(dateOfBirth);
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));

        user.setUpdatedAt(LocalDateTime.now());
        // todo current user from auth
        user.setUpdatedWho(userRepository.findById(1).get());
    }
}
