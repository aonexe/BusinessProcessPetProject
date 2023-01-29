package com.vorsin.businessProcess.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class BPRequest {

    @NotNull(message = "Title should not be empty")
    @Size(max = 30, message = "Title should be less than 30 characters")
    private String title;

    public BPRequest() {}

    public BPRequest(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
