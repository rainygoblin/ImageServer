package com.iworkstation.imageserver.command;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ServerCommandProcessorTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testServerCommandProcessor() {
		ServerCommandProcessor serverCommandProcessor = new ServerCommandProcessor(
				"For test");
		IServerCommand createDirectoryCommand = new CreateDirectoryCommand(
				"d:\\temp");
		serverCommandProcessor.addCommand(createDirectoryCommand);

		IServerCommand copyDirectoryCommand = new CopyDirectoryCommand(
				"c:/temp", "d:/temp");
		serverCommandProcessor.addCommand(copyDirectoryCommand);

		IServerCommand deleteDirectoryCommand = new DeleteDirectoryCommand(
				"c:/temp");
		serverCommandProcessor.addCommand(deleteDirectoryCommand);

		serverCommandProcessor.execute();
	}
}
