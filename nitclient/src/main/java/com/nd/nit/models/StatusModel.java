package com.nd.nit.models;

import java.io.File;
import java.util.List;

public class StatusModel {
    List<File> newFiles;
    List<File> modifiedFiles;
    List<FileInfoModel> removedFiles;

    public StatusModel(){

    }

    public List<File> getNewFiles() {
        return newFiles;
    }

    public void setNewFiles(List<File> newFiles) {
        this.newFiles = newFiles;
    }

    public List<File> getModifiedFiles() {
        return modifiedFiles;
    }

    public void setModifiedFiles(List<File> modifiedFiles) {
        this.modifiedFiles = modifiedFiles;
    }

    public List<FileInfoModel> getRemovedFiles() {
        return removedFiles;
    }

    public void setRemovedFiles(List<FileInfoModel> removedFiles) {
        this.removedFiles = removedFiles;
    }
}
