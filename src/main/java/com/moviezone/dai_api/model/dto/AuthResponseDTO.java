package com.moviezone.dai_api.model.dto;

public class AuthResponseDTO { //* Se usa para la respuesta del loginy del refreshtoken
    private String accessToken;
    private String refreshToken;


    public AuthResponseDTO() {
    }

    public AuthResponseDTO(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
