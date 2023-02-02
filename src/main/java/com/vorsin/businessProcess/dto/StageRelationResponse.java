package com.vorsin.businessProcess.dto;

import com.vorsin.businessProcess.models.Stage;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StageRelationResponse {

    @NotNull(message = "Id should not be empty")
    private int id;

    @NotNull(message = "Stage should not be empty")
    private Stage fromStage;

    @NotNull(message = "Stage should not be empty")
    private Stage toStage;
}
