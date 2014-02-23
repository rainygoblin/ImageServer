package com.iworkstation.imageserver.workqueue;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.Tag;
import org.dcm4che2.io.DicomInputStream;
import org.dcm4che2.io.StopTagInputHandler;
import org.dcm4che2.util.CloseUtils;

import com.iworkstation.imageserver.command.DeleteWorkQueueUidCommand;
import com.iworkstation.imageserver.command.FileDeleteCommand;
import com.iworkstation.imageserver.command.ServerCommandProcessor;
import com.iworkstation.imageserver.domain.Study;
import com.iworkstation.imageserver.domain.WorkQueueUid;
import com.iworkstation.imageserver.enumeration.QueueStudyStateEnum;
import com.iworkstation.imageserver.enumeration.StudyStatusEnum;
import com.iworkstation.imageserver.enums.WorkQueueProcessorDatabaseUpdate;
import com.iworkstation.imageserver.enums.WorkQueueProcessorFailureType;
import com.iworkstation.imageserver.enums.WorkQueueProcessorStatus;
import com.iworkstation.imageserver.parameter.LockStudyParameters;
import com.iworkstation.imageserver.processor.ProcessingStatus;
import com.iworkstation.imageserver.processor.SopInstanceProcessor;
import com.iworkstation.imageserver.processor.StudyProcessorContext;

public class StudyProcessItemProcessor extends AbstractWorkQueueItemProcessor {
	private static final Log LOG = LogFactory
			.getLog(StudyProcessItemProcessor.class);
	protected StudyProcessorContext studyProcessorContext;

	@Override
	void onProcess() throws WorkQueueProcessingException {
		LOG.info(String.format("To process %s workQueue",
				workQueueItem.getGuid()));

		checkIfStudyIsLossy();

		boolean successful = false;
		boolean idle = false;

		loadWorkQueueUids();

		int totalUidCount = workQueueUidList.size();

		if (totalUidCount == 0) {
			successful = true;
			idle = true;
		} else {
			studyProcessorContext = new StudyProcessorContext(storageLocation,
					workQueueManager, studyStorageManager, studyManager);

			// TODO the server rule engine

			// Process the images in the list
			successful = processUidList() > 0;
		}

		if (successful) {
			if (idle && workQueueItem.getExpirationTime().before(new Date())) {
				// Run Study / Series Rules Engine.
				// StudyRulesEngine engine = new
				// StudyRulesEngine(StorageLocation, ServerPartition);
				// engine.Apply(ServerRuleApplyTimeEnum.StudyProcessed);

				// Log the FilesystemQueue related entries
				// StorageLocation.LogFilesystemQueue();

				// Delete the queue entry.
				postProcessing(WorkQueueProcessorStatus.Complete,
						WorkQueueProcessorDatabaseUpdate.ResetQueueState);
			} else if (idle)
				postProcessing(WorkQueueProcessorStatus.IdleNoDelete,
						WorkQueueProcessorDatabaseUpdate.ResetQueueState);
			else
				postProcessing(WorkQueueProcessorStatus.Pending,
						WorkQueueProcessorDatabaseUpdate.ResetQueueState);
		} else {
			boolean allFailedDuplicate = true;
			for (WorkQueueUid workQueueUid : workQueueUidList) {
				if (!(workQueueUid.isDuplicate() && workQueueUid.isFailed())) {
					allFailedDuplicate = false;
				}
			}

			if (allFailedDuplicate) {
				LOG.equals("All entries are duplicates");

				postProcessingFailure(WorkQueueProcessorFailureType.Fatal);
				return;
			}
			postProcessingFailure(WorkQueueProcessorFailureType.NonFatal);
		}

	}

	private void checkIfStudyIsLossy() throws WorkQueueProcessingException {
		LOG.info(String.format("check If Study IsLossy in the %s workQueue",
				workQueueItem.getGuid()));

		if (StudyStatusEnum.OnlineLossy.getLookup().equals(
				storageLocation.getStudyStatusEnum())
				&& storageLocation.isLatestArchiveLossless()) {
			// This should fail the entry and force user to restore the study
			throw new WorkQueueProcessingException(null,
					"Unexpected study state: the study is lossy compressed.");
		}
	}

	@Override
	boolean canStart() throws WorkQueueProcessingException {
		LOG.info(String.format("check can start to process the %s workQueue",
				workQueueItem.getGuid()));

		if (QueueStudyStateEnum.ProcessingScheduled.getLookup().equals(
				storageLocation.getQueueStudyStateEnum())) {
			LockStudyParameters lockStudyParameters = new LockStudyParameters();
			lockStudyParameters.setStudyStorageGUID(storageLocation.getGuid());
			lockStudyParameters.setQueueStudyStateEnum(storageLocation
					.getQueueStudyStateEnum());
			studyStorageManager.lockStudy(lockStudyParameters);
			if (!lockStudyParameters.getSuccessful()) {
				postponeItem(String.format(
						"Study is being locked by another processor: %s",
						lockStudyParameters.getFailureReason()));
				return false;
			}
		}

		return true;

	}

	// 1.
	private int processUidList() {
		LOG.info(String.format("To process WorkQueueUID List of %s workQueue",
				workQueueItem.getGuid()));
		Study study = storageLocation.loadStudyDTOFromJSON();

		int successfulProcessCount = 0;

		for (WorkQueueUid workQueueUid : workQueueUidList) {
			if (workQueueUid.isFailed())
				continue;

			if (processWorkQueueUid(workQueueUid, study))
				successfulProcessCount++;
		}

		return successfulProcessCount;
	}

