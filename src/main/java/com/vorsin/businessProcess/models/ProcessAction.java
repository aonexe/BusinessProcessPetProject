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
    private StageResultEnum resolution;

    @Column(name = "action_result")
    private boolean actionResult;

    @ManyToOne
    @JoinColumn(name = "stage_id", referencedColumnName = "stage_id")
    private Stage stage;

    @OneToOne
    @JoinColumn(name = "task_owner_id", referencedColumnName = "user_id")
    private User taskOwner;

   public ProcessAction() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public StageResultEnum getResolution() {
        return resolution;
    }

    public void setResolution(StageResultEnum resolution) {
        this.resolution = resolution;
    }

    public boolean isActionResult() {
        return actionResult;
    }

    public void setActionResult(boolean actionResult) {
        this.actionResult = actionResult;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public User getTaskOwner() {
        return taskOwner;
    }

    public void setTaskOwner(User taskOwner) {
        this.taskOwner = taskOwner;
    }
}

