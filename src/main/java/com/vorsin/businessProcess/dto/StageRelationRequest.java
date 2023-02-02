package com.vorsin.businessProcess.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class StageRelationRequest {

    @NotNull(message = "Stage should not be empty")
    private int fromStageId;

    @NotNull(message = "Stage should not be empty")
    private int toStageId;
}
