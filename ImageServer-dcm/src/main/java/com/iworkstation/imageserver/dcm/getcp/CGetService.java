package com.iworkstation.imageserver.dcm.getcp;

import java.io.IOException;
import java.util.concurrent.Executor;

import org.dcm4che2.data.DicomObject;
import org.dcm4che2.net.Association;
import org.dcm4che2.net.CommandUtils;
import org.dcm4che2.net.DicomServiceException;
import org.dcm4che2.net.DimseRSP;
import org.dcm4che2.net.SingleDimseRSP;
import org.dcm4che2.net.Status;
import org.dcm4che2.net.service.CGetSCP;
import org.dcm4che2.net.service.DicomService;

class CGetService extends DicomService implements CGetSCP {
	private final Executor executor;

	public CGetService(String[] sopClasses, Executor executor) {
		super(sopClasses);
		this.executor = executor;
	}

	public CGetService(String sopClass, Executor executor) {
		super(sopClass);
		this.executor = executor;
	}

	@Override
	public void cget(Association as, int pcid, DicomObject cmd, DicomObject data)
			throws DicomServiceException, IOException {
		DicomObject cmdrsp = CommandUtils.mkRSP(cmd, CommandUtils.SUCCESS);
		DimseRSP rsp = doCGet(as, pcid, cmd, data, cmdrsp);
		try {
			rsp.next();
		} catch (InterruptedException e) {
			throw new DicomServiceException(cmd, Status.ProcessingFailure);
		}
		cmdrsp = rsp.getCommand();
		if (CommandUtils.isPending(cmdrsp)) {
			as.registerCancelRQHandler(cmd, rsp);
			executor.execute(new WriteMultiDimseRsp(as, pcid, rsp));
		} else {
			as.writeDimseRSP(pcid, cmdrsp, rsp.getDataset());
		}

	}

	protected DimseRSP doCGet(Association as, int pcid, DicomObject cmd,
			DicomObject data, DicomObject cmdrsp) throws DicomServiceException {
		return new SingleDimseRSP(cmdrsp);
	}

}
