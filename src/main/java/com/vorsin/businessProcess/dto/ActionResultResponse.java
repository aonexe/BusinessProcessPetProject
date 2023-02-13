package com.vorsin.businessProcess.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActionResultResponse {

    @NotNull(message = "Id should not be empty")
    private int id;

    @NotNull(message = "Name should not be empty")
    @Size(max = 30, message = "Name should be less than 30 characters")
    private String name;


}