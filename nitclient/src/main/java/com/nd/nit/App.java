package com.nd.nit;

import com.nd.nit.commands.Command;
import com.nd.nit.commands.CommandFactory;

/**
 * Application start point.
 */
public class App {
    public static void main(String[] args) {
        try {
            Command command = CommandFactory.getCommand(args);
            //т.к. pattern factory + command
            command.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
