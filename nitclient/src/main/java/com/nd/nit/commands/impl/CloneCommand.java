package com.nd.nit.commands.impl;

import com.nd.nit.commands.Command;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

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


//        throw new NotImplementedException();
    }
}
