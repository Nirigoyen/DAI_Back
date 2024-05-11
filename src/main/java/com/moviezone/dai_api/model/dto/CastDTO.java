package com.moviezone.dai_api.model.dto;

public class CastDTO {
    private int castId;
    private String castName;
    private String characterName;
    private String castImagePath;



    public CastDTO() {
    }

    public CastDTO(int castId, String castName, String characterName, String castImagePath) {
        this.castId = castId;
        this.castName = castName;
        this.characterName = characterName;
        this.castImagePath = castImagePath;
    }

    public int getCastId() {
        return castId;
    }

    public void setCastId(int castId) {
        this.castId = castId;
    }

    public String getCastName() {
        return castName;
    }

    public void setCastName(String castName) {
        this.castName = castName;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public String getCastImagePath() {
        return castImagePath;
    }

    public void setCastImagePath(String castImagePath) {
        this.castImagePath = castImagePath;
    }
}
