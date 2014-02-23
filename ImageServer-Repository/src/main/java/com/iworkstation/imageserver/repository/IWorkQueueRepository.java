package com.iworkstation.imageserver.repository;

import com.iworkstation.imageserver.domain.WorkQueue;
import com.iworkstation.imageserver.parameter.InsertWorkQueueParameters;
import com.iworkstation.imageserver.parameter.PostponeWorkQueueParameters;
import com.iworkstation.imageserver.parameter.UpdateWorkQueueParameters;
import com.iworkstation.imageserver.parameter.WorkQueueQueryParameters;

public interface IWorkQueueRepository extends
		IGenericRepository<String, WorkQueue> {
	WorkQueue InsertWorkQueue(
			InsertWorkQueueParameters insertWorkQueueParameters);

	WorkQueue queryWorkQueue(WorkQueueQueryParameters parms);

	void updateWorkQueue(UpdateWorkQueueParameters parms);

	void postponeWorkQueue(PostponeWorkQueueParameters parameters);
}
