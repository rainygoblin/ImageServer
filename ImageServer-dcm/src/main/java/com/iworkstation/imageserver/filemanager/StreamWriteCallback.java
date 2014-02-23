package com.iworkstation.imageserver.filemanager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;


public abstract interface StreamWriteCallback {

	public static final StreamWriteCallback DEFAULT_STREAM_WRITE_CALLBACK = new StreamWriteCallback() {
		public void doWrite(InputStream is, OutputStream os) {
			try {
				IOUtils.copy(is, os);
			} catch (IOException ioe) {
				throw new RuntimeException("Write error: ", ioe);
			} finally {
				IOUtils.closeQuietly(os);
				IOUtils.closeQuietly(is);
			}
		}
	};

	public abstract void doWrite(InputStream paramInputStream,
			OutputStream paramOutputStream);
}
