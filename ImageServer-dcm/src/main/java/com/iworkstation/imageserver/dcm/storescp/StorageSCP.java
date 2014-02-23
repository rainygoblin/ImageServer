package com.iworkstation.imageserver.dcm.storescp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dcm4che2.data.BasicDicomObject;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.Tag;
import org.dcm4che2.data.UID;
import org.dcm4che2.net.Association;
import org.dcm4che2.net.CommandUtils;
import org.dcm4che2.net.DicomServiceException;
import org.dcm4che2.net.PDVInputStream;
import org.dcm4che2.net.Status;
import org.dcm4che2.net.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import com.iworkstation.imageserver.command.MoveFileCommand;
import com.iworkstation.imageserver.command.ServerCommandProcessor;
import com.iworkstation.imageserver.command.UpdateWorkQueueCommand;
import com.iworkstation.imageserver.domain.ServerFilesystemInfo;
import com.iworkstation.imageserver.domain.ServerPartition;
import com.iworkstation.imageserver.domain.Study;
import com.iworkstation.imageserver.domain.StudyStorageLocation;
import com.iworkstation.imageserver.enumeration.DuplicateSopPolicyEnum;
import com.iworkstation.imageserver.filemanager.DicomStreamWriteCallback;
import com.iworkstation.imageserver.service.IDeviceManager;
import com.iworkstation.imageserver.service.IFilesystemManager;
import com.iworkstation.imageserver.service.IServerPartitionManager;
import com.iworkstation.imageserver.service.IStudyIntegrityQueueManager;
import com.iworkstation.imageserver.service.IStudyManager;
import com.iworkstation.imageserver.service.IWorkQueueManager;
import com.iworkstation.imageserver.service.exception.NoWritableFilesystemException;
import com.iworkstation.imageserver.service.exception.StudyIsNearlineException;
import com.iworkstation.imageserver.service.exception.StudyNotFoundException;

@Component
public final class StorageSCP extends StorageService {
	private static final Log LOG = LogFactory.getLog(StorageSCP.class);
	private static final String[] sopClasses = {
			UID.BasicStudyContentNotificationSOPClassRetired,
			UID.StoredPrintStorageSOPClassRetired,
			UID.HardcopyGrayscaleImageStorageSOPClassRetired,
			UID.HardcopyColorImageStorageSOPClassRetired,
			UID.ComputedRadiographyImageStorage,
			UID.DigitalXRayImageStorageForPresentation,
			UID.DigitalXRayImageStorageForProcessing,
			UID.DigitalMammographyXRayImageStorageForPresentation,
			UID.DigitalMammographyXRayImageStorageForProcessing,
			UID.DigitalIntraoralXRayImageStorageForPresentation,
			UID.DigitalIntraoralXRayImageStorageForProcessing,
			UID.StandaloneModalityLUTStorageRetired,
			UID.EncapsulatedPDFStorage, UID.StandaloneVOILUTStorageRetired,
			UID.GrayscaleSoftcopyPresentationStateStorageSOPClass,
			UID.ColorSoftcopyPresentationStateStorageSOPClass,
			UID.PseudoColorSoftcopyPresentationStateStorageSOPClass,
			UID.BlendingSoftcopyPresentationStateStorageSOPClass,
			UID.XRayAngiographicImageStorage, UID.EnhancedXAImageStorage,
			UID.XRayRadiofluoroscopicImageStorage, UID.EnhancedXRFImageStorage,
			UID.XRayAngiographicBiPlaneImageStorageRetired,
			UID.PositronEmissionTomographyImageStorage,
			UID.StandalonePETCurveStorageRetired, UID.CTImageStorage,
			UID.EnhancedCTImageStorage, UID.NuclearMedicineImageStorage,
			UID.UltrasoundMultiframeImageStorageRetired,
			UID.UltrasoundMultiframeImageStorage, UID.MRImageStorage,
			UID.EnhancedMRImageStorage, UID.MRSpectroscopyStorage,
			UID.RTImageStorage, UID.RTDoseStorage, UID.RTStructureSetStorage,
			UID.RTBeamsTreatmentRecordStorage, UID.RTPlanStorage,
			UID.RTBrachyTreatmentRecordStorage,
			UID.RTTreatmentSummaryRecordStorage,
			UID.NuclearMedicineImageStorageRetired,
			UID.UltrasoundImageStorageRetired, UID.UltrasoundImageStorage,
			UID.RawDataStorage, UID.SpatialRegistrationStorage,
			UID.SpatialFiducialsStorage, UID.RealWorldValueMappingStorage,
			UID.SecondaryCaptureImageStorage,
			UID.MultiframeSingleBitSecondaryCaptureImageStorage,
			UID.MultiframeGrayscaleByteSecondaryCaptureImageStorage,
			UID.MultiframeGrayscaleWordSecondaryCaptureImageStorage,
			UID.MultiframeTrueColorSecondaryCaptureImageStorage,
			UID.VLImageStorageTrialRetired, UID.VLEndoscopicImageStorage,
			UID.VideoEndoscopicImageStorage, UID.VLMicroscopicImageStorage,
			UID.VideoMicroscopicImageStorage,
			UID.VLSlideCoordinatesMicroscopicImageStorage,
			UID.VLPhotographicImageStorage, UID.VideoPhotographicImageStorage,
			UID.OphthalmicPhotography8BitImageStorage,
			UID.OphthalmicPhotography16BitImageStorage,
			UID.StereometricRelationshipStorage,
			UID.VLMultiframeImageStorageTrialRetired,
			UID.StandaloneOverlayStorageRetired, UID.BasicTextSRStorage,
			UID.EnhancedSRStorage, UID.ComprehensiveSRStorage,
			UID.ProcedureLogStorage, UID.MammographyCADSRStorage,
			UID.KeyObjectSelectionDocumentStorage, UID.ChestCADSRStorage,
			UID.XRayRadiationDoseSRStorage, UID.EncapsulatedPDFStorage,
			UID.EncapsulatedCDAStorage, UID.StandaloneCurveStorageRetired,
			UID._12leadECGWaveformStorage, UID.GeneralECGWaveformStorage,
			UID.AmbulatoryECGWaveformStorage, UID.HemodynamicWaveformStorage,
			UID.CardiacElectrophysiologyWaveformStorage,
			UID.BasicVoiceAudioWaveformStorage, UID.HangingProtocolStorage,
			UID.SiemensCSANonImageStorage,
			UID.Dcm4cheAttributesModificationNotificationSOPClass };

