package com.vorsin.businessProcess.dto;

import com.vorsin.businessProcess.models.Action;
import com.vorsin.businessProcess.models.User;
import com.vorsin.businessProcess.models.UserRoleEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class UserResponse {

    @NotNull(message = "Id should be not empty")
    private int id;

    @NotNull(message = "First name should be not empty")
    @Size(min = 2, max = 30, message = "First name should be between 2 and 30 characters")
    private String firstName;

    @NotNull(message = "Last name should be not empty")
    @Size(min = 2, max = 30, message = "Last name should be between 2 and 30 characters")
    private String lastName;

    @Past(message = "It's impossible")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dateOfBirth;

    @NotNull(message = "Email should be not empty")
    @Email
    private String email;

    @NotNull(message = "Username should be not empty")
    @Size(min = 2, max = 30, message = "Username should be between 2 and 30 characters")
    private String username;

    private UserRoleEnum userRole;

}
