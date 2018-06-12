package com.nd.nit.commands;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CommandFactoryTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testGetCommandArgsNull() throws Exception {
        thrown.expect(Exception.class);
        thrown.expectMessage("Command not specified!");
        CommandFactory.getCommand(null);
    }

    @Test
    public void testGetCommandArgsZero() throws Exception {
        thrown.expect(Exception.class);
        thrown.expectMessage("Command not specified!");
        CommandFactory.getCommand(new String[0]);
    }

    @Test
    public void testGetCommandClone() throws Exception{
        thrown.expect(Exception.class);
        thrown.expectMessage("Url parameter not specified!");
        CommandFactory.getCommand(new String[]{"clone"});
    }
}
