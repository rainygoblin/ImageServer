package com.iworkstation.imageserver.command;

import org.dcm4che2.data.DicomObject;

import com.iworkstation.imageserver.domain.StudyStorageLocation;
import com.iworkstation.imageserver.service.IStudyStorageManager;

public class UpdateStudyStatusCommand extends ServerCommand {
	private DicomObject dcmObj;
	private StudyStorageLocation storageLocation;
	private IStudyStorageManager studyStorageManager;

	public UpdateStudyStatusCommand(DicomObject dcmObj,
			StudyStorageLocation studyStorageLocation,
			IStudyStorageManager studyStorageManager) {
		super("Update StudyStorage and FilesystemStudyStorage", false);
		this.storageLocation = studyStorageLocation;
		this.studyStorageManager = studyStorageManager;
	}

	@Override
	public void onExecute(ServerCommandProcessor theProcessor)
			throws CommandException {
		// // Check if the File is the same syntax as the
		// String fileSyntax = dcmObj.getString(Tag.TransferSyntaxUID);
		// TransferSyntax dbSyntax =
		// TransferSyntax.GetTransferSyntax(_location.TransferSyntaxUid);
		//
		// // Check if the syntaxes match the location
		// if ((!fileSyntax.Encapsulated && !dbSyntax.Encapsulated)
		// || (fileSyntax.LosslessCompressed && dbSyntax.LosslessCompressed)
		// || (fileSyntax.LossyCompressed && dbSyntax.LossyCompressed))
		// {
		// // no changes necessary, just return;
		// return;
		// }
		//
		// // Select the Server Transfer Syntax
		// ServerTransferSyntaxSelectCriteria syntaxCriteria = new
		// ServerTransferSyntaxSelectCriteria();
		// IServerTransferSyntaxEntityBroker syntaxBroker =
		// updateContext.GetBroker<IServerTransferSyntaxEntityBroker>();
		// syntaxCriteria.Uid.EqualTo(fileSyntax.UidString);
		//
		// ServerTransferSyntax serverSyntax =
		// syntaxBroker.FindOne(syntaxCriteria);
		// if (serverSyntax == null)
		// {
		// Platform.Log(LogLevel.Error,
		// "Unable to load ServerTransferSyntax for {0}.  Unable to update study status.",
		// fileSyntax.Name);
		// return;
		// }
		//
		// // Get the FilesystemStudyStorage update broker ready
		// IFilesystemStudyStorageEntityBroker
		// filesystemStudyStorageEntityBroker =
		// updateContext.GetBroker<IFilesystemStudyStorageEntityBroker>();
		// FilesystemStudyStorageUpdateColumns filesystemStorageUpdate = new
		// FilesystemStudyStorageUpdateColumns();
		// FilesystemStudyStorageSelectCriteria filesystemStorageCritiera = new
		// FilesystemStudyStorageSelectCriteria();
		//
		// filesystemStorageUpdate.ServerTransferSyntaxKey = serverSyntax.Key;
		// filesystemStorageCritiera.StudyStorageKey.EqualTo(_location.Key);
		//
		// // Get the StudyStorage update broker ready
		// IStudyStorageEntityBroker studyStorageBroker =
		// updateContext.GetBroker<IStudyStorageEntityBroker>();
		// StudyStorageUpdateColumns studyStorageUpdate = new
		// StudyStorageUpdateColumns();
		// StudyStatusEnum statusEnum = _location.StudyStatusEnum;
		// if (fileSyntax.LossyCompressed)
		// studyStorageUpdate.StudyStatusEnum = statusEnum =
		// StudyStatusEnum.OnlineLossy;
		// else if (fileSyntax.LosslessCompressed)
		// studyStorageUpdate.StudyStatusEnum = statusEnum =
		// StudyStatusEnum.OnlineLossless;
		//
		// studyStorageUpdate.LastAccessedTime = Platform.Time;
		//
		// if
		// (!filesystemStudyStorageEntityBroker.Update(filesystemStorageCritiera,
		// filesystemStorageUpdate))
		// {
		// Platform.Log(LogLevel.Error,
		// "Unable to update FilesystemQueue row: Study {0}, Server Entity {1}",
		// _location.StudyInstanceUid, _location.ServerPartitionKey);
		//
		// }
		// else if (!studyStorageBroker.Update(_location.GetKey(),
		// studyStorageUpdate))
		// {
		// Platform.Log(LogLevel.Error,
		// "Unable to update StudyStorage row: Study {0}, Server Entity {1}",
		// _location.StudyInstanceUid, _location.ServerPartitionKey);
		// }
		// else
		// {
		// // Update the location, so the next time we come in here, we don't
		// try and update the database
		// // for another sop in the study.
		// studyStorageLocation.StudyStatusEnum = statusEnum;
		// studyStorageLocation.TransferSyntaxUid = fileSyntax.UidString;
		// studyStorageLocation.ServerTransferSyntaxKey = serverSyntax.Key;
		// }
	}

	@Override
	public void onUndo() throws CommandException {
	}

}
