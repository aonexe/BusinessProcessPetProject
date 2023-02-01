package com.vorsin.businessProcess.dto;

import com.vorsin.businessProcess.models.Stage;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class BPResponse {

    @NotNull(message = "Id should not be empty")
    private int id;

    @NotNull(message = "Title should not be empty")
    @Size(max = 30, message = "Title should be less than 30 characters")
    private String title;

    private List<Stage> stages;
}
