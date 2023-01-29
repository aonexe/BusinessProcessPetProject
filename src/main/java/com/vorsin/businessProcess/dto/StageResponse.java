package com.vorsin.businessProcess.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vorsin.businessProcess.models.BusinessProcess;
import com.vorsin.businessProcess.models.StageResultEnum;
import com.vorsin.businessProcess.models.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.modelmapper.internal.bytebuddy.build.ToStringPlugin;

import java.time.LocalDateTime;

public class StageResponse {

    private int id;

    @NotNull(message = "Title should not be empty")
    @Size(max = 30, message = "Title should be less than 30 characters")
    private String title;

    @NotNull(message = "Stage result should not be empty")
    private StageResultEnum stageResult;

    @NotNull(message = "Created at time should not be empty")
    private LocalDateTime createdAt;

    @NotNull(message = "Creator should not be empty")
    private User createdWho;

    private LocalDateTime updatedAt;

    private User updatedWho;

    @NotNull(message = "Business process should not be empty")
    private BusinessProcess businessProcess;


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

    public StageResultEnum getStageResult() {
        return stageResult;
    }

    public void setStageResult(StageResultEnum stageResult) {
        this.stageResult = stageResult;
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

    public BusinessProcess getBusinessProcess() {
        return businessProcess;
    }

    public void setBusinessProcess(BusinessProcess businessProcess) {
        this.businessProcess = businessProcess;
    }
}
