package com.iworkstation.imageserver.dcm.movescp;

import java.util.ArrayList;
import java.util.List;
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
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.iworkstation.imageserver.domain.ServerPartition;
import com.iworkstation.imageserver.domain.Study;
import com.iworkstation.imageserver.service.IDeviceManager;
import com.iworkstation.imageserver.service.IStudyManager;

public class MoveSCP extends CMoveService {
	private static final Log LOG = LogFactory.getLog(MoveSCP.class);
	private static final String[] sopClasses = {
			UID.PatientRootQueryRetrieveInformationModelMOVE,
			UID.StudyRootQueryRetrieveInformationModelMOVE,
			UID.PatientStudyOnlyQueryRetrieveInformationModelMOVERetired };
	private final ServerPartition partition;
	private IDeviceManager deviceManager;
	private IStudyManager studyManager;

	public MoveSCP(Executor executor, ServerPartition partition) {
		super(sopClasses, executor);
		this.partition = partition;
	}

	public void setDeviceManager(IDeviceManager deviceManager) {
		this.deviceManager = deviceManager;
	}

	public void setStudyManager(IStudyManager studyManager) {
		this.studyManager = studyManager;
	}

	@Override
	protected DimseRSP doCMove(Association as, int pcid, DicomObject cmd,
			DicomObject data, DicomObject cmdrsp) throws DicomServiceException {
		com.iworkstation.imageserver.domain.Device remoteDevice = deviceManager
				.lookupDevice(partition.getGuid(), as.getCallingAET(), as
						.getSocket().getRemoteSocketAddress().toString());
		if (remoteDevice == null) {
			LOG.error("The calling remoteDevice should not be null!");
			throw new DicomServiceException(cmd, Status.ProcessingFailure,
					"The calling remoteDevice should not be null!");
		}

		String destinationAE = cmd.getString(Tag.MoveDestination);
		if (destinationAE == null) {
			String faultString = String
					.format("Unknown move destination \"%s\", failing C-MOVE-RQ from %s to %s",
							as.getRemoteAET(), as.getCallingAET(),
							as.getCalledAET());
			LOG.error(faultString);
			throw new DicomServiceException(cmd, Status.ProcessingFailure,
					faultString);
		}

		com.iworkstation.imageserver.domain.Device destinationDevice = deviceManager
				.loadRemoteHost(partition.getGuid(), destinationAE);
		if (destinationDevice == null) {
			LOG.error("The destinationDevice should not be null!");
			throw new DicomServiceException(cmd, Status.ProcessingFailure,
					"The destinationDevice should not be null!");
		}

		// If the remote node is a DHCP node, use its IP address from the
		// connection information, else
		// use what is configured. Always use the configured port.
		if (destinationDevice.getDhcp()) {
			destinationDevice.setIpAddress(as.getSocket()
					.getRemoteSocketAddress().toString());
		}
		String level = data.getString(Tag.QueryRetrieveLevel);
		if (level == null) {
			LOG.error("The QueryRetrieveLevel should not be null!");
			throw new DicomServiceException(cmd, Status.ProcessingFailure,
					"The QueryRetrieveLevel should not be null!");
		}

		List<DicomObject> dicomObjectResults = new ArrayList<DicomObject>();
		boolean isOnline;

		if (level.equals("PATIENT")) {
			isOnline = getSopListForPatient(dicomObjectResults, data);
		}
		if (level.equals("STUDY")) {
			isOnline = getSopListForStudy(dicomObjectResults, data);
		}
		if (level.equals("SERIES")) {
			isOnline = getSopListForSeries(dicomObjectResults, data);
		}
		if (level.equals("IMAGE")) {
			isOnline = getSopListForSop(dicomObjectResults, data);
		}

		return new SingleDimseRSP(cmdrsp);
	}

	private boolean getSopListForSop(List<DicomObject> dicomObjectResults,
			DicomObject data) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean getSopListForSeries(List<DicomObject> dicomObjectResults,
			DicomObject data) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean getSopListForStudy(List<DicomObject> dicomObjectResults,
			DicomObject data) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean getSopListForPatient(List<DicomObject> dicomObjectResults,
			DicomObject data) {
		String patientID = data.getString(Tag.PatientID);
		if (patientID == null) {
			LOG.error("The patientid in the PatientRootQueryRetrieveInformationModelMOVE should not be null!");
			return false;
		}

		List<Criterion> studyCriterions = new ArrayList<Criterion>();
		studyCriterions.add(createStringCondition("serverPartitionGUID",
				partition.getGuid()));
		studyCriterions.add(createStringCondition("patientId",
				data.getString(Tag.PatientID)));
		List<Study> studyList = studyManager.findStudyByCriterions(
				studyCriterions, null);
		return false;
	}

	private Criterion createStringCondition(String propertyName, String val) {
		if (val == null)
			return null;
		if (val.contains("*") || val.contains("?")) {
			// keep the % for orignal char.
			String value = val.replace("%", "[%]").replace("_", "[_]");
			value = value.replace('*', '%');
			value = value.replace('?', '_');
			return Restrictions.like(propertyName, value);
		} else
			return Restrictions.eq(propertyName, val);
	}
}
