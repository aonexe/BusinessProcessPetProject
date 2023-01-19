package com.vorsin.businessProcess.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;


public class BusinessProcessDTO {

    @NotEmpty
    @Size(max = 30, message = "Title should be less than 30 characters")
    private String title;

    public BusinessProcessDTO(){}

    public BusinessProcessDTO(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
