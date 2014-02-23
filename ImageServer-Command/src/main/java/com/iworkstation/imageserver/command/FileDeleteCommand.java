package com.iworkstation.imageserver.command;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FileDeleteCommand extends ServerCommand {
	private static final Log LOG = LogFactory.getLog(FileDeleteCommand.class);

	private String fileToDelete;

	public FileDeleteCommand(String fileToDelete, boolean requiresRollback) {
		super(String.format("Delete %s", fileToDelete), requiresRollback);
		this.fileToDelete = fileToDelete;
	}

	@Override
	public void onExecute(ServerCommandProcessor theProcessor)
			throws CommandException {

		File file = new File(fileToDelete);
		if (file.exists()) {
			if (isRequiresRollback()) {
				backup();
			}
			LOG.info(String.format("Delete %s", fileToDelete));
			file.delete();
		}

	}

	private void backup() {
		String backupFile = fileToDelete + ".bak";
		try {
			FileUtils.copyFile(new File(fileToDelete), new File(backupFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onUndo() throws CommandException {
		// TODO Auto-generated method stub

	}

}
