package com.iworkstation.imageserver.command;

import java.util.Date;

import com.iworkstation.imageserver.domain.StudyStorageLocation;
import com.iworkstation.imageserver.domain.WorkQueue;
import com.iworkstation.imageserver.enumeration.WorkQueueTypeEnum;
import com.iworkstation.imageserver.parameter.InsertWorkQueueParameters;
import com.iworkstation.imageserver.service.IWorkQueueManager;

public class UpdateWorkQueueCommand extends ServerCommand {

	private IWorkQueueManager workQueueManager;
	private String deviceGUID;
	private String seriesInstanceUid;
	private StudyStorageLocation studyLocation;
	private String sopInstanceUid;
	private boolean duplicate = false;
	private String extension;
	private String uidGroupId;

	public UpdateWorkQueueCommand(IWorkQueueManager workQueueManager,
			String deviceGUID, String seriesInstanceUid,
			StudyStorageLocation studyLocation, String sopInstanceUid) {
		super("Update/Insert a WorkQueue Entry", true);
		this.workQueueManager = workQueueManager;
		this.deviceGUID = deviceGUID;
		this.seriesInstanceUid = seriesInstanceUid;
		this.studyLocation = studyLocation;
		this.sopInstanceUid = sopInstanceUid;

	}

	public UpdateWorkQueueCommand(IWorkQueueManager workQueueManager,
			String deviceGUID, String seriesInstanceUid,
			StudyStorageLocation studyLocation, String sopInstanceUid,
			boolean duplicate, String extension, String uidGroupId) {
		super("Update/Insert a WorkQueue Entry", true);
		this.workQueueManager = workQueueManager;
		this.deviceGUID = deviceGUID;
		this.seriesInstanceUid = seriesInstanceUid;
		this.studyLocation = studyLocation;
		this.sopInstanceUid = sopInstanceUid;
		this.duplicate = duplicate;
		this.extension = extension;
		this.uidGroupId = uidGroupId;

	}

	@Override
	public void onExecute(ServerCommandProcessor theProcessor)
			throws CommandException {
		InsertWorkQueueParameters parms = new InsertWorkQueueParameters();
		parms.setDeviceGUID(deviceGUID);
		parms.setScheduledTime(new Date());
		parms.setSeriesInstanceUid(seriesInstanceUid);
		parms.setServerPartitionGUID(studyLocation.getServerPartitionGUID());
		parms.setSopInstanceUid(sopInstanceUid);
		parms.setStudyStorageGUID(studyLocation.getGuid());
		parms.setWorkQueueTypeEnum(WorkQueueTypeEnum.StudyProcess.getLookup());

		if (duplicate) {
			parms.setDuplicate(duplicate);
			parms.setExtension(extension);
			parms.setUidGroupID(uidGroupId);
		}

		WorkQueue workQueue = workQueueManager.insertWorkQueue(parms);
		if (workQueue == null) {
			throw new CommandException("UpdateWorkQueueCommand failed");
		}
	}

	@Override
	public void onUndo() throws CommandException {
		// TODO Auto-generated method stub

	}

}
