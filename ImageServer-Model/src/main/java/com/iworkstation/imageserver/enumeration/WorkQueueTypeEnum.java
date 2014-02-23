package com.iworkstation.imageserver.enumeration;


public enum WorkQueueTypeEnum {
	StudyProcess("StudyProcess", "Processing of a new incoming study."), AutoRoute(
			"AutoRoute", "DICOM Auto-route request."), DeleteStudy(
			"DeleteStudy", "Automatic deletion of a Study."), WebDeleteStudy(
			"WebDeleteStudy", "Manual study delete via the Web UI."), WebMoveStudy(
			"WebMoveStudy", "Manual DICOM move of a study via the Web UI."), WebEditStudy(
			"WebEditStudy", "Manual study edit via the Web UI."), CleanupStudy(
			"CleanupStudy",
			"Cleanup all unprocessed or failed instances within a study."), CompressStudy(
			"CompressStudy", "Compress a study."), MigrateStudy("MigrateStudy",
			"Migrate studies between tiers."), PurgeStudy("PurgeStudy",
			"Purge archived study and place offline."), ReprocessStudy(
			"ReprocessStudy", "Reprocess an entire study."), ReconcileStudy(
			"ReconcileStudy", "Reconcile images."), ReconcileCleanup(
			"ReconcileCleanup", "Cleanup a failed Reconcile Study entry"), ReconcilePostProcess(
			"ReconcilePostProcess", "Process reconciled images."), ProcessDuplicate(
			"ProcessDuplicate", "Process duplicate."), CleanupDuplicate(
			"CleanupDuplicate", "Cleanup failed ProcessDuplicate entry.");

	private String lookup;
	private String description;

	private WorkQueueTypeEnum(String lookup, String description) {
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
