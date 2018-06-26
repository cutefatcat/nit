package com.nd.nit.models;

import java.time.LocalDateTime;

public class VersionModel {
    private int id;
    private LocalDateTime createDate;
    private boolean released;
    private String description;

    public VersionModel(int id, String description){
        this.id = id;
        createDate = LocalDateTime.now();
        released = false;
        this.description = description;
    }

    public VersionModel(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public boolean isReleased() {
        return released;
    }

    public void setReleased(boolean released) {
        this.released = released;
    }
}
