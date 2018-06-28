package com.nd.nit.commands.impl;

import com.nd.nit.commands.Command;
import com.nd.nit.models.CreateVersionModel;
import com.nd.nit.models.FileInfoModel;
import com.nd.nit.util.HashUtil;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatusCommand extends BaseCommand implements Command {
    @Override
    public void execute() {
        RestTemplate restTemplate = new RestTemplate();
        CreateVersionModel createVersionModel  = restTemplate.getForObject("http://localhost:8080/version/last",CreateVersionModel.class);

        Map<String, FileInfoModel> serverFilesMap= new HashMap<>();
        for (FileInfoModel fileInfo : createVersionModel.getInfoModelList()){
            serverFilesMap.put(fileInfo.getHashFullname(),fileInfo);
        }

        final String dirPath = System.getProperty("user.dir");
        File dir = new File(dirPath);
        Path basePath = dir.toPath();
        List<File> list = new ArrayList<>();
        processFilesFromFolder(dir, list);

        List<File> newFiles = new ArrayList<>();
        List<File> modifiedFiles = new ArrayList<>();

        Map<String, File> clientFilesMap= new HashMap<>();
        for (File file : list){
            String relativePath = basePath.relativize(file.toPath()).toString();
            String relativePathHash = HashUtil.calculateHash(relativePath);
            if (serverFilesMap.containsKey(relativePathHash)) {
                //modif or unchan
                modifiedFiles.add(file);
                //TODO files content compare
            } else {
                //add
                newFiles.add(file);
            }

            clientFilesMap.put(relativePathHash, file);
        }

        List<FileInfoModel> removedFiles = new ArrayList<>();
        for (FileInfoModel fileInfo : createVersionModel.getInfoModelList()){
            if (!clientFilesMap.containsKey(fileInfo.getHashFullname())) {
                //remove
                removedFiles.add(fileInfo);
            }
        }

        //TODO print collections to console
        System.out.println();
    }
}
