package com.moviezone.dai_api.model.dto;

public class ErrorResponseDTO {
    private int errorCode;
    private String errorMessage;

    public ErrorResponseDTO(String errorMessage, int errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }
}
