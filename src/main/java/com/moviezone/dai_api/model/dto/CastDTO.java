package com.moviezone.dai_api.model.dto;

public class CastDTO {

    private String fullName;
    private String character;
    private String profilePath;

    public CastDTO() {
    }

    public CastDTO(String fullName, String character, String profilePath) {
        this.fullName = fullName;
        this.character = character;
        this.profilePath = profilePath;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }
}
