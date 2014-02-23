package com.iworkstation.imageserver.command;

import org.dcm4che2.data.DicomObject;

import com.iworkstation.imageserver.domain.StudyStorageLocation;

public class SaveDicomFileCommand extends ServerCommand {

	public SaveDicomFileCommand(StudyStorageLocation studyStorageLocation,
			DicomObject dcmObj, boolean requiresRollback) {
		super("", requiresRollback);
		// TODO Auto-generated constructor stub
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
