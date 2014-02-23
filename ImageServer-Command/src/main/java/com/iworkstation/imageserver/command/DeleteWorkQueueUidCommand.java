package com.iworkstation.imageserver.command;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iworkstation.imageserver.domain.WorkQueueUid;
import com.iworkstation.imageserver.service.IWorkQueueManager;

public class DeleteWorkQueueUidCommand extends ServerCommand {
	private static final Log LOG = LogFactory
			.getLog(DeleteWorkQueueUidCommand.class);

	private final WorkQueueUid uid;
	private IWorkQueueManager workQueueManager;

	public DeleteWorkQueueUidCommand(WorkQueueUid uid,
			IWorkQueueManager workQueueManager) {
		super("Delete WorkQueue Uid Entry", true);
		this.uid = uid;
		this.workQueueManager = workQueueManager;
	}

	@Override
	public void onExecute(ServerCommandProcessor theProcessor) {
		LOG.info(String.format("To delete the %s WorkQueueUid", uid.getGuid()));
		workQueueManager.deleteWorkQueueUid(uid);
	}

	@Override
	public void onUndo() {

	}

}
