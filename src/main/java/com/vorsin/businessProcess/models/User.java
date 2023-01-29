package com.vorsin.businessProcess.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "bp_user")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name")
    @NotNull(message = "First name should be not empty")
    @Size(min = 2, max = 30, message = "First name should be between 2 and 30 characters")
    private String firstName;

    @Column(name = "last_name")
    @NotNull(message = "Last name should be not empty")
    @Size(min = 2, max = 30, message = "Last name should be between 2 and 30 characters")
    private String lastName;

    @Column(name = "date_of_birth")
    @Past(message = "It's impossible")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dateOfBirth;

    @Column(name = "email")
    @NotNull(message = "Email should be not empty")
    @Email
    private String email;

    @Column(name = "username")
    @NotNull(message = "Username should be not empty")
    @Size(min = 2, max = 30, message = "Username should be between 2 and 30 characters")
    private String username;

    @Column(name = "password")
    @NotNull(message = "Password should be not empty")
    @Size(min = 8, max = 50, message = "Password should be between 8 and 50 characters")
    private String password;

    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    private UserRoleEnum userRole;

    @Column(name = "created_at")
    @NotNull(message = "Created at time should not be empty")
    private LocalDateTime createdAt;

    @JoinColumn(name = "created_who", referencedColumnName = "user_id")
    @OneToOne
    @JsonBackReference
    @NotNull(message = "Creator should not be empty")
    private User createdWho;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @JoinColumn(name = "updated_who", referencedColumnName = "user_id")
    @OneToOne
    @JsonBackReference
    private User updatedWho;

    //todo one to many
    @OneToOne(mappedBy = "taskOwner")
    private ProcessAction action;


    public User() {}

    public User(String firstName, String lastName, Date dateOfBirth, String email, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRoleEnum getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRoleEnum userRole) {
        this.userRole = userRole;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public User getCreatedWho() {
        return createdWho;
    }

    public void setCreatedWho(User createdWho) {
        this.createdWho = createdWho;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getUpdatedWho() {
        return updatedWho;
    }

    public void setUpdatedWho(User updatedWho) {
        this.updatedWho = updatedWho;
    }

    public ProcessAction getAction() {
        return action;
    }

    public void setAction(ProcessAction action) {
        this.action = action;
    }
}