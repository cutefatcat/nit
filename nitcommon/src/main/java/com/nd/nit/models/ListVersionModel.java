package com.nd.nit.models;

import java.util.ArrayList;
import java.util.List;

public class ListVersionModel {
    private List<VersionModel> listVersions = new ArrayList();

    public ListVersionModel (){
    }

    public ListVersionModel (List<VersionModel> listVersions){
        this.listVersions = listVersions;
    }

    public List<VersionModel> getListVersions() {
        return listVersions;
    }

    public void setListVersions(List<VersionModel> listVersions) {
        this.listVersions = listVersions;
    }
}
