package com.nd.nit.commands.impl;

import com.nd.nit.commands.Command;
import com.nd.nit.models.CreateVersionModel;
import com.nd.nit.models.FileInfoModel;
import com.nd.nit.models.VersionModel;
import com.nd.nit.util.HashUtil;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PushCommand extends BaseCommand implements Command {
    private String description;

    public PushCommand(String description){
        this.description = description;
    }

    @Override
    public void execute() {
        File dir = new File(getCurrentDirectory());
        Path basePath = dir.toPath();
        List<File> list = new ArrayList<>();
        processFilesFromFolder(dir, list);

        //2 Found files in dir
        List<FileInfoModel> fileInfoList = new ArrayList<>();
        Map<String, File> clientFilesMap = new HashMap<>();
        for (File file : list) {
            FileInfoModel fileInfoModel= new FileInfoModel();
            fileInfoModel.setName(file.getName());

            String relativePath = basePath.relativize(file.toPath()).toString();
            String relativePathHash = HashUtil.calculateHash(relativePath);
            //cut path(without name)
            String path = relativePath.substring(0, relativePath.lastIndexOf(fileInfoModel.getName()));
            fileInfoModel.setPath(path);
            fileInfoList.add(fileInfoModel);
            clientFilesMap.put(relativePathHash, file);
        }

        //3
        CreateVersionModel createVersion = new CreateVersionModel();
        createVersion.setInfoModelList(fileInfoList);
        VersionModel versionModel = new VersionModel();
        versionModel.setDescription(description);
        createVersion.setVersionModel(versionModel);

        //4
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<CreateVersionModel> request = new HttpEntity<>(createVersion);
        String baseUrl;
        try {
            baseUrl = getBaseUrl();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        CreateVersionModel createVersionModel = restTemplate.postForObject( baseUrl + "/version", request, CreateVersionModel.class);

        //5
        for (FileInfoModel infoModel : createVersionModel.getInfoModelList()) {
            File file = clientFilesMap.get(infoModel.getHashFullname());

            MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
            parameters.add("file", new FileSystemResource(file));

            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "multipart/form-data");
            headers.set("Accept", "text/plain");

            String url = String.format(baseUrl + "/file/%d", infoModel.getId());
            restTemplate.postForObject(
                    url,
                    new HttpEntity<MultiValueMap<String, Object>>(parameters, headers),
                    String.class);

        }

        //6
        createVersionModel.getVersionModel().setReleased(true);
        String resourceUrl =
               baseUrl + "/version" + '/' + createVersionModel.getVersionModel().getId();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<VersionModel> requestUpdate = new HttpEntity<>(createVersionModel.getVersionModel(), headers);
        restTemplate.exchange(resourceUrl, HttpMethod.PUT, requestUpdate, Void.class);
    }
}