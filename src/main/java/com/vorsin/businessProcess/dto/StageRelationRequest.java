package com.vorsin.businessProcess.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class StageRelationRequest {

    @Size(max = 70, message = "Title should be less than 70 characters")
    private String title;

    @NotNull(message = "Stage should not be empty")
    private int fromStageId;

    @NotNull(message = "Stage should not be empty")
    private int toStageId;
}
