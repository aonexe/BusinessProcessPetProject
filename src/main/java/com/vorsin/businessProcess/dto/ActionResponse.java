package com.vorsin.businessProcess.dto;

import com.vorsin.businessProcess.models.ActionResultEnum;
import com.vorsin.businessProcess.models.Stage;
import com.vorsin.businessProcess.models.User;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActionResponse {

    @NotNull(message = "Id should not be empty")
    private int id;

    @NotNull(message = "Action result should not be empty")
    private ActionResultEnum actionResult;

    @NotNull(message = "Task owner should not be empty")
    private User taskOwner;
}
