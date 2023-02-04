package com.vorsin.businessProcess.dto;

import com.vorsin.businessProcess.models.Stage;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StageRelationResponse {

    @NotNull(message = "Id should not be empty")
    private int id;

    @NotNull(message = "Title should not be empty")
    @Size(max = 70, message = "Title should be less than 70 characters")
    private String title;

    @NotNull(message = "Stage should not be empty")
    private Stage fromStage;

    @NotNull(message = "Stage should not be empty")
    private Stage toStage;
}
