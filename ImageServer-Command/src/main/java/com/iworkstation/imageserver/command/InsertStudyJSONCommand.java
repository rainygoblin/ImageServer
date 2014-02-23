package com.iworkstation.imageserver.command;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dcm4che2.data.DicomObject;

import com.iworkstation.imageserver.domain.Study;
import com.iworkstation.imageserver.domain.StudyStorageLocation;

public class InsertStudyJSONCommand extends ServerCommand {
	private static final Log LOG = LogFactory
			.getLog(InsertStudyJSONCommand.class);

	private Study study;
	private StudyStorageLocation storageLocation;

	public InsertStudyJSONCommand(DicomObject dcmObj, Study study,
			StudyStorageLocation storageLocation) {
		super("Insert into Study XML", true);
		this.study = study;
		this.storageLocation = storageLocation;
	}

	@Override
	public void onExecute(ServerCommandProcessor theProcessor)
			throws CommandException {
		LOG.info("Insert Study to the JSON file.");
		try {
			storageLocation.writeStudyToJSON(study);
		} catch (Exception e) {
			LOG.error("Insert the json ,Get the exeption :" + e.getMessage());
			throw new CommandException(e.getMessage());
		}
	}

	@Override
	public void onUndo() throws CommandException {
		// TODO Auto-generated method stub

	}

}
