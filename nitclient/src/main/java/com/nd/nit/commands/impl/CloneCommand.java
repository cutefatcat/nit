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


public class CloneCommand extends BaseCommand implements Command{
    private String url;

    public CloneCommand(String url){
        this.url = url;
    }

    @Override
    public void execute() {
        //Получениие текущей директории
        final String dir = getCurrentDirectory();
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

        List<FileInfoModel> fileInfoList = getFileInfoList();

        for (int i = 0; i < fileInfoList.size(); i++){
            FileInfoModel fileInfo = fileInfoList.get(i);
            try {
                downloadFile(fileInfo);
            } catch (IOException e) {
                System.out.println("Error during " + fileInfo.getName() + " file downloading. " + e.getMessage());
            }
        }
    }
}
