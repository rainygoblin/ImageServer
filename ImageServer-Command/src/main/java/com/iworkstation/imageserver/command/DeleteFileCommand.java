package com.iworkstation.imageserver.command;

import java.io.File;

public class DeleteFileCommand extends ServerCommand {

	private String fileName;

	public DeleteFileCommand(String fileName) {
		super(String.format("Delete {%s}", fileName), true);
		this.fileName = fileName;
	}

	@Override
	public void onExecute(ServerCommandProcessor theProcessor) {
		if (isRequiresRollback()) {
			backup();
		}

		File fileInfo = new File(fileName);
		if (fileInfo.exists()) {
			fileInfo.delete();
		}
	}

	private void backup() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUndo() {
		// TODO Auto-generated method stub

	}

}
