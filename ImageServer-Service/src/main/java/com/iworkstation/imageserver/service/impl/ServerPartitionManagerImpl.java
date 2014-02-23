package com.iworkstation.imageserver.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iworkstation.imageserver.domain.ServerPartition;
import com.iworkstation.imageserver.repository.IGenericRepository;
import com.iworkstation.imageserver.service.IServerPartitionManager;

@Service
public class ServerPartitionManagerImpl implements IServerPartitionManager {

	@Autowired
	private IGenericRepository<String, ServerPartition> serverPartitionRepository;

	@Transactional
	public List<ServerPartition> loadAll() {
		return serverPartitionRepository.loadAll();
	}

	@Transactional
	public ServerPartition findByAETitle(String calledAET) {
		ServerPartition serverPartition = null;
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("aeTitle", calledAET);
		serverPartition = serverPartitionRepository.findUniqueByProps(parms);
		return serverPartition;
	}
}
