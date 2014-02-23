package com.iworkstation.imageserver.workqueue;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iworkstation.imageserver.domain.StudyStorageLocation;
import com.iworkstation.imageserver.domain.WorkQueue;
import com.iworkstation.imageserver.domain.WorkQueueTypeProperties;
import com.iworkstation.imageserver.domain.WorkQueueUid;
import com.iworkstation.imageserver.enumeration.QueueStudyStateEnum;
import com.iworkstation.imageserver.enumeration.WorkQueueStatusEnum;
import com.iworkstation.imageserver.enums.WorkQueueProcessorDatabaseUpdate;
import com.iworkstation.imageserver.enums.WorkQueueProcessorFailureType;
import com.iworkstation.imageserver.enums.WorkQueueProcessorStatus;
import com.iworkstation.imageserver.parameter.PostponeWorkQueueParameters;
import com.iworkstation.imageserver.parameter.UpdateWorkQueueParameters;
import com.iworkstation.imageserver.service.IFilesystemManager;
import com.iworkstation.imageserver.service.IStudyManager;
import com.iworkstation.imageserver.service.IStudyStorageManager;
import com.iworkstation.imageserver.service.IWorkQueueManager;

public abstract class AbstractWorkQueueItemProcessor {
	private static final Log LOG = LogFactory
			.getLog(AbstractWorkQueueItemProcessor.class);
	private final int inactiveWorkQueueMinTime = 2 * 24 * 60 * 60 * 1000;

	protected String processorId;
	protected WorkQueueTypeProperties workQueueProperties;
	protected StudyStorageLocation storageLocation;
	protected WorkQueue workQueueItem;
	protected List<WorkQueueUid> workQueueUidList;
	protected boolean isCompleted;

	protected IStudyManager studyManager;
	protected IFilesystemManager filesystemManager;
	protected IStudyStorageManager studyStorageManager;
	protected IWorkQueueManager workQueueManager;

	public void setStudyStorageManager(IStudyStorageManager studyStorageManager) {
		this.studyStorageManager = studyStorageManager;
	}

	public void setStudyManager(IStudyManager studyManager) {
		this.studyManager = studyManager;
	}

	public void setFilesystemManager(IFilesystemManager filesystemManager) {
		this.filesystemManager = filesystemManager;
	}

	public void setWorkQueueManager(IWorkQueueManager workQueueManager) {
		this.workQueueManager = workQueueManager;
	}

	public void setProcessorId(String processorId) {
		this.processorId = processorId;
	}

	void process(WorkQueue item) throws WorkQueueProcessingException {
		workQueueItem = item;
		storageLocation = filesystemManager
				.getWritableStudyStorageLocation(item.getStudyStorageGUID());

		workQueueProperties = workQueueManager
				.getWorkQueueProperties(workQueueItem.getWorkQueueTypeEnum());
		onProcess();
	}

	abstract void onProcess() throws WorkQueueProcessingException;

	abstract boolean canStart() throws WorkQueueProcessingException;

	public void loadWorkQueueUids() {
		workQueueUidList = workQueueManager
				.findWorkQueueUidByWorkQueueGUID(workQueueItem.getGuid());
	}

