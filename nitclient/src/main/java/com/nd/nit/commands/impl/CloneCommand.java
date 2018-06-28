package com.nd.nit.commands.impl;

import com.nd.nit.commands.Command;
import com.nd.nit.models.CreateVersionModel;
import com.nd.nit.models.FileInfoModel;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.nio.file.Files;
import java.util.List;


public class CloneCommand implements Command{
    private String url;

    public CloneCommand(String url){
        this.url = url;
    }

    @Override
    public void execute() {
        // TODO create nit.conf file.
        //Получениие текущей директории
        final String dir = System.getProperty("user.dir");
        File repository = new File(dir, ".nit");
        if (!repository.exists()) {
            repository.mkdir();
        }

        File conf = new File(repository, "nit.conf");
        if (!conf.exists()) {
            try {
                conf.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            Files.write(conf.toPath(), url.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*
        TODO get files list
        TODO get file one by one
         */

        List<FileInfoModel> fileInfoList = getFileInfoList();
        int fileBinaryId;

        for (int i = 0; i < fileInfoList.size(); i++){
            fileBinaryId = fileInfoList.get(i).getBinaryId();
            getFile(fileBinaryId);
        }
    }

    public List<FileInfoModel> getFileInfoList(){
        RestTemplate restTemplate = new RestTemplate();
        CreateVersionModel createVersionModel  = restTemplate.getForObject("http://localhost:8080/version/16",CreateVersionModel.class);

        FileInfoModel fileInfoModel = new FileInfoModel();
        List<FileInfoModel> fileInfoModelList = createVersionModel.getInfoModelList();

        return fileInfoModelList;
    }

    public ResponseEntity<Resource> getFile(int fileId){
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("http://localhost:8080/file/%s", fileId);

        HttpHeaders headers = new HttpHeaders();
        //headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<Resource> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, Resource.class);

        try (OutputStream outputStream = new FileOutputStream(new File("file.txt"))) {
            IOUtils.copy(responseEntity.getBody().getInputStream(), outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseEntity;
    }
}
