package com.iworkstation.imageserver.processor;

import java.util.ArrayList;
import java.util.List;

import com.iworkstation.imageserver.command.BaseImageLevelUpdateCommand;
import com.iworkstation.imageserver.domain.Study;
import com.iworkstation.imageserver.domain.StudyStorageLocation;
import com.iworkstation.imageserver.service.IStudyManager;
import com.iworkstation.imageserver.service.IStudyStorageManager;
import com.iworkstation.imageserver.service.IWorkQueueManager;

public class StudyProcessorContext {
	private StudyStorageLocation storageLocation;
	private Study study;
	private IWorkQueueManager workQueueManager;
	private IStudyStorageManager studyStorageManager;
	private IStudyManager studyManager;
	private List<BaseImageLevelUpdateCommand> updateCommands = new ArrayList<BaseImageLevelUpdateCommand>();

	public StudyProcessorContext(StudyStorageLocation storageLocation,
			IWorkQueueManager workQueueManager,
			IStudyStorageManager studyStorageManager, IStudyManager studyManager) {
		this.workQueueManager = workQueueManager;
		this.storageLocation = storageLocation;
		this.studyManager = studyManager;
		this.studyStorageManager = studyStorageManager;
	}

	public StudyStorageLocation getStorageLocation() {
		return storageLocation;
	}

	public Study getStudy() {
		return study;
	}

	public IWorkQueueManager getWorkQueueManager() {
		return workQueueManager;
	}

	public List<BaseImageLevelUpdateCommand> getUpdateCommands() {
		return updateCommands;
	}

	public IStudyManager getStudyManager() {
		return studyManager;
	}

	public IStudyStorageManager getStudyStorageManager() {
		return studyStorageManager;
	}
}
