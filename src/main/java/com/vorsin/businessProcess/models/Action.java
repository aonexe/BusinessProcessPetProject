package com.vorsin.businessProcess.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "action")
@Data
public class Action {

    @Id
    @Column(name = "action_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name = "action_result_id", referencedColumnName = "action_result_id")
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private ActionResult actionResult;

    @Column(name = "created_at")
    @NotNull(message = "Created at time should not be empty")
    @JsonIgnore
    private LocalDateTime createdAt;

    @JoinColumn(name = "created_who", referencedColumnName = "user_id")
    @OneToOne(fetch = FetchType.EAGER)
    @NotNull(message = "Creator should not be empty")
    @JsonIgnore
    private User createdWho;

    @Column(name = "updated_at")
    @JsonIgnore
    private LocalDateTime updatedAt;

    @JoinColumn(name = "updated_who", referencedColumnName = "user_id")
    @OneToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private User updatedWho;

    @JoinColumn(name = "stage_id", referencedColumnName = "stage_id")
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    @NotNull(message = "Stage should not be empty")
    private Stage stage;

    @JoinColumn(name = "task_owner_id", referencedColumnName = "user_id")
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    @NotNull(message = "Task owner should not be empty")
    private User taskOwner;

}

