package com.nd.nit.commands;

import com.nd.nit.commands.impl.CloneCommand;
import com.nd.nit.commands.impl.StatusCommand;

/**
  * An implementation of CommandFactory.
 */
public class CommandFactory {

    /**
     * Creates commands instances.
     *
     * @param args Command line arguments
     * @return Command instance
     * @throws Exception Command format error
     */
    public static Command getCommand(String[] args) throws Exception {
        if (args == null || args.length < 1){
            throw new Exception("Command not specified!");
        }

        switch (args[0]){
            case "clone":
                if (args.length != 2 || args[1] == null || args[1].equals("")){
                    throw new Exception("Url parameter not specified!");
                }

                return new CloneCommand(args[1]);
            case "status":
                return new StatusCommand();
            default:
                throw new Exception("Wrong command!");
        }
    }
}
