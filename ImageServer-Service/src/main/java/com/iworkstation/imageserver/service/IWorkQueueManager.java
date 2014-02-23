package com.iworkstation.imageserver.service;

import java.util.List;

import com.iworkstation.imageserver.domain.WorkQueue;
import com.iworkstation.imageserver.domain.WorkQueueTypeProperties;
import com.iworkstation.imageserver.domain.WorkQueueUid;
import com.iworkstation.imageserver.enumeration.WorkQueueTypeEnum;
import com.iworkstation.imageserver.parameter.InsertWorkQueueParameters;
import com.iworkstation.imageserver.parameter.PostponeWorkQueueParameters;
import com.iworkstation.imageserver.parameter.UpdateWorkQueueParameters;
import com.iworkstation.imageserver.parameter.WorkQueueQueryParameters;

public interface IWorkQueueManager {
	WorkQueue insertWorkQueue(
			InsertWorkQueueParameters insertWorkQueueParameters);

	boolean workQueueUidExists(String studyStorageGUID,
			String seriesInstanceUid, String sopInstanceUid);

	void deleteWorkQueueUid(WorkQueueUid uid);

	WorkQueue queryWorkQueue(WorkQueueQueryParameters parms);

	void updateWorkQueue(UpdateWorkQueueParameters parms);

	List<WorkQueueUid> findWorkQueueUidByWorkQueueGUID(String workQueueGUID);

	WorkQueueTypeProperties getWorkQueueProperties(
			WorkQueueTypeEnum workQueueType);

	List<WorkQueue> findWorkQueueByStudyStorage(String studyStorageGUID);

	void postponeWorkQueue(PostponeWorkQueueParameters parameters);
}
