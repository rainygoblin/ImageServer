package com.iworkstation.imageserver.workqueue;

import java.util.concurrent.RejectedExecutionException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.iworkstation.imageserver.domain.WorkQueue;

@Service("workQueueProcessor")
public class WorkQueueProcessor extends Thread {
	private static final Log LOG = LogFactory.getLog(WorkQueueProcessor.class);
	public static long DEFAUL_TIME_TO_SLEEP_IN_MILLIS = 10000;
	public static int DEFAULT_THREAD_POOL_SIZE = 10;

	/**
	 * If there is no job in the queue to process, the main thread sleeps this
	 * many milliseconds before picking up the next job. The default time is
	 * 1000 ms.
	 */
	private long timeToSleepInMillis = DEFAUL_TIME_TO_SLEEP_IN_MILLIS;

	/**
	 * The default thread pool size used to process the jobs. The default size
	 * is 2.
	 */
	private int threadPoolSize = DEFAULT_THREAD_POOL_SIZE;

	/**
	 * Flag indicating that stop has been requested in the run loop.
	 */
	private volatile boolean stopRequested;

	@Autowired
	private WorkQueueProcessorManager workQueueProcessorManager;

	@Autowired
	private TaskExecutor taskExceutor;

	// public synchronized WorkQueueProcessorManager
	// getWorkQueueProcessorManager() {
	// if (workQueueProcessorManager == null) {
	// throw new IllegalStateException(
	// "workQueueProcessorManager is not specified. Must specify workQueueProcessorManager");
	// }
	// return workQueueProcessorManager;
	// }
	//
	// public void setWorkQueueProcessorManager(
	// WorkQueueProcessorManager workQueueProcessorManager) {
	// this.workQueueProcessorManager = workQueueProcessorManager;
	// }

	/**
	 * Check if the queue processor has been requested to stop processing any
	 * new jobs.
	 * 
	 * @return True is the stop has been requested.
	 */
	public synchronized boolean stopRequested() {
		return stopRequested;
	}

	/**
	 * Request the job processor to stop. Note that the job processor only stops
	 * after finishing the current jobs being processed by the threads in the
	 * thread pool.
	 */
	public synchronized void requestToStop() {
		stopRequested = true;
	}

	/**
	 * Get the time to sleep in milliseconds if there is no jobs to process.
	 * 
	 * @return The time to sleep.
	 */
	public long getTimeToSleepInMillis() {
		return timeToSleepInMillis;
	}

	@PostConstruct
	public void init() {
		LOG.info("Start the WorkQueueProcessor to processor dicom message.");
		 setDaemon(true);
		 setName("WorkQueue Processor Service");
		start();
	}

	@PreDestroy
	public void cleanup() {
	}

	public void run() {
		// Initialize the thread pool
		// BlockingQueue<Runnable> blockingQueue = new
		// ArrayBlockingQueue<Runnable>(
		// threadPoolSize);
		// ThreadPoolExecutor threadPool = new ThreadPoolExecutor(1,
		// threadPoolSize, 30, TimeUnit.SECONDS, blockingQueue);
		// threadPool.setThreadFactory(new ThreadFactory() {
		// public Thread newThread(Runnable runnable) {
		// Thread thread = new Thread(runnable);
		// thread.setName("WorkQueue Processor Thread");
		// return thread;
		// }
		// });
		LOG.debug("Created thread pool with size " + threadPoolSize);

		// Job processing loop.
		while (!stopRequested()) {
			try {
				LOG.debug("Checking for new jobs");
				final WorkQueue finalWorkQueue = workQueueProcessorManager
						.getNewWorkQueue();
				if (finalWorkQueue != null) {
					LOG.debug("Found new job to processor " + finalWorkQueue);

					// Found a new job to process
					try {
						// Execute it using the pool.
						LOG.debug("Scheduling new job " + finalWorkQueue);
						taskExceutor.execute(new Runnable() {
							public void run() {
								processJob(finalWorkQueue);
							}
						});
						LOG.debug("Scheduled new job " + finalWorkQueue);
					} catch (RejectedExecutionException rejectedException) {
						LOG.debug("No more thread. Wait to schedule job "
								+ finalWorkQueue);
						idleSleep();
					}
				} else {
					// There is no new job to process.
					// Use this "idle" time to delete completed jobs.
					LOG.debug("No new jobs found");

					// int numOfCompletedJobsDeleted = deleteCompletedJobs();
					// LOG.debug("Deleted " + numOfCompletedJobsDeleted
					// + " completed jobs");
					//
					// // Sleep for a while before checking again.
					// if (numOfCompletedJobsDeleted == 0) {
					// idleSleep();
					// }
					idleSleep();
				}
			} catch (Throwable throwable) {
				LOG.error("Failed in run loop. Wait for a while.", throwable);
				idleSleep();
			}
		}
	}

	private void idleSleep() {
		try {
			Thread.sleep(getTimeToSleepInMillis());
		} catch (InterruptedException ignore) {
			// Ignore
		}
	}

	private void processJob(final WorkQueue finalWorkQueue) {
		try {
			workQueueProcessorManager.process(finalWorkQueue);
		} catch (WorkQueueProcessingException exception) {
			LOG.error("Failed to process job " + finalWorkQueue, exception);
			workQueueProcessorManager.markWorkQueueFailed(finalWorkQueue,
					exception.getErrorMessage());
		}
	}

}
