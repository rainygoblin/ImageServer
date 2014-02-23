package com.iworkstation.imageserver.dcm.getcp;

import java.util.concurrent.Executor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.UID;
import org.dcm4che2.net.Association;
import org.dcm4che2.net.DicomServiceException;
import org.dcm4che2.net.DimseRSP;
import org.dcm4che2.net.SingleDimseRSP;

import com.iworkstation.imageserver.domain.ServerPartition;
import com.iworkstation.imageserver.service.IDeviceManager;

public final class GetSCP extends CGetService {
	private static final Log LOG = LogFactory.getLog(CGetService.class);
	private static final String[] sopClasses = {
			UID.PatientStudyOnlyQueryRetrieveInformationModelGETRetired,
			UID.PatientRootQueryRetrieveInformationModelGET,
			UID.StudyRootQueryRetrieveInformationModelGET };

	private final ServerPartition partition;
	private IDeviceManager deviceManager;

	protected GetSCP(Executor executor, ServerPartition partition) {
		super(sopClasses, executor);
		this.partition = partition;
	}

	public void setDeviceManager(IDeviceManager deviceManager) {
		this.deviceManager = deviceManager;
	}

	@Override
	protected DimseRSP doCGet(Association as, int pcid, DicomObject cmd,
			DicomObject data, DicomObject cmdrsp) throws DicomServiceException {
		return new SingleDimseRSP(cmdrsp);
	}
}

class WriteMultiDimseRsp implements Runnable {
	private final Association as;

	private final int pcid;

	private final DimseRSP rsp;

	public WriteMultiDimseRsp(Association as, int pcid, DimseRSP rsp) {
		this.as = as;
		this.pcid = pcid;
		this.rsp = rsp;
	}

	public void run() {
		try {
			try {
				do
					as.writeDimseRSP(pcid, rsp.getCommand(), rsp.getDataset());
				while (rsp.next());
			} catch (DicomServiceException e) {
				as.writeDimseRSP(pcid, e.getCommand(), e.getDataset());
			}
		} catch (Throwable e) {
			as.abort();
		}
	}

}