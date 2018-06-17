package com.nd.nit.models;

import java.time.LocalDate;

public class Version {
    private int id;
    private String token;
    private LocalDate createDate;
    private boolean release;

    public Version(int id, String token){
        this.id = id;
        this.token = token;
        createDate = LocalDate.now();
        release = false;
    }

    public Version(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public boolean isRelease() {
        return release;
    }

    public void setRelease(boolean release) {
        this.release = release;
    }
}
