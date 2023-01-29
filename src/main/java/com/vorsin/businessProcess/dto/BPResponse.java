package com.vorsin.businessProcess.dto;

import com.vorsin.businessProcess.models.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;


public class BPResponse {

    @NotNull(message = "Title should not be empty")
    @Size(max = 30, message = "Title should be less than 30 characters")
    private String title;

    @NotNull(message = "Created at time should not be empty")
    private LocalDateTime createdAt;

    @NotNull(message = "Creator should not be empty")
    private User createdWho;

    private LocalDateTime updatedAt;

    private User updatedWho;

    public BPResponse() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
}
