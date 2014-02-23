package com.iworkstation.imageserver.dcm;

import java.util.concurrent.ThreadFactory;

final class DeviceListenerThreadFactory implements ThreadFactory {

	private static int threadNumber = 1;
	private String name;
	private int port;

	/**
	 * Get the thread number used for thread name and increment the thread
	 * number.
	 * 
	 * @return The thread number to be used for thread name.
	 */
	private synchronized int getThreadNumber() {
		return threadNumber++;
	}

	/**
	 * Create a thread factory for the specified device listening on host/port.
	 * 
	 * @param host
	 *            The host the device is listening on.
	 * @param port
	 *            The port the device is listening on.
	 */
	public DeviceListenerThreadFactory(String name, int port) {
		this.name = name;
		this.port = port;
	}

	@Override
	public Thread newThread(Runnable runnable) {
		Thread thread = new Thread(runnable);
		// Need to set thread to daemon so they won't block process from exiting
		thread.setDaemon(true);
		// Set the thread name so it is easier to locate in the debugger
		thread.setName("DicomDeviceListenerThread-" + name + ":" + port + "-"
				+ getThreadNumber());
		return thread;
	}

}
