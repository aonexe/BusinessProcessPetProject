package com.vorsin.businessProcess.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ActionRequest {

    @NotNull(message = "Stage should not be empty")
    private int stageId;

    @NotNull(message = "Task owner should not be empty")
    private int taskOwnerId;
}
