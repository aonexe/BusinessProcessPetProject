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

import java.util.List;

@Entity
@Table(name = "process_stage")
public class ProcessStage {

    @Id
    @Column(name = "stage_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    @NotEmpty
    @Size(max = 30, message = "Title should be less than 30 characters")
    private String title;

    @Column(name = "stage_result")
    private boolean stageResult;

    @ManyToOne
    @JoinColumn(name = "process_id", referencedColumnName = "process_id")
    private BusinessProcess businessProcess;

    @OneToMany(mappedBy = "stage")
    private List<ProcessAction> actions;


    public ProcessStage() {}

    public ProcessStage(String title) {
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

    public boolean isStageResult() {
        return stageResult;
    }

    public void setStageResult(boolean stageResult) {
        this.stageResult = stageResult;
    }

    public BusinessProcess getBusinessProcess() {
        return businessProcess;
    }

    public void setBusinessProcess(BusinessProcess businessProcess) {
        this.businessProcess = businessProcess;
    }
}


