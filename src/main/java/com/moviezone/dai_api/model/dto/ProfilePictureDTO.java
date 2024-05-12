package com.moviezone.dai_api.model.dto;

public class ProfilePictureDTO {
    private int id;
    private byte[] data;

    public ProfilePictureDTO(int id, byte[] data) {
        this.id = id;
        this.data = data;
    }

    public ProfilePictureDTO(byte[] data) {
        this.data = data;
    }

    public ProfilePictureDTO() {
    }

    public int getId() {
        return id;
    }

    public byte[] getPath() {
        return data;
    }
}
