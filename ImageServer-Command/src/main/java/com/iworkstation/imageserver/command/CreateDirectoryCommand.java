package com.iworkstation.imageserver.command;

import java.io.File;

public class CreateDirectoryCommand extends ServerCommand {

	private File theDir = null;

	public CreateDirectoryCommand(String directory) {
		super("Create Directory", true);
		if (directory == null || directory.isEmpty()) {
			throw new IllegalArgumentException(
					"The directory to be created should not be null!");
		}

		theDir = new File(directory);
	}

	@Override
	public void onExecute(ServerCommandProcessor theProcessor) {

		if (theDir.exists()) {
			return;
		}

		theDir.mkdir();

	}

	@Override
	public void onUndo() {
		if (theDir.exists()) {
			theDir.delete();
		}
	}

}
