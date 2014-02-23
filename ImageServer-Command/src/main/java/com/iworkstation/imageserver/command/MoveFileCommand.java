package com.iworkstation.imageserver.command;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MoveFileCommand extends ServerCommand {
	private static final Log LOG = LogFactory.getLog(MoveFileCommand.class);

	private final File srcDir;
	private final File destDir;

	public MoveFileCommand(String src, String dest) {
		super(String.format("MoveFile {%s}", src), true);
		srcDir = new File(src);
		if (!srcDir.exists()) {
			throw new IllegalArgumentException(
					"The file to be copy should not be null!");

		}

		destDir = new File(dest);
		if (destDir.exists()) {
			destDir.delete();
		}
	}

	@Override
	public void onExecute(ServerCommandProcessor theProcessor)
			throws CommandException {
		try {
			FileUtils.moveFile(srcDir, destDir);
		} catch (IOException e) {
			LOG.error(e);
			throw new CommandException(e.getMessage());
		}
	}

	@Override
	public void onUndo() throws CommandException {
		// TODO Auto-generated method stub

	}

}
