package com.iworkstation.imageserver.dcm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dcm4che2.data.UID;
import org.dcm4che2.net.Device;
import org.dcm4che2.net.NetworkApplicationEntity;
import org.dcm4che2.net.NetworkConnection;
import org.dcm4che2.net.TransferCapability;
import org.dcm4che2.net.service.DicomService;
import org.dcm4che2.net.service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.iworkstation.imageserver.dcm.findscp.FindSCP;
import com.iworkstation.imageserver.dcm.movescp.MoveSCP;
import com.iworkstation.imageserver.dcm.storescp.StgCmtSCP;
import com.iworkstation.imageserver.dcm.storescp.StorageSCP;
import com.iworkstation.imageserver.domain.ServerPartition;
import com.iworkstation.imageserver.service.IDeviceManager;
import com.iworkstation.imageserver.service.IFilesystemManager;
import com.iworkstation.imageserver.service.IPatientManager;
import com.iworkstation.imageserver.service.ISeriesManager;
import com.iworkstation.imageserver.service.IServerPartitionManager;
import com.iworkstation.imageserver.service.IStudyIntegrityQueueManager;
import com.iworkstation.imageserver.service.IStudyManager;
import com.iworkstation.imageserver.service.IStudyStorageManager;
import com.iworkstation.imageserver.service.IWorkQueueManager;

@Service("dcmListener")
public final class DcmListener extends Thread implements ApplicationContextAware{
	private static final Log LOG = LogFactory.getLog(DcmListener.class);
	private static final String[] ONLY_DEF_TS = { UID.ImplicitVRLittleEndian };
	private static final String[] NON_RETIRED_LE_TS = { UID.JPEGLSLossless,
			UID.JPEGLossless, UID.JPEGLosslessNonHierarchical14,
			UID.JPEG2000LosslessOnly, UID.DeflatedExplicitVRLittleEndian,
			UID.RLELossless, UID.ExplicitVRLittleEndian,
			UID.ImplicitVRLittleEndian, UID.JPEGBaseline1, UID.JPEGExtended24,
			UID.JPEGLSLossyNearLossless, UID.JPEG2000, UID.MPEG2, };

	private String[] tsuids = NON_RETIRED_LE_TS;

	private List<Device> workingDevices = new ArrayList<Device>();

	@Autowired
	private IServerPartitionManager serverPartitionManager;
	@Autowired
	private IStudyStorageManager studyStorageManager;
	@Autowired
	private IDeviceManager deviceManager;
	@Autowired
	private IPatientManager patientManager;
	@Autowired
	private IStudyManager studyManager;
	@Autowired
	private ISeriesManager seriesManager;
	@Autowired
	private StorageSCP storageSCP;
	@Autowired
	private TaskExecutor taskExecutor;

	//inject the application context
	private ApplicationContext applicationContext;
	public void setApplicationContext(ApplicationContext applicationContext){
		this.applicationContext=applicationContext;
	}
//	
//	public  IFilesystemManager getFilesystemManager() {
//		if (filesystemManager == null) {
//			throw new IllegalStateException(
//					"filesystemManager is not specified. Must specify filesystemManager");
//		}
//		return filesystemManager;
//	}
//
//	public  IStudyStorageManager getStudyStorageManager() {
//		if (studyStorageManager == null) {
//			throw new IllegalStateException(
//					"studyStorageManager is not specified. Must specify studyStorageManager");
//		}
//		return studyStorageManager;
//	}
//
//	public  IDeviceManager getDeviceManager() {
//		if (deviceManager == null) {
//			throw new IllegalStateException(
//					"deviceManager is not specified. Must specify deviceManager");
//		}
//		return deviceManager;
//	}
//
//	public  IWorkQueueManager getWorkQueueManager() {
//		if (workQueueManager == null) {
//			throw new IllegalStateException(
//					"workQueueManager is not specified. Must specify workQueueManager");
//		}
//		return workQueueManager;
//	}
//
//	public  IPatientManager getPatientManager() {
//		if (patientManager == null) {
//			throw new IllegalStateException(
//					"patientManager is not specified. Must specify patientManager");
//		}
//		return patientManager;
//	}
//
//	public  IStudyManager getStudyManager() {
//		if (studyManager == null) {
//			throw new IllegalStateException(
//					"studyManager is not specified. Must specify studyManager");
//		}
//		return studyManager;
//	}
//
//	public  ISeriesManager getSeriesManager() {
//		if (seriesManager == null) {
//			throw new IllegalStateException(
//					"seriesManager is not specified. Must specify seriesManager");
//		}
//		return seriesManager;
//	}
//
//	public  IStudyIntegrityQueueManager getStudyIntegrityQueueManager() {
//		if (studyIntegrityQueueManager == null) {
//			throw new IllegalStateException(
//					"studyIntegrityQueueManager is not specified. Must specify studyIntegrityQueueManager");
//		}
//		return studyIntegrityQueueManager;
//	}

