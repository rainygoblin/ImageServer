package com.iworkstation.imageserver.command;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dcm4che2.data.DicomObject;

import com.iworkstation.imageserver.domain.StudyStorageLocation;
import com.iworkstation.imageserver.parameter.InsertInstanceParameters;
import com.iworkstation.imageserver.service.IStudyManager;

public class InsertInstanceCommand extends ServerCommand {
	private static final Log LOG = LogFactory
			.getLog(InsertInstanceCommand.class);

	private DicomObject dcmObj;
	private StudyStorageLocation studyStorageLocation;
	private IStudyManager studyManager;

	public InsertInstanceCommand(DicomObject dcmObj,
			StudyStorageLocation studyStorageLocation,
			IStudyManager studyManager) {
		super("Insert Instance into Database", true);
		this.dcmObj = dcmObj;
		this.studyStorageLocation = studyStorageLocation;
		this.studyManager = studyManager;
	}

	@Override
	public void onExecute(ServerCommandProcessor theProcessor)
			throws CommandException {
		LOG.info("Insert Instance into Database");
		// Setup the insert parameters
		InsertInstanceParameters parms = new InsertInstanceParameters();
		parms.setServerPartitionGUID(studyStorageLocation
				.getServerPartitionGUID());
		parms.setStudyStorageGUID(studyStorageLocation.getGuid());
		parms.initial(dcmObj);
		studyManager.insertInstance(parms);

		// Get the Insert Instance broker and do the insert

		// If the Request Attributes Sequence is in the dataset, do an insert.
		// if (dcmObj.contains(Tag.RequestAttributesSequence))
		// {
		// DicomAttributeSQ attribute =
		// dcmObj.get.DataSet[DicomTags.RequestAttributesSequence] as
		// DicomAttributeSQ;
		// if (!attribute.IsEmpty)
		// {
		// foreach (DicomSequenceItem sequenceItem in (DicomSequenceItem[])
		// attribute.Values)
		// {
		// RequestAttributesInsertParameters requestParms = new
		// RequestAttributesInsertParameters();
		// sequenceItem.LoadDicomFields(requestParms);
		// requestParms.SeriesKey = _insertKey.SeriesKey;
		//
		// IInsertRequestAttributes insertRequest =
		// updateContext.GetBroker<IInsertRequestAttributes>();
		// insertRequest.Execute(requestParms);
		// }
		// }
		// }
	}

	@Override
	public void onUndo() throws CommandException {
		// TODO Auto-generated method stub

	}

}
