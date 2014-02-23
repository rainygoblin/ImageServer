package com.iworkstation.imageserver.service;

import java.util.List;

import com.iworkstation.imageserver.domain.ServerPartition;

public interface IServerPartitionManager {
	List<ServerPartition> loadAll();

	ServerPartition findByAETitle(String calledAET);
}
