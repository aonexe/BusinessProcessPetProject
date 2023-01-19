package com.vorsin.businessProcess.models;

import jakarta.persistence.*;

@Entity
@Table(name = "process_action")
public class ProcessAction {

    @Id
    @Column(name = "action_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "resolution")
    private Resolutions resolution;

    @Column(name = "action_result")
    private boolean actionResult;

    @ManyToOne
    @JoinColumn(name = "stage_id", referencedColumnName = "stage_id")
    private ProcessStage stage;

    @OneToOne
    @JoinColumn(name = "task_owner_id", referencedColumnName = "employee_id")
    private Employee taskOwner;

   public ProcessAction() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Resolutions getResolution() {
        return resolution;
    }

    public void setResolution(Resolutions resolution) {
        this.resolution = resolution;
    }

    public boolean isActionResult() {
        return actionResult;
    }

    public void setActionResult(boolean actionResult) {
        this.actionResult = actionResult;
    }

    public ProcessStage getStage() {
        return stage;
    }

    public void setStage(ProcessStage stage) {
        this.stage = stage;
    }

    public Employee getTaskOwner() {
        return taskOwner;
    }

    public void setTaskOwner(Employee taskOwner) {
        this.taskOwner = taskOwner;
    }
}

