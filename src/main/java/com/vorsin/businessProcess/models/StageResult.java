package com.vorsin.businessProcess.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "stage_result")
@Data
public class StageResult {

    @Id
    @Column(name = "stage_result_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "stage_result_name")
    @NotNull(message = "Name should not be empty")
    @Size(max = 30, message = "Name should be less than 30 characters")
    private String name;

    @Column(name = "created_at")
    @NotNull(message = "Created at time should not be empty")
    @JsonIgnore
    private LocalDateTime createdAt;

    @JoinColumn(name = "created_who", referencedColumnName = "user_id")
    @OneToOne(fetch = FetchType.LAZY)
    @NotNull(message = "Creator should not be empty")
    @JsonIgnore
    private User createdWho;

    @Column(name = "updated_at")
    @JsonIgnore
    private LocalDateTime updatedAt;

    @JoinColumn(name = "updated_who", referencedColumnName = "user_id")
    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User updatedWho;

    @OneToMany(mappedBy = "stageResult", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Stage> stages;

    public StageResult() {}
    public StageResult(String name) {
        this.name = name;
    }
}
