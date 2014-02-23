package com.iworkstation.imageserver.command;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CopyDirectoryCommand extends ServerCommand {
	private static final Log LOG = LogFactory
			.getLog(CopyDirectoryCommand.class);

	private final File srcDir;
	private final File destDir;

	public CopyDirectoryCommand(String src, String dest) {
		super(String.format("CopyDirectory {%s}", src), true);
		srcDir = new File(src);
		if (!srcDir.exists()) {
			LOG.error("The directory to be copy should not be null!");
			throw new IllegalArgumentException(
					"The directory to be copy should not be null!");

		}

		destDir = new File(dest);
		if (!destDir.exists()) {
			destDir.mkdir();
		}
	}

	@Override
	public void onExecute(ServerCommandProcessor theProcessor)
			throws CommandException {
		try {
			FileUtils.copyDirectory(srcDir, destDir);
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
