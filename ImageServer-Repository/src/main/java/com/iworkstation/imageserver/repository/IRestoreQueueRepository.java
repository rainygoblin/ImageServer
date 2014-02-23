package com.iworkstation.imageserver.repository;

import com.iworkstation.imageserver.domain.RestoreQueue;
import com.iworkstation.imageserver.parameter.InsertRestoreQueueParameters;

public interface IRestoreQueueRepository extends
		IGenericRepository<String, RestoreQueue> {
	public RestoreQueue insertRestoreQueue(
			InsertRestoreQueueParameters insertRestoreQueueParameters);
}
