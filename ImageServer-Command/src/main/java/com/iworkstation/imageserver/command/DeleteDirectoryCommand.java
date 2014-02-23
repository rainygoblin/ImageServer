package com.iworkstation.imageserver.command;

import java.io.File;

public class DeleteDirectoryCommand extends ServerCommand {
	private File theDir = null;

	public DeleteDirectoryCommand(String directory) {
		super(String.format("DeleteDirectory {%s}", directory), true);
		if (directory == null || directory.isEmpty()) {
			throw new IllegalArgumentException(
					"The directory to be created should not be null!");
		}

		theDir = new File(directory);
	}

	@Override
	public void onExecute(ServerCommandProcessor theProcessor) {
		if (theDir.exists()) {
			theDir.delete();
		}

	}

	@Override
	public void onUndo() {
		// TODO Auto-generated method stub

	}

	protected void finalize() {
		// finalization code here
	}
}
