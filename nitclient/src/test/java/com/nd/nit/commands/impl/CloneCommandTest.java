package com.nd.nit.commands.impl;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.Assert.assertEquals;

public class CloneCommandTest {
    @Test
    public void testExecute() throws IOException {
        CloneCommand command = new CloneCommand("http://");
        command.execute();

        final String dir = System.getProperty("user.dir");
        File repository = new File(dir, ".nit");
        File conf = new File(repository, "nit.conf");

        String url = Files.readAllLines(conf.toPath()).get(0);
        assertEquals("http://", url);
        conf.delete();
        repository.delete();
    }
}
