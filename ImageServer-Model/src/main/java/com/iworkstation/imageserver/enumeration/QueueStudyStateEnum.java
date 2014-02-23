package com.iworkstation.imageserver.enumeration;


public enum QueueStudyStateEnum {
	Idle("Idle", "The study currently does not have any queue entries."), DeleteScheduled(
			"DeleteScheduled", "The study is scheduled for deletion"), WebDeleteScheduled(
			"WebDeleteScheduled", "The study is scheduled for deletion."), EditScheduled(
			"EditScheduled", "The study is scheduled for editing"), ProcessingScheduled(
			"ProcessingScheduled", "The study is being processed"), PurgeScheduled(
			"PurgeScheduled", "The study has been scheduled for purging"), ReconcileScheduled(
			"ReconcileScheduled",
			"The study has been scheduled for reconciliation"), ReconcileRequired(
			"ReconcileRequired", "The study needs to be reconciled"), CleanupScheduled(
			"CleanupScheduled",
			"A WorkQueue entry for the study needs to be cleaned up"), CompressScheduled(
			"CompressScheduled", "The study is scheduled for compression"), MigrationScheduled(
			"MigrationScheduled",
			"The study is scheduled for migration to a new tier of storage"), ReprocessScheduled(
			"ReprocessScheduled", "The study is scheduled for reprocessing"), RestoreScheduled(
			"RestoreScheduled", "The study is scheduled for restore"), ArchiveScheduled(
			"ArchiveScheduled", "The study is scheduled for archiving");

	private String lookup;
	private String description;

	private QueueStudyStateEnum(String lookup, String description) {
		this.lookup = lookup;
		this.description = description;
	}

	public String getLookup() {
		return lookup;
	}

	public String getDescription() {
		return description;
	}
}