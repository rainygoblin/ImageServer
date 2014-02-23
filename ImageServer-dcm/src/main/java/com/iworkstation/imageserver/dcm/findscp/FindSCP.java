package com.iworkstation.imageserver.dcm.findscp;

import java.util.concurrent.Executor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.Tag;
import org.dcm4che2.data.UID;
import org.dcm4che2.net.Association;
import org.dcm4che2.net.DicomServiceException;
import org.dcm4che2.net.DimseRSP;
import org.dcm4che2.net.SingleDimseRSP;
import org.dcm4che2.net.Status;
import org.dcm4che2.net.service.CFindService;

import com.iworkstation.imageserver.domain.ServerPartition;
import com.iworkstation.imageserver.service.IDeviceManager;
import com.iworkstation.imageserver.service.IPatientManager;
import com.iworkstation.imageserver.service.ISeriesManager;
import com.iworkstation.imageserver.service.IStudyManager;
import com.iworkstation.imageserver.service.IStudyStorageManager;

public final class FindSCP extends CFindService {

	private static final Log LOG = LogFactory.getLog(FindSCP.class);
	private static final String[] sopClasses = {
			UID.PatientRootQueryRetrieveInformationModelFIND,
			UID.StudyRootQueryRetrieveInformationModelFIND,
			UID.PatientStudyOnlyQueryRetrieveInformationModelFINDRetired };

	private final ServerPartition partition;
	private IPatientManager patientManager;
	private IStudyManager studyManager;
	private ISeriesManager seriesManager;
	private IDeviceManager deviceManager;
	private IStudyStorageManager studyStorageManager;

	public FindSCP(Executor executor, ServerPartition partition) {
		super(sopClasses, executor);
		this.partition = partition;
	}

	public IPatientManager getPatientManager() {
		return patientManager;
	}

	public void setPatientManager(IPatientManager patientManager) {
		this.patientManager = patientManager;
	}

	public void setStudyManager(IStudyManager studyManager) {
		this.studyManager = studyManager;
	}

	public void setSeriesManager(ISeriesManager seriesManager) {
		this.seriesManager = seriesManager;
	}

	public void setStudyStorageManager(IStudyStorageManager studyStorageManager) {
		this.studyStorageManager = studyStorageManager;
	}

	public void setDeviceManager(IDeviceManager deviceManager) {
		this.deviceManager = deviceManager;
	}

	@Override
	protected DimseRSP doCFind(Association as, int pcid, DicomObject cmd,
			DicomObject data, DicomObject rsp) throws DicomServiceException {

		com.iworkstation.imageserver.domain.Device remoteDevice = deviceManager
				.lookupDevice(partition.getGuid(), as.getCallingAET(), as
						.getSocket().getRemoteSocketAddress().toString());
		if (remoteDevice == null) {
			LOG.error("The remoteDevice should not be null!");
			throw new DicomServiceException(cmd, Status.ProcessingFailure,
					"The remoteDevice should not be null!");
		}

		String cuid = cmd.getString(Tag.AffectedSOPClassUID);
		if (cuid == null) {
			LOG.error("The AffectedSOPClassUID should not be null!");
			throw new DicomServiceException(cmd, Status.ProcessingFailure,
					"The AffectedSOPClassUID should not be null!");
		}

		String level = data.getString(Tag.QueryRetrieveLevel);
		if (level == null) {
			LOG.error("The QueryRetrieveLevel should not be null!");
			throw new DicomServiceException(cmd, Status.ProcessingFailure,
					"The QueryRetrieveLevel should not be null!");
		}

		if (cuid.equals(UID.StudyRootQueryRetrieveInformationModelFIND)) {
			if (level.equals("STUDY")) {
				return new StudyLeveQuery(partition, studyManager,
						seriesManager).query(data, rsp);
			}
			if (level.equals("SERIES")) {
				return new SeriesLeveQuery(partition, studyStorageManager,
						studyManager, seriesManager).query(data, rsp);
			}
			if (level.equals("IMAGE")) {

			}
		}

		if (cuid.equals(UID.PatientRootQueryRetrieveInformationModelFIND)) {
			if (level.equals("PATIENT")) {
				return new PatientLeveQuery(partition, patientManager).query(
						data, rsp);
			}
		}
		return new SingleDimseRSP(rsp);
	}

}
