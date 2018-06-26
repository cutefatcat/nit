package com.nd.nit.models;

public class FileBinaryModel {
    private int id;
    private byte[] content;
    private String hashContent;

    public FileBinaryModel(){}

    public FileBinaryModel(int id, byte[] content, String hashContent){
        this.id = id;
        this.content = content;
        this.hashContent = hashContent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getHashContent() {
        return hashContent;
    }

    public void setHashContent(String hashContent) {
        this.hashContent = hashContent;
    }
}
