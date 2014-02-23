package com.iworkstation.imageserver.service.exception;

public class StudyNotFoundException extends Exception {

	private String studyInstanceUid = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = -7163378961331275071L;

	public StudyNotFoundException(String studyInstanceUid) {
		super("Study not found: " + studyInstanceUid);
		this.studyInstanceUid = studyInstanceUid;
	}

	public String getStudyInstanceUid() {
		return studyInstanceUid;
	}

}
