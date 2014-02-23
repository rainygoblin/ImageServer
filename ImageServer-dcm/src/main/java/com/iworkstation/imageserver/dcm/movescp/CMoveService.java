package com.iworkstation.imageserver.dcm.movescp;

import java.io.IOException;
import java.util.concurrent.Executor;

import org.dcm4che2.data.DicomObject;
import org.dcm4che2.net.Association;
import org.dcm4che2.net.CommandUtils;
import org.dcm4che2.net.DicomServiceException;
import org.dcm4che2.net.DimseRSP;
import org.dcm4che2.net.SingleDimseRSP;
import org.dcm4che2.net.Status;
import org.dcm4che2.net.service.CMoveSCP;
import org.dcm4che2.net.service.DicomService;

class CMoveService extends DicomService implements CMoveSCP {
	private final Executor executor;

	public CMoveService(String[] sopClasses, Executor executor) {
		super(sopClasses);
		this.executor = executor;
	}

	public CMoveService(String sopClass, Executor executor) {
		super(sopClass);
		this.executor = executor;
	}

	@Override
	public void cmove(Association as, int pcid, DicomObject cmd,
			DicomObject data) throws DicomServiceException, IOException {
		DicomObject cmdrsp = CommandUtils.mkRSP(cmd, CommandUtils.SUCCESS);
		DimseRSP rsp = doCMove(as, pcid, cmd, data, cmdrsp);
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

	protected DimseRSP doCMove(Association as, int pcid, DicomObject cmd,
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
