package com.nd.nit.models;

import java.util.ArrayList;
import java.util.List;

public class CreateVersionModel {
    VersionModel versionModel;
    List<FileInfoModel> infoModelList = new ArrayList<>();

    public CreateVersionModel(){
    }

    public CreateVersionModel(VersionModel versionModel, List<FileInfoModel> infoModelList){
        this.versionModel = versionModel;
        this.infoModelList = infoModelList;
    }

    public VersionModel getVersionModel() {
        return versionModel;
    }

    public void setVersionModel(VersionModel versionModel) {
        this.versionModel = versionModel;
    }

    public List<FileInfoModel> getInfoModelList() {
        return infoModelList;
    }

    public void setInfoModelList(List<FileInfoModel> infoModelList) {
        this.infoModelList = infoModelList;
    }
}
