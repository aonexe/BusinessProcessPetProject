package com.vorsin.businessProcess.models;

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
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "business_process")
public class BusinessProcess {

    @Id
    @Column(name = "process_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;

    @Column(name = "title")
    @NotNull
    @Size(max = 30, message = "Title should be less than 30 characters")
    private String title;

    @Column(name = "created_at")
    @NotNull(message = "Created at time should not be empty")
    private LocalDateTime createdAt;

    @JoinColumn(name = "created_who", referencedColumnName = "user_id")
    @OneToOne(fetch = FetchType.EAGER)
    @NotNull(message = "Creator should not be empty")
    private User createdWho;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    @JoinColumn(name = "updated_who", referencedColumnName = "user_id")
    @OneToOne(fetch = FetchType.LAZY)
    private User updatedWho;

    @OneToMany(mappedBy = "businessProcess", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Stage> stages;

    public BusinessProcess() {}


    public BusinessProcess(String title) {
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

    public List<Stage> getStages() {
        return stages;
    }

    public void setStages(List<Stage> stages) {
        this.stages = stages;
    }
}


