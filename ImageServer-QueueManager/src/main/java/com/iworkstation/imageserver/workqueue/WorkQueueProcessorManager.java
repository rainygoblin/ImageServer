package com.iworkstation.imageserver.workqueue;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iworkstation.imageserver.domain.WorkQueue;
import com.iworkstation.imageserver.enumeration.WorkQueueTypeEnum;
import com.iworkstation.imageserver.parameter.UpdateWorkQueueParameters;
import com.iworkstation.imageserver.parameter.WorkQueueQueryParameters;
import com.iworkstation.imageserver.service.IFilesystemManager;
import com.iworkstation.imageserver.service.IStudyManager;
import com.iworkstation.imageserver.service.IStudyStorageManager;
import com.iworkstation.imageserver.service.IWorkQueueManager;

@Service
public class WorkQueueProcessorManager {
	private static final Log LOG = LogFactory
			.getLog(WorkQueueProcessorManager.class);

	private String processorId = null;
	@Autowired
	private IStudyManager studyManager;
	@Autowired
	private IFilesystemManager filesystemManager;
	@Autowired
	private IWorkQueueManager workQueueManager;
	@Autowired
	private IStudyStorageManager studyStorageManager;

	private String getProcessorId() {
		if (processorId == null) {
			try {
				InetAddress inetAddress = InetAddress.getLocalHost();
				processorId = inetAddress.getHostAddress();
			} catch (UnknownHostException e) {
				LOG.error("Can not get the processorid.");
				processorId = "localhost";
			}
		}
		return processorId;
	}

	public WorkQueue getNewWorkQueue() {
		WorkQueueQueryParameters parms = new WorkQueueQueryParameters();
		parms.setProcessorID(getProcessorId());
		//parms.setWorkQueuePriorityEnum(WorkQueuePriorityEnum.Stat.getLookup());
		return workQueueManager.queryWorkQueue(parms);
	}

	public void process(WorkQueue finalWorkQueue)
			throws WorkQueueProcessingException {
		AbstractWorkQueueItemProcessor processor = null;

		processor = getProcessor(finalWorkQueue.getWorkQueueTypeEnum());
		if (processor != null)
			processor.process(finalWorkQueue);
	}

	public void markWorkQueueFailed(WorkQueue finalWorkQueue,
			String errorMessage) {
		UpdateWorkQueueParameters parms = new UpdateWorkQueueParameters();
		parms.setProcessorID(getProcessorId());
		parms.setWorkQueueGUID(finalWorkQueue.getGuid());
		parms.setStudyStorageGUID(finalWorkQueue.getStudyStorageGUID());
		parms.setFailureCount(finalWorkQueue.getFailureCount() + 1);
		parms.setFailureDescription(errorMessage);
		updateWorkQueue(parms);
	}

	private void updateWorkQueue(UpdateWorkQueueParameters parms) {
		workQueueManager.updateWorkQueue(parms);
	}

	private AbstractWorkQueueItemProcessor getProcessor(
			WorkQueueTypeEnum workQueueType) {
		if (WorkQueueTypeEnum.StudyProcess == workQueueType) {
			AbstractWorkQueueItemProcessor processor = new StudyProcessItemProcessor();
			processor.setProcessorId(processorId);
			processor.setFilesystemManager(filesystemManager);
			processor.setStudyManager(studyManager);
			processor.setWorkQueueManager(workQueueManager);
			processor.setStudyStorageManager(studyStorageManager);
			return processor;
		} else {
			return null;
		}
	}
}
