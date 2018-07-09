package com.nd.nit.commands.impl;

import com.nd.nit.models.CreateVersionModel;
import com.nd.nit.models.FileInfoModel;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public abstract class BaseCommand {
    protected List<FileInfoModel> getFileInfoList(Integer versionId){
        String baseUrl;
        try {
            baseUrl = getBaseUrl();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Collections.EMPTY_LIST;
        }

        RestTemplate restTemplate = new RestTemplate();
        String url;
        if (versionId == null) {
            url = baseUrl + "/version/last";
        } else {
            url = String.format(baseUrl + "/version/%d", versionId);
        }

        CreateVersionModel createVersionModel  = restTemplate.getForObject(url, CreateVersionModel.class);
        List<FileInfoModel> fileInfoModelList = createVersionModel.getInfoModelList();

        return fileInfoModelList;
    }

    protected void downloadFile(FileInfoModel fileInfo) throws IOException {
        String baseUrl;
        try {
            baseUrl = getBaseUrl();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        RestTemplate restTemplate = new RestTemplate();
        String url = String.format(baseUrl + "/file/%s", fileInfo.getBinaryId());

        HttpHeaders headers = new HttpHeaders();
        //headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Resource> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, Resource.class);

        Path path = Paths.get(getCurrentDirectory(), fileInfo.getPath(), fileInfo.getName());
        //создание директории
        Paths.get(getCurrentDirectory(), fileInfo.getPath()).toFile().mkdirs();
        //запись файла
        try (OutputStream outputStream = new FileOutputStream(path.toFile())) {
            IOUtils.copy(responseEntity.getBody().getInputStream(), outputStream);
        }
    }

    protected String getCurrentDirectory(){
        return "temp"; //System.getProperty("user.dir");
    }

    protected void processFilesFromFolder(File folder, List<File> list) {
        File[] folderEntries = folder.listFiles();
        for (File entry : folderEntries) {
            if (entry.isDirectory()) {
                if (!entry.getName().equals(".nit")) {
                    processFilesFromFolder(entry, list);
                }
                continue;
            }

            list.add(entry);
        }
    }

    protected String getBaseUrl() throws Exception {
        Path configPath = Paths.get(getCurrentDirectory(), ".nit", "nit.conf");
        return Files.readAllLines(configPath).get(0);
    }

    protected void deleteFilesFromFolder() throws IOException {
        File baseDir = new File(getCurrentDirectory());
        for (File file : baseDir.listFiles()){
            if (file.isDirectory() && file.getName().equals(".nit")){
                continue;
            }

            delete(file);
        }
    }

    private void delete(File file) throws IOException {
        if (file.isDirectory()) {
            for (File c : file.listFiles())
                delete(c);
        }
        if (!file.delete()) {
            throw new FileNotFoundException("Failed to delete file: " + file);
        }
    }
}