	@Autowired
	private TaskExecutor taskExecutor;
	@Autowired
	private IServerPartitionManager serverPartitionManager;
	@Autowired
	private IFilesystemManager filesystemManager;
	@Autowired
	private IDeviceManager deviceManager;
	@Autowired
	private IWorkQueueManager workQueueManager;
	@Autowired
	private IStudyManager studyManager;
	@Autowired
	private IStudyIntegrityQueueManager studyIntegrityQueueManager;

	
	public StorageSCP() {
		super(sopClasses);
//		this.taskExecutor = taskExecutor;
//		this.dimseRspDelay = partition.getDimseRspDelay();
//		this.partition = partition;
	}

//	public void setFilesystemManager(IFilesystemManager filesystemManager) {
//		this.filesystemManager = filesystemManager;
//	}
//
//	public void setDeviceManager(IDeviceManager deviceManager) {
//		this.deviceManager = deviceManager;
//	}
//
//	public void setWorkQueueManager(IWorkQueueManager workQueueManager) {
//		this.workQueueManager = workQueueManager;
//	}
//
//	public void setStudyManager(IStudyManager studyManager) {
//		this.studyManager = studyManager;
//	}
//
//	public void setStudyIntegrityQueueManager(
//			IStudyIntegrityQueueManager studyIntegrityQueueManager) {
//		this.studyIntegrityQueueManager = studyIntegrityQueueManager;
//	}

