package com.vorsin.businessProcess.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "process_stage")
public class Stage {

    @Id
    @Column(name = "stage_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    @NotEmpty
    @Size(max = 30, message = "Title should be less than 30 characters")
    private String title;

    @Column(name = "stage_result")
    private StageResult stageResult;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "created_who")
    @NotEmpty
    @Size(max = 30, message = "Creator name should be less than 30 characters")
    private String createdWho;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_who")
    @Size(max = 30, message = "Updater name should be less than 30 characters")
    private int updatedWho;

    @ManyToOne
    @JoinColumn(name = "process_id", referencedColumnName = "process_id")
    private BusinessProcess businessProcess;

    @OneToMany(mappedBy = "stage")
    private List<ProcessAction> actions;


    public Stage() {}

    public Stage(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public StageResult getStageResult() {
        return stageResult;
    }

    public void setStageResult(StageResult stageResult) {
        this.stageResult = stageResult;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedWho() {
        return createdWho;
    }

    public void setCreatedWho(String createdWho) {
        this.createdWho = createdWho;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getUpdatedWho() {
        return updatedWho;
    }

    public void setUpdatedWho(int updatedWho) {
        this.updatedWho = updatedWho;
    }

    public BusinessProcess getBusinessProcess() {
        return businessProcess;
    }

    public void setBusinessProcess(BusinessProcess businessProcess) {
        this.businessProcess = businessProcess;
    }

    public List<ProcessAction> getActions() {
        return actions;
    }

    public void setActions(List<ProcessAction> actions) {
        this.actions = actions;
    }
}


