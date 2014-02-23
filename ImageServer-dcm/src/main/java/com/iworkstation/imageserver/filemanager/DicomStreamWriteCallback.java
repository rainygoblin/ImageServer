package com.iworkstation.imageserver.filemanager;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.Tag;
import org.dcm4che2.data.UID;
import org.dcm4che2.data.VR;
import org.dcm4che2.io.DicomOutputStream;
import org.dcm4che2.net.PDVInputStream;

public final class DicomStreamWriteCallback implements StreamWriteCallback {
	private static final Log LOG = LogFactory
			.getLog(DicomStreamWriteCallback.class);

	private DicomObject fileMetaInfoObject;
	private DicomObject dicomHeader;

	public synchronized DicomObject getFileMetaInfoObject() {
		return fileMetaInfoObject;
	}

	public synchronized DicomObject getDicomHeader() {
		return dicomHeader;
	}

	/*
	 * doWrite first, convert inputStream to PDVInputStream second, read dicom
	 * object from inputStream third, write outputStream forth, get dicom header
	 */
	@Override
	public synchronized void doWrite(InputStream inputStream,
			OutputStream outputStream) {

		try {
			String inputTransferSyntaxUid = UID.ImplicitVRLittleEndian;
			if (fileMetaInfoObject != null) {
				inputTransferSyntaxUid = fileMetaInfoObject.getString(
						Tag.TransferSyntaxUID, UID.ImplicitVRLittleEndian);
			}

			DicomOutputStream dicomOutputStream = new DicomOutputStream(
					outputStream);
			PDVInputStream dataStream = (PDVInputStream) inputStream;
			dicomHeader = dataStream.readDataset();
			dicomHeader.putString(Tag.TransferSyntaxUID, VR.UI,
					inputTransferSyntaxUid);
			dicomOutputStream.writeDicomFile(dicomHeader);
			dicomOutputStream.writeFileMetaInformation(fileMetaInfoObject);
			dicomOutputStream.close();
			dicomHeader = dicomHeader.subSet(Tag.AffectedSOPClassUID,
					Tag.InstanceNumber);
		} catch (Exception ex) {
			LOG.error(ex);
			throw new RuntimeException(ex);
		} finally {
			IOUtils.closeQuietly(inputStream);
			IOUtils.closeQuietly(outputStream);
		}
	}

	public void setFileMetaInfoObject(DicomObject fileMetaInfoObject) {
		this.fileMetaInfoObject = fileMetaInfoObject;
	}

}
