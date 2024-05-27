package com.moviezone.dai_api.model.dto;

public class TokenDTO { //? Podemos usar esto para recibir token de google y mandar los JWT
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