	// 2.
	protected boolean processWorkQueueUid(WorkQueueUid workQueueUid,
			Study studyDTO) {

		LOG.info(String.format("To process the %s WorkQueueUID",
				workQueueUid.getGuid()));

		onProcessUidBegin(workQueueUid);

		String path = null;

		try {
			if (workQueueUid.isDuplicate()
					&& workQueueUid.getExtension() != null) {

				LOG.info(String.format(
						"To process the Duplicate %s WorkQueueUID",
						workQueueUid.getGuid()));

				path = getDuplicateUidPath();

				DicomObject dcmObj = null;
				DicomInputStream in = null;
				try {
					File dicomFile = new File(path);
					in = new DicomInputStream(dicomFile);
					in.setHandler(new StopTagInputHandler(Tag.PixelData));

					dcmObj = in.readDicomObject();
				} catch (IOException e) {
					e.printStackTrace();
					System.out.print('F');
					throw new WorkQueueProcessingException(e, "");
				} finally {
					CloseUtils.safeClose(in);
				}

				InstancePreProcessingResult result = preProcessFile(
						workQueueUid, dcmObj);

				if (false == dcmObj.getString(Tag.StudyInstanceUID).equals(
						storageLocation.getStudyInstanceUid())
						|| result.isDiscardImage()) {
					removeWorkQueueUid(workQueueUid, null);
				} else {
					processDuplicate(workQueueUid, dcmObj);
				}
			} else {

				LOG.info(String.format(
						"To process the no Duplicate %s WorkQueueUID",
						workQueueUid.getGuid()));

				path = storageLocation.getSopInstancePath(
						workQueueUid.getSeriesInstanceUid(),
						workQueueUid.getSopInstanceUid());
				DicomObject dcmObj = null;
				DicomInputStream in = null;
				try {
					in = new DicomInputStream(new File(path));
					in.setHandler(new StopTagInputHandler(Tag.PixelData));

					dcmObj = in.readDicomObject();
				} catch (IOException e) {
					LOG.error(e);
					throw new WorkQueueProcessingException(e, "");
				} finally {
					CloseUtils.safeClose(in);
				}

				InstancePreProcessingResult result = preProcessFile(
						workQueueUid, dcmObj);

				if (false == dcmObj.getString(Tag.StudyInstanceUID).equals(
						storageLocation.getStudyInstanceUid())
						|| result.isDiscardImage()) {
					removeWorkQueueUid(workQueueUid, path);
				} else {
					processFile(workQueueUid, dcmObj, path, studyDTO,
							result.isAutoReconciled() ? false : true);
				}

			}

			return true;
		} catch (Exception e) {
			LOG.error(String
					.format("Unexpected %s exception when processing file: %s SOP Instance: %s",
							e.getClass(), path,
							workQueueUid.getSopInstanceUid()));
			workQueueItem.setFailureDescription(e.getMessage());

			return false;

		} finally {
			onProcessUidEnd(workQueueUid);
		}
	}

	private void onProcessUidEnd(WorkQueueUid workQueueUid) {
		if (workQueueUid.isDuplicate()) {
			String dupPath = getDuplicateUidPath();
			// Delete the container if it's empty
			File f = new File(dupPath);

			if (f.exists()) {
				f.delete();
			}
		}
	}

	private void processFile(WorkQueueUid workQueueUid, DicomObject dcmObj,
			String destFileToSave, Study study, boolean compare) {
		LOG.info(String.format(
				"To process the dicom file in the %s WorkQueueUID",
				workQueueUid.getGuid()));

		SopInstanceProcessor processor = new SopInstanceProcessor(
				studyProcessorContext);

		ProcessingStatus result = processor.processFile(
				workQueueUid.getGroupID(), dcmObj, destFileToSave, study,
				compare, true, workQueueUid, null);

		if (result == ProcessingStatus.Reconciled) {
			// file has been saved by SopInstanceProcessor in another place for
			// reconcilation
			// Note: SopInstanceProcessor has removed the WorkQueueUid so we
			// only need to delete the file here.
			FileUtils.deleteQuietly(new File(destFileToSave));
		}

	}

	private void processDuplicate(WorkQueueUid workQueueUid, DicomObject dcmObj) {
		// TODO Auto-generated method stub

	}

	private void removeWorkQueueUid(WorkQueueUid workQueueUid,
			String fileToDelete) throws WorkQueueProcessingException {
		ServerCommandProcessor processor = new ServerCommandProcessor(
				"Remove Work Queue Uid");
		processor.addCommand(new DeleteWorkQueueUidCommand(workQueueUid,
				workQueueManager));
		if (fileToDelete != null) {
			processor.addCommand(new FileDeleteCommand(fileToDelete, true));

		}

		if (!processor.execute()) {
			String error = String.format(
					"Unable to delete Work Queue Uid %s: %s",
					workQueueUid.getGuid(), processor.getFailureReason());
			LOG.error(error);
			throw new WorkQueueProcessingException(null,
					processor.getFailureReason());
		}
	}

	private void onProcessUidBegin(WorkQueueUid workQueueUid) {
		// TODO Auto-generated method stub

	}

	private InstancePreProcessingResult preProcessFile(WorkQueueUid uid,
			DicomObject dcmObj) {

		InstancePreProcessingResult result = new InstancePreProcessingResult();

		return result;
	}

	//
	// //3.
	// protected virtual void ProcessFile(WorkQueueUid queueUid, DicomFile file,
	// StudyXml stream, bool compare){
	// //4.to call SopInstanceProcessor
	//
	// }

	private String getDuplicateUidPath() {
		String dupPath = null;// getReconcileSopInstancePath()

		return dupPath;
	}

}
