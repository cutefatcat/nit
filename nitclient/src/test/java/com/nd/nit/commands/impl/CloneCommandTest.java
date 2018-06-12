package com.nd.nit.commands.impl;

import org.junit.Test;

import java.io.File;

public class CloneCommandTest {
    @Test
    public void testExecute() {
        CloneCommand command = new CloneCommand("");
        command.execute();

        final String dir = System.getProperty("user.dir");
        File repository = new File(dir, ".nit");
        File conf = new File(repository, "nit.conf");
        conf.delete();
        repository.delete();
    }
}