	protected void postProcessing(WorkQueueProcessorStatus status,
			WorkQueueProcessorDatabaseUpdate resetQueueStudyState) {
		Date now = new Date();
		isCompleted = status == WorkQueueProcessorStatus.Complete
				|| (status == WorkQueueProcessorStatus.Idle && workQueueItem
						.getExpirationTime().before(now));
		if (isCompleted) {
			LOG.info(String.format("%s has completed (GUID=%s)",
					workQueueItem.getWorkQueueTypeEnum(),
					workQueueItem.getGuid()));
			UpdateWorkQueueParameters parms = new UpdateWorkQueueParameters();
			parms.setWorkQueueGUID(workQueueItem.getGuid());
			parms.setStudyStorageGUID(workQueueItem.getStudyStorageGUID());
			parms.setProcessorID(workQueueItem.getProcessorID());

			if (workQueueItem.getFailureDescription() != null)
				parms.setFailureDescription(workQueueItem
						.getFailureDescription());

			Date scheduledTime = new Date(now.getTime()
					+ workQueueProperties.getProcessDelaySeconds() * 1000);

			if (scheduledTime.after(workQueueItem.getExpirationTime()))
				scheduledTime = workQueueItem.getExpirationTime();

			if (status == WorkQueueProcessorStatus.CompleteDelayDelete) {
				parms.setWorkQueueStatusEnum(WorkQueueStatusEnum.Idle
						.getLookup());
				parms.setFailureCount(workQueueItem.getFailureCount());
				parms.setFailureDescription("");
				parms.setScheduledTime(new Date(parms.getExpirationTime()
						.getTime()
						+ workQueueProperties.getDeleteDelaySeconds()));
				if (resetQueueStudyState == WorkQueueProcessorDatabaseUpdate.ResetQueueState)
					parms.setQueueStudyStateEnum(QueueStudyStateEnum.Idle
							.getLookup());
			} else if (status == WorkQueueProcessorStatus.Complete
					|| (status == WorkQueueProcessorStatus.Idle && workQueueItem
							.getExpirationTime().before(now))) {
				parms.setWorkQueueStatusEnum(WorkQueueStatusEnum.Completed
						.getLookup());
				parms.setFailureCount(workQueueItem.getFailureCount());
				parms.setScheduledTime(scheduledTime);
				if (resetQueueStudyState == WorkQueueProcessorDatabaseUpdate.ResetQueueState)
					parms.setQueueStudyStateEnum(QueueStudyStateEnum.Idle
							.getLookup());

				// keep the same
				parms.setExpirationTime(workQueueItem.getExpirationTime());
				isCompleted = true;
			} else if (status == WorkQueueProcessorStatus.Idle
					|| status == WorkQueueProcessorStatus.IdleNoDelete) {
				scheduledTime = new Date(now.getTime()
						+ workQueueProperties.getDeleteDelaySeconds() * 1000);
				if (scheduledTime.after(workQueueItem.getExpirationTime()))
					scheduledTime = workQueueItem.getExpirationTime();

				parms.setWorkQueueStatusEnum(WorkQueueStatusEnum.Completed
						.getLookup());
				parms.setScheduledTime(scheduledTime);
				// keep the same
				parms.setExpirationTime(workQueueItem.getExpirationTime());
				parms.setFailureCount(workQueueItem.getFailureCount());

			} else {
				parms.setWorkQueueStatusEnum(WorkQueueStatusEnum.Pending
						.getLookup());

				parms.setExpirationTime(new Date(scheduledTime.getTime()
						+ workQueueProperties.getExpireDelaySeconds() * 1000));
				parms.setScheduledTime(scheduledTime);
				parms.setFailureCount(workQueueItem.getFailureCount());

			}

			workQueueManager.updateWorkQueue(parms);
		}
	}

	protected void postProcessingFailure(
			WorkQueueProcessorFailureType processorFailureType) {

	}

	protected void postponeItem(String reason,
			WorkQueueProcessorFailureType errorType) {
		Date newScheduledTime = new Date(new Date().getTime()
				+ workQueueProperties.getPostponeDelaySeconds() * 1000);
		Date expireTime = new Date(newScheduledTime.getTime() + 2 * 60 * 1000);
		postponeItem(newScheduledTime, expireTime, reason, errorType);
	}

	protected void postponeItem(String reasonText) {
		Date newScheduledTime = new Date(new Date().getTime()
				+ workQueueProperties.getPostponeDelaySeconds() * 1000);
		Date expireTime = new Date(newScheduledTime.getTime() + 2 * 60 * 1000);
		postponeItem(newScheduledTime, expireTime, reasonText, null);
	}

	protected void postponeItem(Date newScheduledTime, Date expireTime,
			String reason) {
		postponeItem(newScheduledTime, expireTime, reason, null);
	}

	protected void postponeItem(Date newScheduledTime, Date expireTime,
			String postponeReason, WorkQueueProcessorFailureType errorType) {
		String stuckReason = null;
		@SuppressWarnings("deprecation")
		boolean updatedBefore = workQueueItem.getLastUpdatedTime().after(
				new Date(-1899, 0, 1));

		if (updatedBefore && appearsStuck(stuckReason)) {
			String reason = stuckReason == null ? String.format(
					"Aborted because %s", postponeReason) : String.format(
					"Aborted because %s. %s", postponeReason, stuckReason);
			abortQueueItem(reason, true);
		} else {
			internalPostponeWorkQueue(newScheduledTime, expireTime,
					postponeReason, !updatedBefore, errorType);
		}

	}

	@SuppressWarnings("deprecation")
	private boolean appearsStuck(String reason) {
		List<WorkQueue> allItems = workQueueManager
				.findWorkQueueByStudyStorage(workQueueItem
						.getStudyStorageGUID());
		boolean updatedBefore = workQueueItem.getLastUpdatedTime().after(
				new Date(-1899, 0, 1));
		reason = null;

		if (allItems.size() == 1 /* this is the only entry */) {
			if (updatedBefore) {
				if (workQueueItem.getLastUpdatedTime().before(
						new Date(new Date().getTime()
								- inactiveWorkQueueMinTime))) {
					reason = String.format(
							"This entry has not been updated since %s",
							workQueueItem.getLastUpdatedTime());
					return true;
				}
			}
		} else {
			for (WorkQueue anotherItem : allItems) {
				if (anotherItem.getGuid().equals(workQueueItem.getGuid()))
					continue;

				if (!isActiveWorkQueue(anotherItem)) {
					reason = "Another work queue entry for the same study appears stuck.";
					return true;
				}
			}

			// none of the other entries are active. Either they are all stuck
			// or failed.
			// This entry is considered stuck if it hasn't been updated for long
			// time.
			if (updatedBefore
					&& workQueueItem.getLastUpdatedTime().before(
							new Date(new Date().getTime()
									- inactiveWorkQueueMinTime))) {
				reason = String.format(
						"This entry has not been updated for since {0}",
						workQueueItem.getLastUpdatedTime());
				return true;
			}
		}

		return false;
	}

