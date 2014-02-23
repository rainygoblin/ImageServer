package com.iworkstation.imageserver.dcm.findscp;

import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.Tag;
import org.dcm4che2.data.VR;
import org.dcm4che2.net.Association;
import org.dcm4che2.net.DimseRSP;
import org.dcm4che2.net.Status;

final class MultiFindRSP implements DimseRSP {
	private static final Log LOG = LogFactory.getLog(MultiFindRSP.class);
	private int index = 0;
	private final DicomObject cmd;
	private List<DicomObject> results;
	private DicomObject currentDicomObject;

	public MultiFindRSP(DicomObject cmd, List<DicomObject> results) {
		this.cmd = cmd;
		this.results = results;
	}

	@Override
	public synchronized boolean next() throws IOException, InterruptedException {
		if (index < 0)
			return false;
		if (results == null) {
			cmd.putInt(Tag.Status, VR.US, Status.Cancel);
		} else {
			try {
				if (index < results.size()) {
					currentDicomObject = results.get(index++);
					cmd.putInt(Tag.Status, VR.US, Status.Pending);
					return true;

				} else {
					cmd.putInt(Tag.Status, VR.US, Status.Success);

				}

			} catch (Exception e) {
				LOG.error(e);
				cmd.putInt(Tag.Status, VR.US, Status.ProcessingFailure);
				cmd.putString(Tag.ErrorComment, VR.LO, e.getMessage());
			}
		}
		results = null;
		currentDicomObject = null;
		index = -1;
		return true;

	}

	@Override
	public DicomObject getCommand() {
		return cmd;
	}

	@Override
	public DicomObject getDataset() {
		return currentDicomObject;
	}

	@Override
	public synchronized void cancel(Association a) throws IOException {
		cmd.putInt(Tag.Status, VR.US, Status.Cancel);
		results = null;
	}

}
