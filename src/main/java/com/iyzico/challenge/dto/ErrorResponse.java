package com.iyzico.challenge.dto;

public class ErrorResponse {
    private String detail;

    public ErrorResponse(String detail) {
        this.detail = detail;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
