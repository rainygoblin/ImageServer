package com.iworkstation.imageserver.service;

import com.iworkstation.imageserver.domain.Device;


public interface IDeviceManager {
	Device lookupDevice(String partitionGUID, String callingAE,
			String callingIpAddress);
	Device loadRemoteHost(String partitionGUID, String remoteAE);
}
