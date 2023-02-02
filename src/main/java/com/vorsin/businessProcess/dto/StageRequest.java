package com.vorsin.businessProcess.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class StageRequest {

    @NotNull(message = "Title should not be empty")
    @Size(max = 30, message = "Title should be less than 30 characters")
    private String title;

    @NotNull(message = "Business process should not be empty")
    private int businessProcessId;

}
