package com.iworkstation.imageserver.enumeration;


public enum DuplicateSopPolicyEnum {
	SendSuccess(
			"SendSuccess",
			"Send a DICOM C-STORE-RSP success status when receiving a duplicate, but ignore the file."), RejectDuplicates(
			"RejectDuplicates",
			"Send a DICOM C-STORE-RSP reject status when receiving a duplicate."), CompareDuplicates(
			"CompareDuplicates",
			"Process duplicate objects received and compare them to originals flagging any differences as a failure.");

	private String lookup;
	private String description;

	private DuplicateSopPolicyEnum(String lookup, String description) {
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
