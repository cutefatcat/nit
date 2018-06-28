package com.nd.nit.commands;

import com.nd.nit.commands.impl.*;

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
//            case "diff":
//                return new DiffCommand();
            case "push":
                if (args.length != 2 || args[1] == null || args[1].equals("")){
                    throw new Exception("Description parameter not specified!");
                }

                return new PushCommand(args[1]);
            case "pull":
                if ( args.length == 1) {
                    return new PullCommand(null);
                } else if (args.length == 2) {
                    return new PullCommand(Integer.parseInt(args[1]));
                } else {
                    throw new Exception("Wrong arguments count!");
                }


            default:

                throw new Exception("Wrong command!");
        }
    }
}
