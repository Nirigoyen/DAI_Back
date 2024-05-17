package com.moviezone.dai_api.utils;

public class ErrorResponse {
    private int errorCode;
    private String errorMessage;

    public ErrorResponse(String errorMessage, int errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }
}
