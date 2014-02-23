package com.iworkstation.imageserver.dcm.storescp;

import java.util.Timer;

import org.dcm4che2.data.DicomObject;
import org.dcm4che2.net.Association;
import org.dcm4che2.net.service.StorageCommitmentService;

public class StgCmtSCP extends StorageCommitmentService {
	private Timer stgcmtTimer;
	private int stgCmtPort;

	public StgCmtSCP(int stgCmtPort) {
		super();
		this.stgCmtPort = stgCmtPort;
	}

	@Override
	protected void onNActionRQ(Association as, int pcid, DicomObject rq,
			DicomObject info, DicomObject rsp) {
		// overwrite by actual StgCmt SCP
	}

	@Override
	protected void onNActionRSP(Association as, int pcid, DicomObject rq,
			DicomObject info, DicomObject rsp) {
		// overwrite by actual StgCmt SCP
	}
}
