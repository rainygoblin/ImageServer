package com.iworkstation.imageserver.command;

import org.dcm4che2.data.DicomObject;

public class BaseImageLevelUpdateCommand extends ServerCommand {

	private DicomObject dcmObj;

	public BaseImageLevelUpdateCommand(String description,
			boolean requiresRollback) {
		super(description, requiresRollback);
		// TODO Auto-generated constructor stub
	}

	public void setDcmObj(DicomObject dcmObj) {
		this.dcmObj = dcmObj;
	}

	@Override
	public void onExecute(ServerCommandProcessor theProcessor)
			throws CommandException {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUndo() throws CommandException {
		// TODO Auto-generated method stub

	}

}
