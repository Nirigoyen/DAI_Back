package com.moviezone.dai_api.model.dto;

public class HealthDTO {
    private boolean DB_STATUS = false;
    private boolean API_STATUS = false;

    public HealthDTO() {
    }

    public HealthDTO(boolean DB_STATUS, boolean API_STATUS) {
        this.DB_STATUS = DB_STATUS;
        this.API_STATUS = API_STATUS;
    }

    public boolean isDB_STATUS() {
        return DB_STATUS;
    }

    public void setDB_STATUS(boolean DB_STATUS) {
        this.DB_STATUS = DB_STATUS;
    }

    public boolean isAPI_STATUS() {
        return API_STATUS;
    }

    public void setAPI_STATUS(boolean API_STATUS) {
        this.API_STATUS = API_STATUS;
    }

    @Override
    public String toString() {
        return "HealthDTO{" +
                "DB_STATUS=" + DB_STATUS +
                ", API_STATUS=" + API_STATUS +
                '}';
    }
}
