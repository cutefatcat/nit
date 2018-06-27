package com.nd.nit.models;

import com.nd.nit.util.HashUtil;

import java.sql.Connection;

public class FileInfoModel {
    private int id;
    private String name;
    private String path;
    private int versionId;
    private int binaryId;

    public FileInfoModel(){
    }

    public FileInfoModel(int id, String name, String path){ 
        this.id = id;
        this.name = name;
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getHashFullname() {
        String fullname = path + name;
        return HashUtil.calculateHash(fullname);
    }

    public int getVersionId() {
        return versionId;
    }

    public void setVersionId(int versionId) {
        this.versionId = versionId;
    }

    public int getBinaryId() {
        return binaryId;
    }

    public void setBinaryId(int binaryId) {
        this.binaryId = binaryId;
    }
}
