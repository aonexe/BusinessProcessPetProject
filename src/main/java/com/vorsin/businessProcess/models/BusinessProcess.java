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
@Table(name = "business_process")
@Data
public class BusinessProcess {

    //todo rename to business_process_id
    @Id
    @Column(name = "process_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    @NotNull
    @Size(max = 30, message = "Title should be less than 30 characters")
    private String title;

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

    @OneToMany(mappedBy = "businessProcess", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Stage> stages;

}


