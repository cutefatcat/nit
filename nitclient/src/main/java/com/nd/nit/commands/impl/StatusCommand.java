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
        String baseUrl;
        try {
            baseUrl = getBaseUrl();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        RestTemplate restTemplate = new RestTemplate();
        CreateVersionModel createVersionModel  = restTemplate.getForObject(baseUrl + "/version/last",CreateVersionModel.class);

        Map<String, FileInfoModel> serverFilesMap= new HashMap<>();
        for (FileInfoModel fileInfo : createVersionModel.getInfoModelList()){
            serverFilesMap.put(fileInfo.getHashFullname(),fileInfo);
        }

        final String dirPath = getCurrentDirectory();
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

        //print collections to console
        System.out.println("New files: ");
        for (File file : newFiles) {
            System.out.println(file.getPath());
        }
        System.out.println("Modified files: ");
        for (File file : modifiedFiles) {
            System.out.println(file.getPath());
        }
        System.out.println("Removed files: ");
        for (FileInfoModel file : removedFiles) {
            System.out.println(file.getPath() + file.getName());
        }
    }
}