	/**
	 * Overwrite {@link StorageService#cstore} to send delayed C-STORE RSP by
	 * separate Thread, so reading of following received C-STORE RQs from the
	 * open association is not blocked.
	 */
	@Override
	public void cstore(final Association as, final int pcid, DicomObject rq,
			PDVInputStream dataStream, String tsuid)
			throws DicomServiceException, IOException {
		final DicomObject rsp = CommandUtils.mkRSP(rq, CommandUtils.SUCCESS);
		
		ServerPartition currentPartition=serverPartitionManager.findByAETitle(as.getCalledAET());
		
		
		onCStoreRQ(currentPartition,as, pcid, rq, dataStream, tsuid, rsp);
		
		
		if (currentPartition.getDimseRspDelay() > 0) {
			final int dimseRspDelay=currentPartition.getDimseRspDelay();
			taskExecutor.execute(new Runnable() {
				public void run() {
					try {
						Thread.sleep(dimseRspDelay);
						as.writeDimseRSP(pcid, rsp);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} else {
			as.writeDimseRSP(pcid, rsp);
		}
		
		onCStoreRSP(as, pcid, rq, dataStream, tsuid, rsp);
	}

	protected void onCStoreRQ(ServerPartition currentServerPartition,Association as, int pcid, DicomObject rq,
			PDVInputStream dataStream, String tsuid, DicomObject rsp)
			throws IOException, DicomServiceException {

		com.iworkstation.imageserver.domain.Device remoteDevice = deviceManager
				.lookupDevice(currentServerPartition.getGuid(), as.getCallingAET(), as
						.getSocket().getRemoteSocketAddress().toString());

		BasicDicomObject fileMetaInfoObject = null;
		String cuid = rq.getString(Tag.AffectedSOPClassUID);
		String iuid = rq.getString(Tag.AffectedSOPInstanceUID);
		fileMetaInfoObject = new BasicDicomObject();
		fileMetaInfoObject.initFileMetaInformation(cuid, iuid, tsuid);

		DicomStreamWriteCallback dicomStreamWriteCallback = new DicomStreamWriteCallback();
		dicomStreamWriteCallback.setFileMetaInfoObject(fileMetaInfoObject);

		// for test
		ServerFilesystemInfo serverFilesystemInfo = filesystemManager
				.selectFilesystem();
		String tempFileName = FilenameUtils
				.concat(serverFilesystemInfo.getTempDir(), UUID.randomUUID()
						.toString());
		dicomStreamWriteCallback.doWrite(dataStream, new FileOutputStream(
				tempFileName));

		DicomObject dicomHeader = dicomStreamWriteCallback.getDicomHeader();
		if (dicomHeader == null) {
			throw new DicomServiceException(rq, Status.ProcessingFailure,
					"Can not get the Dicom header");
		}

		String transferSyntaxUID = dicomHeader.getString(Tag.TransferSyntaxUID);

		String studyInstanceUid = dicomHeader.getString(Tag.StudyInstanceUID);
		String seriesInstanceUid = dicomHeader.getString(Tag.SeriesInstanceUID);
		String sopInstanceUid = dicomHeader.getString(Tag.SOPInstanceUID);
		String studyDate = dicomHeader.getString(Tag.StudyDate);

		ServerCommandProcessor commandProcessor = new ServerCommandProcessor(
				String.format("Processing Sop Instance %s", sopInstanceUid));

		String failureMessage;
		StudyStorageLocation studyLocation = null;
		try {
			studyLocation = getWritableOnlineStorage(currentServerPartition.getGuid(),
					studyInstanceUid, studyDate, transferSyntaxUID);

			String finalDest = studyLocation.getSopInstancePath(
					seriesInstanceUid, sopInstanceUid);

			if (hasUnprocessedCopy(studyLocation.getGuid(), seriesInstanceUid,
					sopInstanceUid)) {
				failureMessage = String
						.format("Another copy of the SOP Instance was received but has not been processed: %s",
								sopInstanceUid);
				LOG.error(failureMessage);
				throw new DicomServiceException(rq,
						Status.DuplicateSOPinstance, failureMessage);
			}
			File finalDestFile = new File(finalDest);
			if (finalDestFile.exists()) {
				String reconcileSopInstancePath = FilenameUtils.concat(
						serverFilesystemInfo.getReconcileStorageFolder(),
						studyLocation.getReconcileSopInstancePath(
								remoteDevice.getAeTitle(), studyInstanceUid,
								seriesInstanceUid, sopInstanceUid));
				handleDuplicate(currentServerPartition,sopInstanceUid, seriesInstanceUid,
						studyInstanceUid, currentServerPartition.getGuid(), tempFileName,
						reconcileSopInstancePath, remoteDevice, studyLocation,
						commandProcessor);

			} else {
				handleNonDuplicate(seriesInstanceUid, sopInstanceUid,
						studyLocation, tempFileName, finalDest, remoteDevice,
						commandProcessor);
			}

			if (!commandProcessor.execute()) {
				failureMessage = String
						.format("Failure processing message: %s. Sending failure status.",
								commandProcessor.getFailureReason());
				LOG.error(failureMessage);
				throw new DicomServiceException(rq, Status.ProcessingFailure,
						failureMessage);
			}
		} catch (NoWritableFilesystemException e) {

			failureMessage = String
					.format("Unable to process image, no writable filesystem found for Study UID %s.",
							sopInstanceUid);
			LOG.error(failureMessage);
			throw new DicomServiceException(rq, Status.ProcessingFailure,
					failureMessage);
		} catch (StudyIsNearlineException e) {
			failureMessage = e.isRestoreRequested() ? String.format(
					"%s. Restore has been requested.", e.getMessage()) : e
					.getMessage();

			commandProcessor.rollback();
			LOG.error(failureMessage);
			throw new DicomServiceException(rq, Status.ProcessingFailure,
					failureMessage);
		} catch (Throwable e) {
			failureMessage = String.format("%s.  Rolling back operation.",
					e.getMessage());
			commandProcessor.rollback();
			LOG.error(failureMessage);
			throw new DicomServiceException(rq, Status.ProcessingFailure,
					failureMessage);
		}

		// delete the temp dicom file
		if (new File(tempFileName).exists()) {
			new File(tempFileName).delete();
		}
		commandProcessor = null;
		// super.onCStoreRQ(as, pcid, rq, dataStream, tsuid, rsp);
	}

	private void handleNonDuplicate(String seriesInstanceUid,
			String sopInstanceUid, StudyStorageLocation studyLocation,
			String tempFileName, String finalDest,
			com.iworkstation.imageserver.domain.Device remoteDevice,
			ServerCommandProcessor commandProcessor) throws IOException {
		commandProcessor
				.addCommand(new MoveFileCommand(tempFileName, finalDest));

		commandProcessor.addCommand(new UpdateWorkQueueCommand(
				workQueueManager, remoteDevice.getGuid(), seriesInstanceUid,
				studyLocation, sopInstanceUid));

	}

	private void handleDuplicate(ServerPartition currentServerPartition,String sopInstanceUid,
			String seriesInstanceUid, String studyInstanceUid,
			String partitionGUID, String tempFileName,
			String reconcileSopInstancePath,
			com.iworkstation.imageserver.domain.Device remoteDevice,
			StudyStorageLocation studyLocation,
			ServerCommandProcessor commandProcessor)
			throws DicomServiceException {
		Study study = studyManager.findStudy(studyInstanceUid, partitionGUID);

		if (study != null) {
			LOG.info(String
					.format("Received duplicate SOP %s (A#:%s StudyUid:%s  Patient: %s  ID:%s)",
							sopInstanceUid, study.getAccessionNumber(),
							study.getStudyInstanceUid(),
							study.getPatientsName(), study.getPatientId()));
		} else {
			LOG.info(String
					.format("Received duplicate SOP %s (StudyUid:%s). Existing files haven't been processed.",
							sopInstanceUid, studyInstanceUid));
		}
		String failureMessage;

		if (currentServerPartition.getDuplicateSopPolicyEnum() == DuplicateSopPolicyEnum.SendSuccess) {
			LOG.info(String
					.format("Duplicate SOP Instance received, sending success response %s",
							sopInstanceUid));
			return;
		}
		if (currentServerPartition.getDuplicateSopPolicyEnum() == DuplicateSopPolicyEnum.RejectDuplicates) {
			failureMessage = String.format(
					"Duplicate SOP Instance received, rejecting %s",
					sopInstanceUid);
			LOG.error(failureMessage);
			throw new DicomServiceException(null, Status.DuplicateSOPinstance,
					failureMessage);
		}
		if (currentServerPartition.getDuplicateSopPolicyEnum() == DuplicateSopPolicyEnum.CompareDuplicates) {
			commandProcessor.addCommand(new MoveFileCommand(tempFileName,
					reconcileSopInstancePath));
			commandProcessor.addCommand(new UpdateWorkQueueCommand(
					workQueueManager, remoteDevice.getGuid(),
					seriesInstanceUid, studyLocation, sopInstanceUid, true,
					"dup", remoteDevice.getAeTitle()));
		} else {
			failureMessage = String
					.format("Duplicate SOP Instance received. Unsupported duplicate policy %s.",
							currentServerPartition.getDuplicateSopPolicyEnum());
			LOG.error(failureMessage);
			throw new DicomServiceException(null, Status.DuplicateSOPinstance,
					failureMessage);
		}

	}

	private boolean hasUnprocessedCopy(String studyStorageGUID,
			String seriesInstanceUid, String sopInstanceUid) {
		if (workQueueManager.workQueueUidExists(studyStorageGUID,
				seriesInstanceUid, sopInstanceUid))
			return true;
		return studyIntegrityQueueManager.studyIntegrityUidExists(
				studyStorageGUID, seriesInstanceUid, sopInstanceUid);
	}

	private StudyStorageLocation getWritableOnlineStorage(
			String serverPartitionGUID, String studyInstanceUid,
			String studyDate, String transferSyntaxUID)
			throws NoWritableFilesystemException, StudyIsNearlineException,
			StudyNotFoundException {
		StudyStorageLocation studyLocation = filesystemManager
				.getOrCreateWritableStudyStorageLocation(studyInstanceUid,
						studyDate, transferSyntaxUID, serverPartitionGUID);

		return studyLocation;
	}

}