package com.iworkstation.imageserver.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iworkstation.imageserver.domain.Device;
import com.iworkstation.imageserver.domain.ServerPartition;
import com.iworkstation.imageserver.enumeration.DeviceTypeEnum;
import com.iworkstation.imageserver.repository.IGenericRepository;
import com.iworkstation.imageserver.service.IDeviceManager;

@Service
public class DeviceManagerImpl implements IDeviceManager {
	private static final Log LOG = LogFactory.getLog(DeviceManagerImpl.class);

	@Autowired
	private IGenericRepository<String, Device> deviceRepository;

	@Autowired
	private IGenericRepository<String, ServerPartition> serverPartitionRepository;

	@Override
	@Transactional
	public Device lookupDevice(String partitionGUID, String callingAE,
			String callingIpAddress) {
		Device foundDevice = null;
		ServerPartition partition = serverPartitionRepository
				.findById(partitionGUID);
		if (partition == null) {
			LOG.error("Can not get the ServerPartition of:" + partitionGUID);
			throw new IllegalArgumentException(
					"Can not get the ServerPartition of:" + partitionGUID);
		}
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("aeTitle", callingAE);
		parms.put("serverPartitionGUID", partitionGUID);
		foundDevice = deviceRepository.findUniqueByProps(parms);
		if (foundDevice == null) {
			if (partition.isAcceptAnyDevice()) {
				if (partition.isAutoInsertDevice()) {
					foundDevice = new Device();
					foundDevice.setAeTitle(callingAE);
					foundDevice.setDescription(String.format("AE: %s",
							callingAE));
					foundDevice.setIpAddress(callingIpAddress);
					foundDevice.setPort(partition.getDefaultRemotePort());
					foundDevice.setServerPartitionGUID(partitionGUID);
					foundDevice.setDeviceTypeEnum(DeviceTypeEnum.Workstation);
				}
			}
		}
		foundDevice.setLastAccessedTime(new Date());
		deviceRepository.saveOrUpdateEntity(foundDevice);
		return foundDevice;
	}

	@Override
	@Transactional
	public Device loadRemoteHost(String partitionGUID, String remoteAE) {
		Device foundDevice = null;
		ServerPartition partition = serverPartitionRepository
				.findById(partitionGUID);
		if (partition == null) {
			LOG.error("Can not get the ServerPartition of:" + partitionGUID);
			throw new IllegalArgumentException(
					"Can not get the ServerPartition of:" + partitionGUID);
		}
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("aeTitle", remoteAE);
		parms.put("serverPartitionGUID", partitionGUID);
		foundDevice = deviceRepository.findUniqueByProps(parms);

		return foundDevice;
	}
}
