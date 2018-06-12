package com.nd.nit.commands.impl;

import com.nd.nit.commands.Command;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class CloneCommand implements Command{
    private String url;

    public CloneCommand(String url){
        this.url = url;
    }

    @Override
    public void execute() {

        throw new NotImplementedException();
    }
}
