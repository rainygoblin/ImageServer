package com.iworkstation.imageserver.processor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.Tag;

import com.iworkstation.imageserver.command.BaseImageLevelUpdateCommand;
import com.iworkstation.imageserver.command.DeleteWorkQueueUidCommand;
import com.iworkstation.imageserver.command.FileDeleteCommand;
import com.iworkstation.imageserver.command.InsertInstanceCommand;
import com.iworkstation.imageserver.command.InsertStudyJSONCommand;
import com.iworkstation.imageserver.command.ServerCommandProcessor;
import com.iworkstation.imageserver.command.UpdateStudyStatusCommand;
import com.iworkstation.imageserver.domain.Study;
import com.iworkstation.imageserver.domain.StudyStorageLocation;
import com.iworkstation.imageserver.domain.WorkQueueUid;

public class SopInstanceProcessor {
	private static final Log LOG = LogFactory
			.getLog(SopInstanceProcessor.class);

	private StudyProcessorContext studyProcessorContext;

	public SopInstanceProcessor(StudyProcessorContext studyProcessorContext) {
		this.studyProcessorContext = studyProcessorContext;
	}

	public ProcessingStatus processFile(String group, DicomObject dcmObj,
			String destFileToSave, Study study, boolean compare, boolean retry,
			WorkQueueUid uid, String deleteFile) {
		LOG.info(String.format("To process the SOP of  %s ", destFileToSave));

		ProcessingStatus result = null;
		try {
			ServerCommandProcessor processor = new ServerCommandProcessor(
					"Process File");

			// if (EnforceNameRules)
			// {
			// _patientNameRules.Apply(file);
			// }

			if (compare
					&& shouldReconcile(
							studyProcessorContext.getStorageLocation(), dcmObj)) {
				scheduleReconcile(processor,
						studyProcessorContext.getStorageLocation(), dcmObj, uid);
				result = ProcessingStatus.Reconciled;
			} else {
				insertInstance(dcmObj, study, uid, deleteFile);
				result = ProcessingStatus.Success;
			}

		} catch (Exception e) {
			// If its a duplicate, ignore the exception, and just throw it
			// if (deleteFile != null && (e is InstanceAlreadyExistsException
			// || e.InnerException != null && e.InnerException is
			// InstanceAlreadyExistsException))
			// throw;
			//
			// if (uid != null)
			// FailUid(uid, retry);
			// throw;
		}
		return result;
	}

	private void insertInstance(DicomObject dcmObj, Study study,
			WorkQueueUid uid, String deleteFile)
			throws SopInstanceProcessorException {
		LOG.info(String.format("To insert the SOP into database."));

		ServerCommandProcessor processor = new ServerCommandProcessor(
				"Processing WorkQueue DICOM file");

		InsertInstanceCommand insertInstanceCommand = null;
		InsertStudyJSONCommand insertStudyJSONCommand = null;

		String patientsName = dcmObj.getString(Tag.PatientName);
		String modality = dcmObj.getString(Tag.Modality);

		if (studyProcessorContext.getUpdateCommands() != null
				&& studyProcessorContext.getUpdateCommands().size() > 0) {
			for (BaseImageLevelUpdateCommand command : studyProcessorContext
					.getUpdateCommands()) {
				command.setDcmObj(dcmObj);
				processor.addCommand(command);
			}
		}

		try {
			// Create a context for applying actions from the rules engine
			// ServerActionContext context =
			// new ServerActionContext(file,
			// _context.StorageLocation.FilesystemKey, _context.Partition,
			// _context.StorageLocation.Key);
			// context.CommandProcessor = processor;
			//
			// _context.SopCompressionRulesEngine.Execute(context);
			String seriesUid = dcmObj.getString(Tag.SeriesInstanceUID);
			String sopUid = dcmObj.getString(Tag.SOPInstanceUID);
			String finalDest = studyProcessorContext.getStorageLocation()
					.getSopInstancePath(seriesUid, sopUid);

			// Update the StudyStream object
			insertStudyJSONCommand = new InsertStudyJSONCommand(dcmObj, study,
					studyProcessorContext.getStorageLocation());
			processor.addCommand(insertStudyJSONCommand);

			// Have the rules applied during the command processor, and add the
			// objects.
			// processor.AddCommand(new
			// ApplySopRulesCommand(context,_context.SopProcessedRulesEngine));

			// If specified, delete the file
			if (deleteFile != null)
				processor.addCommand(new FileDeleteCommand(deleteFile, true));

			// Insert into the database, but only if its not a duplicate so the
			// counts don't get off
			insertInstanceCommand = new InsertInstanceCommand(dcmObj,
					studyProcessorContext.getStorageLocation(),
					studyProcessorContext.getStudyManager());
			processor.addCommand(insertInstanceCommand);

			// Do a check if the StudyStatus value should be changed in the
			// StorageLocation. This
			// should only occur if the object has been compressed in the
			// previous steps.
			processor.addCommand(new UpdateStudyStatusCommand(dcmObj,
					studyProcessorContext.getStorageLocation(),
					studyProcessorContext.getStudyStorageManager()));

			if (uid != null)
				processor.addCommand(new DeleteWorkQueueUidCommand(uid,
						studyProcessorContext.getWorkQueueManager()));

			// Do the actual processing
			if (!processor.execute()) {
				LOG.error(String.format(
						"Failure processing command %s for SOP: %s",
						processor.getDescription(),
						dcmObj.getString(Tag.SOPInstanceUID)));
				LOG.error(String.format("File that failed processing: %s",
						deleteFile));
				throw new Exception("Unexpected failure ("
						+ processor.getFailureReason()
						+ ") executing command for SOP: "
						+ dcmObj.getString(Tag.SOPInstanceUID)
						+ processor.getFailureReason());
			}
			LOG.info(String.format(
					"Successfully processed SOP: %s for Patient %s",
					dcmObj.getString(Tag.SOPInstanceUID), patientsName));
		} catch (Exception e) {
			LOG.error(String.format(
					"Unexpected exception when {0}.  Rolling back operation.",
					processor.getDescription()));
			processor.rollback();
			throw new SopInstanceProcessorException(
					"Unexpected exception when processing file.", e);
		}

	}

	private void scheduleReconcile(ServerCommandProcessor processor,
			StudyStorageLocation storageLocation, DicomObject dcmObj,
			WorkQueueUid uid) {
		LOG.info(String.format("To schedule Reconcile the SOP "));

		// ImageReconciler reconciler = new ImageReconciler(context);
		// reconciler.ScheduleReconcile(file,
		// StudyIntegrityReasonEnum.InconsistentData, uid);
	}

	private boolean shouldReconcile(StudyStorageLocation storageLocation,
			DicomObject dcmObj) {
		if (studyProcessorContext.getStudy() == null) {
			// the study doesn't exist in the database
			return false;
		}

		// StudyComparer comparer = new StudyComparer();
		// DifferenceCollection list = comparer.Compare(message,
		// storageLocation.Study,
		// storageLocation.ServerPartition.GetComparisonOptions());
		//
		// if (list != null && list.Count > 0) {
		// LogDifferences(message, list);
		// return true;
		// }
		return false;
	}
}
