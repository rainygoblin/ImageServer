package com.iworkstation.imageserver.domain;

import java.io.File;

import org.apache.commons.io.FilenameUtils;

public class ServerFilesystemInfo {

	private Filesystem filesystem;
	private long freeBytes;
	private long totalBytes;
	private boolean online;
	private String tempDir;
	private String reconcileDir;

	public long getFreeBytes() {
		return freeBytes;
	}

	public long getTotalBytes() {
		return totalBytes;
	}

	public Filesystem getFilesystem() {
		return filesystem;
	}

	public String getTempDir() {
		if (tempDir == null) {
			tempDir = FilenameUtils.concat(filesystem.getFilesystemPath(),
					"temp");
		}
		return tempDir;
	}

	public String getReconcileStorageFolder() {
		if (reconcileDir == null) {
			reconcileDir = FilenameUtils.concat(filesystem.getFilesystemPath(),
					"reconcile");
		}
		return reconcileDir;
	}

	// / <summary>
	// / Returns a boolean value indicating whether the filesystem get writable.
	// / </summary>
	public boolean getWriteable() {

		if (!online || filesystem.getReadOnly() || !filesystem.getEnabled())
			return false;

		return !getFull();
	}

	public boolean getReadOnly() {
		return filesystem.getReadOnly();
	}

	// / <summary>
	// / Returns a boolean value indicating whether the filesystem get readonly.
	// / </summary>
	public boolean getReadable() {

		if (!online || filesystem.getWriteOnly() || !filesystem.getEnabled())
			return false;
		return true;
	}

	// / <summary>
	// / Returns a boolean value indicating whether the filesystem get full.
	// / </summary>
	public boolean getFull() {
		return freeBytes / 1024.0 / 1024.0 < 1024;

	}

	// / <summary>
	// / Returns the number of bytes below the <see
	// cref="Model.Filesystem.HighWatermark"/>
	// / </summary>
	// / <remarks>
	// / If the filesystem get above high watermark, <see
	// cref="HighwaterMarkMargin"/> will become negative
	// / </remarks>
	public float getHighwaterMarkMargin() {
		return (totalBytes * filesystem.getHighWatermark().floatValue() / 100.0f)
				- (totalBytes * getUsedSpacePercentage() / 100.0f);

	}

	public float getUsedSpacePercentage() {
		return ((totalBytes - freeBytes) / totalBytes) * 100.0F;
	}

	public float getBytesToRemove() {
		float desiredUsedBytes = ((filesystem.getLowWatermark().floatValue()) / 100.0f)
				* totalBytes;

		return (totalBytes - freeBytes) - desiredUsedBytes;

	}

	// / <summary>
	// / get the filesystem above the low watermark?
	// / </summary>
	public boolean getAboveLowWatermark() {
		return (getUsedSpacePercentage() > filesystem.getLowWatermark()
				.floatValue());

	}

	// / <summary>
	// / get the filesystem above the high watermark?
	// / </summary>
	public boolean getAboveHighWatermark() {
		return (getUsedSpacePercentage() > filesystem.getHighWatermark()
				.floatValue());

	}

	public boolean getEnable() {
		return filesystem.getEnabled();
	}

	public ServerFilesystemInfo(Filesystem filesystem) {
		this.filesystem = filesystem;
		this.online = true;
		loadFreeSpace();
	}

	public void loadFreeSpace() {

		File file = new File(filesystem.getFilesystemPath());
		totalBytes = file.getTotalSpace();
		freeBytes = file.getFreeSpace();
	}
}