	private boolean isActiveWorkQueue(WorkQueue item) {
		// The following code assumes InactiveWorkQueueMinTime is set
		// appropirately

		if (item.getWorkQueueStatusEnum() == WorkQueueStatusEnum.Failed)
			return false;

		if (item.getWorkQueueStatusEnum() == WorkQueueStatusEnum.Pending
				|| item.getWorkQueueStatusEnum() == WorkQueueStatusEnum.Idle) {
			// Assuming that if the entry is picked up and rescheduled recently
			// (the ScheduledTime would have been updated),
			// the item is inactive if its ScheduledTime still indicated it was
			// scheduled long time ago.
			// Note: this logic still works if the entry has never been
			// processed (new). It will be
			// considered as "inactive" if it was scheduled long time ago and
			// had never been updated.

			@SuppressWarnings("deprecation")
			Date time = item.getLastUpdatedTime() != new Date(-1899, 0, 1) ? item
					.getLastUpdatedTime() : item.getScheduledTime();
			if (time.before(new Date(new Date().getTime()
					- inactiveWorkQueueMinTime)))
				return false;
		} else if (item.getWorkQueueStatusEnum() == WorkQueueStatusEnum.InProgress) {
			if (item.getProcessorID() != null) {
				// This is a special case, the item is not assigned but is set
				// to InProgress.
				// It's definitely stuck cause it won't be picked up by any
				// servers.
				return false;
			}
			// TODO: Need more elaborate logic to detect if it's stuck when the
			// status is InProgress.
			// Ideally, we can assume item is stuck if it has not been updated
			// for a while.
			// Howerver, some operations were designed to process everything in
			// a single run
			// instead of batches.One example is the StudyProcess, research
			// studies may take days to process
			// and the item stays in "InProgress" for the entire period without
			// any update
			// (eventhough the WorkQueueUid records are removed)
			// For now, we assume it's stucked if it is not updated for long
			// time.
			if (item.getScheduledTime().before(
					new Date(new Date().getTime() - inactiveWorkQueueMinTime)))
				return false;
		}

		return true;
	}

	private void internalPostponeWorkQueue(Date newScheduledTime,
			Date expireTime, String reasonText, boolean updateWorkQueueEntry,
			WorkQueueProcessorFailureType errorType) {
		if (errorType != null) {
			LOG.info(String
					.format("Postpone %s entry until %s: %s. [GUID=%s.] (This transaction is treated as a failure)",
							workQueueItem.getWorkQueueTypeEnum(),
							newScheduledTime, reasonText,
							workQueueItem.getGuid()));
			workQueueItem.setFailureDescription(reasonText);
			postProcessingFailure(WorkQueueProcessorFailureType.NonFatal);
			return;
		}

		LOG.info(String.format("Postpone %s entry until %s: %s. [GUID=%s]",
				workQueueItem.getWorkQueueTypeEnum(), newScheduledTime,
				reasonText, workQueueItem.getGuid()));

		PostponeWorkQueueParameters parameters = new PostponeWorkQueueParameters();
		parameters.setWorkQueueGUID(workQueueItem.getGuid());
		parameters.setReason(reasonText);
		parameters.setScheduledTime(newScheduledTime);
		parameters.setExpirationTime(expireTime);
		parameters.setUpdateWorkQueue(updateWorkQueueEntry);
		workQueueManager.postponeWorkQueue(parameters);

	}

	protected void abortQueueItem(String failureDescription,
			boolean generateAlert) {
		int retryCount = 0;
		while (true) {
			int count = retryCount;
			if (count > 0)
				LOG.error(String
						.format("Abort %s WorkQueue entry (%s). Retry # %s. Reason: %s",
								workQueueItem.getWorkQueueTypeEnum(),
								workQueueItem.getGuid(), count,
								failureDescription));
			else
				LOG.error(String.format(
						"Abort %s WorkQueue entry (%s). Reason: %s",
						workQueueItem.getWorkQueueTypeEnum(),
						workQueueItem.getGuid(), failureDescription));
			UpdateWorkQueueParameters parms = new UpdateWorkQueueParameters();
			parms.setProcessorID(processorId);
			parms.setWorkQueueGUID(workQueueItem.getGuid());
			parms.setStudyStorageGUID(workQueueItem.getStudyStorageGUID());
			parms.setFailureCount(workQueueItem.getFailureCount() + 1);
			parms.setFailureDescription(failureDescription);
			parms.setWorkQueueStatusEnum(WorkQueueStatusEnum.Failed.getLookup());
			parms.setScheduledTime(new Date());
			parms.setExpirationTime(new Date(new Date().getTime() + 24 * 60
					* 60 * 1000));
			workQueueManager.updateWorkQueue(parms);

			break; // done

		}

	}

}
