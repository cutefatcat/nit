package com.nd.nit.commands.impl;

import com.nd.nit.commands.Command;
import com.nd.nit.models.FileInfoModel;

import java.io.IOException;
import java.util.List;

public class PullCommand extends BaseCommand implements Command{
    private Integer versionId;

    public PullCommand(Integer versionId) {
        this.versionId = versionId;
    }

    @Override
    public void execute() {
        try {
            deleteFilesFromFolder();
        } catch (IOException e) {
            System.out.println("Error during files removing. " + e.getMessage());
        }

        List<FileInfoModel> fileInfoList = getFileInfoList(versionId);

        for (FileInfoModel fileInfo : fileInfoList){
            try {
                downloadFile(fileInfo);
            } catch (IOException e) {
                System.out.println("Error during " + fileInfo.getName() + " file downloading. " + e.getMessage());
            }
        }
    }
}
