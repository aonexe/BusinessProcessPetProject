package com.vorsin.businessProcess.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "stage")
@Data
public class Stage {

    @Id
    @Column(name = "stage_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    @NotNull(message = "Title should not be empty")
    @Size(max = 30, message = "Title should be less than 30 characters")
    private String title;

    @JoinColumn(name = "stage_result_id", referencedColumnName = "stage_result_id")
    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull(message = "Stage result should not be empty")
    @JsonBackReference
    private StageResult stageResult;

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

    @JoinColumn(name = "business_process_id", referencedColumnName = "business_process_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "Business process should not be empty")
    @JsonBackReference
    private BusinessProcess businessProcess;

    @OneToMany(mappedBy = "stage", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Action> actions;

    @OneToMany(mappedBy = "fromStage", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<StageRelation> stageRelationsFrom;

    @OneToMany(mappedBy = "toStage", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<StageRelation> stageRelationsTo;

}