	@PostConstruct
	public void init() {
		LOG.info("Start the Dicom Receive Service to receive dicom message.");
		setDaemon(true);
		setName("DICOM Receive Service");
		start();
	}

	@PreDestroy
	public void cleanup() {
		for (Device device : workingDevices) {
			device.stopListening();
		}
	}

	public void run() {
		List<ServerPartition> serverPartitions = serverPartitionManager
				.loadAll();
		for (ServerPartition serverPartition : serverPartitions) {
			if (serverPartition.isEnabled()) {
				// initial the networkconnection
				NetworkConnection nc = new NetworkConnection();

				// set the hostname
				// if (serverPartition.getHostName() != null) {
				// nc.setHostname(serverPartition.getHostName());
				// } else {
				// nc.setHostname("localhost");
				// }

				nc.setPort(serverPartition.getPort());
				nc.setTcpNoDelay(true);
				// nc.setTcpNoDelay(serverPartition.isTcpNoDelay());
				// nc.setAcceptTimeout(serverPartition.getAcceptTimeOut());
				// nc.setRequestTimeout(serverPartition.getRequestTimeout());
				// nc.setReleaseTimeout(serverPartition.getReleaseTimeout());
				// nc.setSocketCloseDelay(serverPartition.getSocketCloseDelay());

				NetworkApplicationEntity ae = new NetworkApplicationEntity();

				List<DicomService> dicomServices = new ArrayList<DicomService>();
				// Initial the executor
//				Executor executor=ApplicationContextProvider.getBean("taskExecutor");
//				Executor executor = Executors.newFixedThreadPool(
//						serverPartition.getNumberOfThreads(),
//						new DeviceListenerThreadFactory(serverPartition
//								.getName(), serverPartition.getPort()));

//				new NewThreadExecutor(serverPartition.getName());
				ae.setPackPDV(true);
				ae.setNetworkConnection(nc);
				ae.setAssociationAcceptor(true);

				//register the verification service to device
				ae.register(new VerificationService());
				

//				StorageSCP storageSCP = new StorageSCP(executor,
//						serverPartition);
//				storageSCP.setFilesystemManager(getFilesystemManager());
//				storageSCP.setDeviceManager(getDeviceManager());
//				storageSCP.setWorkQueueManager(getWorkQueueManager());
//				storageSCP
//						.setStudyIntegrityQueueManager(studyIntegrityQueueManager);
//				storageSCP.setStudyManager(getStudyManager());
				ae.register(storageSCP);
				dicomServices.add(storageSCP);

				// register the study commit scp
				if (serverPartition.isStgcmtEnabled()) {
					ae.register(new StgCmtSCP(serverPartition.getStgCmtPort()));
				}

				FindSCP findSCP = new FindSCP(taskExecutor, serverPartition);
				findSCP.setPatientManager(patientManager);
				findSCP.setStudyStorageManager(studyStorageManager);
				findSCP.setStudyManager(studyManager);
				findSCP.setSeriesManager(seriesManager);
				findSCP.setDeviceManager(deviceManager);
				ae.register(findSCP);
				dicomServices.add(findSCP);
				
				MoveSCP moveSCP=new MoveSCP(taskExecutor, serverPartition);
				moveSCP.setDeviceManager(deviceManager);
				moveSCP.setStudyManager(studyManager);
				ae.register(moveSCP);
				dicomServices.add(moveSCP);
				
				initTransferCapability(ae, dicomServices,
						serverPartition.isStgcmtEnabled());

				// new Device to construct and initial
				Device device = new Device(serverPartition.getName());
				device.setAssociationReaperPeriod(serverPartition
						.getAssociationReaperPeriod());
				device.setNetworkApplicationEntity(ae);
				device.setNetworkConnection(nc);
				// start to listening
				try {
					device.startListening(taskExecutor);
				} catch (IOException e) {
					LOG.error(e);
				}
			}
		}
	}

	private void initTransferCapability(NetworkApplicationEntity ae,
			List<DicomService> dicomServices, boolean isStgcmtEnabled) {
		int length = 0;
		for (DicomService dicomService : dicomServices) {
			length = length + dicomService.getSopClasses().length;
		}
		TransferCapability[] tc;
		if (isStgcmtEnabled) {
			tc = new TransferCapability[length + 2];
			tc[tc.length - 1] = new TransferCapability(
					UID.StorageCommitmentPushModelSOPClass, ONLY_DEF_TS,
					TransferCapability.SCP);
		} else {
			tc = new TransferCapability[length + 1];
		}

		int index = 1;
		tc[0] = new TransferCapability(UID.VerificationSOPClass, ONLY_DEF_TS,
				TransferCapability.SCP);
		for (DicomService dicomService : dicomServices) {
			int tempLength = dicomService.getSopClasses().length;
			for (int i = 0; i < tempLength; i++) {
				tc[index] = new TransferCapability(
						dicomService.getSopClasses()[i], tsuids,
						TransferCapability.SCP);
				index++;
			}
		}

		ae.setTransferCapability(tc);
	}
}
