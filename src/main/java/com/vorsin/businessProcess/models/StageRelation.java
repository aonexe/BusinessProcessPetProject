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
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "stage_relation")
@Data
public class StageRelation {

    @Id
    @Column(name = "stage_relation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //todo custom from stage1 to stage2
    @Column(name = "title")
    @Size(max = 70, message = "Title should be less than 70 characters")
    private String title;

    @JoinColumn(name = "from_stage", referencedColumnName = "stage_id")
    @ManyToOne
    @NotNull(message = "Stage should not be empty")
    @JsonBackReference
    private Stage fromStage;

    @JoinColumn(name = "to_stage", referencedColumnName = "stage_id")
    @ManyToOne
    @NotNull(message = "Stage should not be empty")
    @JsonBackReference
    private Stage toStage;

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

}
