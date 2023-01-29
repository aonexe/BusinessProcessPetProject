package com.vorsin.businessProcess.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class StageRequest {

    @NotNull(message = "Title should not be empty")
    @Size(max = 30, message = "Title should be less than 30 characters")
    private String title;

    @NotNull(message = "Business process should not be empty")
    private int businessProcessId;

    public StageRequest() {}

    public StageRequest(String title, int businessProcessId) {
        this.title = title;
        this.businessProcessId = businessProcessId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getBusinessProcessId() {
        return businessProcessId;
    }

    public void setBusinessProcessId(int businessProcessId) {
        this.businessProcessId = businessProcessId;
    }
}
