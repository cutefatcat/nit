package com.nd.nit.commands.impl;

import com.nd.nit.commands.Command;
import com.nd.nit.models.CreateVersionModel;
import com.nd.nit.models.FileInfoModel;
import com.nd.nit.util.HashUtil;
import org.springframework.web.client.RestTemplate;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiffCommand implements Command  {

//    public List<File> callStatusCommand(){
//        StatusCommand statusCommand = new StatusCommand();
//        List<File> statusList = statusCommand.execute(); //<Status>
//        List<File> modifiedStatus= new ArrayList();
//        while (statusList.size() > 0){
//            //проверка на стасус
//            if (status = "modified"){
//                modifiedStatus.add(status);
//            }
//        }
//
//        return modifiedStatus;
//    }

    @Override
    public void execute() {
//        List<File> list = callStatusCommand();
        throw new NotImplementedException();
    }
}
