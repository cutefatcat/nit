package com.nd.nit;

import com.nd.nit.commands.Command;
import com.nd.nit.commands.CommandFactory;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        Command command = CommandFactory.getCommand(args);
        //т.к. pattern factory + command
        command.execute();
    }
}
