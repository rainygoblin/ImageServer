package com.iworkstation.imageserver.queuemanager;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;


/**
 * Abstract class for a Queue Processor template. The subclass should be implemented to process a specific queue. This
 * class implements Runnable. The actual process should run this in a daemon thread.
 * 
 * @param <T>
 *            T is a class implement interface Job.
 */
public class QueueProcessor<T extends Job> extends Thread
{
    public static long DEFAUL_TIME_TO_SLEEP_IN_MILLIS = 1000;
    public static int DEFAULT_THREAD_POOL_SIZE = 2;
    public static int DEFAULT_BATCH_OPERATION_SIZE = 10;
    
    private static final Logger logger = Logger.getLogger(QueueProcessor.class);

    /**
     * Flag indicating that stop has been requested in the run loop.
     */
    private volatile boolean stopRequested;

    /**
     * If there is no job in the queue to process, the main thread sleeps this many milliseconds before picking up the
     * next job. The default time is 1000 ms.
     */
    private long timeToSleepInMillis = DEFAUL_TIME_TO_SLEEP_IN_MILLIS;

    /**
     * The default thread pool size used to process the jobs. The default size is 2.
     */
    private int threadPoolSize = DEFAULT_THREAD_POOL_SIZE;

    /**
     * The batch size for restarting incomplete jobs and deletion of completed jobs.
     */
    private int batchOperationSize = DEFAULT_BATCH_OPERATION_SIZE;
    
    /**
     * The queue manager used to interface with the actual database queue.
     */
    private QueueManager<T> queueManager;

    /**
     * The job selector is responsible for selecting new, incomplete, and completed jobs.
     */
    private JobSelector<T> jobSelector;

    /**
     * The job process that actually process a single job.
     */
    private JobProcessor<T> jobProcessor;

    /**
     * Set the time to sleep in millisecond if there is no jobs in the queue to process.
     * 
     * @param timeToSleepInMillis
     *            The time to sleep.
     */
    public synchronized void setTimeToSleepInMillis(long timeToSleepInMillis)
    {
        if (timeToSleepInMillis < 0)
        {
            throw new IllegalArgumentException("Argument timeToSleepInMillis must be >= 0: " + timeToSleepInMillis);
        }
        this.timeToSleepInMillis = timeToSleepInMillis;
    }

    /**
     * Get the time to sleep in milliseconds if there is no jobs to process.
     * 
     * @return The time to sleep.
     */
    public synchronized long getTimeToSleepInMillis()
    {
        return timeToSleepInMillis;
    }
    
    /**
     * Set the thread pool size used to process the jobs. Note once the queue processor is started, changing the thread
     * pool size is ignored.
     * 
     * @param threadPoolSize
     *            The thread pool size.
     */
    public synchronized void setThreadPoolSize(int threadPoolSize)
    {
        if (threadPoolSize <= 0)
        {
            throw new IllegalArgumentException("Argument threadPoolSize must be > 0");
        }
        this.threadPoolSize = threadPoolSize;
    }

    /**
     * Get the thread pool size used to process the jobs.
     * 
     * @return The thread pool size.
     */
    public synchronized int getThreadPoolSize()
    {
        return threadPoolSize;
    }

    /**
     * Set the batch operation size for restarting incomplete and deletion of completed jobs. The default value is 10.
     * 
     * @param batchOperationSize
     *            The batch operation size to set to.
     */
    public synchronized void setBatchOperationSize(int batchOperationSize)
    {
        if (batchOperationSize <= 0)
        {
            throw new IllegalArgumentException("Argument batchOperationSize must be > 0");
        }
        this.batchOperationSize = batchOperationSize;
    }

    /**
     * The restart of incomplete jobs and deletion of completed jobs are done in batch reads and process one job at a
     * time. This method returns the number of jobs to read from the database and cache in memory at a time. Note that
     * increase the value will increase the amount of memory used.
     * 
     * @return
     */
    public synchronized int getBatchOperationSize()
    {
        return batchOperationSize;
    }

    /**
     * Set the queue manager used to interface with the database queue.
     * 
     * @param queueManager
     *            The queue manager to set to.
     */
    public synchronized void setQueueManager(QueueManager<T> queueManager)
    {
        if (queueManager == null)
        {
            throw new IllegalArgumentException("Argument queueManager must not be null");
        }
        this.queueManager = queueManager;
    }

    /**
     * Get the queue manager for this job processor.
     * 
     * @return The queue manager.
     */
    public synchronized QueueManager<T> getQueueManager()
    {
        if (queueManager == null)
        {
            throw new IllegalStateException("queueManager is not specified. Must specify queueManager");
        }
        return queueManager;
    }

    /**
     * Set the job selector for this queue processor.
     * 
     * @param jobSelector
     *            The job selector to set to.
     */
    public synchronized void setJobSelector(JobSelector<T> jobSelector)
    {
        this.jobSelector = jobSelector;
    }

    /**
     * Get the job selector for this queue processor.
     * 
     * @return The job selector for this queue processor.
     */
    public synchronized JobSelector<T> getJobSelector()
    {
        return jobSelector;
    }

    /**
     * Set the job process for this queue processor.
     * 
     * @param jobProcessor
     *            The job processor to set to.
     */
    public synchronized void setJobProcessor(JobProcessor<T> jobProcessor)
    {
        if (jobProcessor == null)
        {
            throw new IllegalArgumentException("Argument jobProcessor cannot be null");
        }
        this.jobProcessor = jobProcessor;
    }

    /**
     * Get the job processor for this queue processor.
     * 
     * @return The job process for this queue processor.
     */
    public synchronized JobProcessor<T> getJobProcessor()
    {
        if (jobProcessor == null)
        {
            throw new IllegalStateException("jobProcessor is not specified. Must specify jobProcessor");
        }
        return jobProcessor;
    }

    /**
     * Check if the queue processor has been requested to stop processing any new jobs.
     * 
     * @return True is the stop has been requested.
     */
    public synchronized boolean stopRequested()
    {
        return stopRequested;
    }

    /**
     * Request the job processor to stop. Note that the job processor only stops after finishing the current jobs being
     * processed by the threads in the thread pool.
     */
    public synchronized void requestToStop()
    {
        stopRequested = true;
    }

    /**
     * Initialize the queue processor thread and start the thread execution.
     */
    public void init()
    {
        setDaemon(true);
        setName(getQueueManager().getName());
        start();
    }

    /**
     * Execute the queue processor main loop. This method does the following: 1) Initialize the thread pool. 2) Restart
     * any jobs that are incomplete due to crashes or hard process termination. 3) In the job processing loop, 3a) Get a
     * new job and mark it started 3b) If there is new job, get a thread from thread pool to process it. 3c) If there is
     * no new job, delete the completed jobs in the queue.
     */
    @Override
    public void run()
    {
        // Initialize the thread pool
        BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<Runnable>(getThreadPoolSize());
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(1, getThreadPoolSize(), 30, TimeUnit.SECONDS, blockingQueue);
        threadPool.setThreadFactory(new ThreadFactory()
        {
            public Thread newThread(Runnable runnable)
            {
                Thread thread = new Thread(runnable);
                thread.setName(getQueueManager().getName());
                return thread;
            }
        });
        logger.debug("Created thread pool with size " + getThreadPoolSize());
        
        // On start up, restart incomplete jobs first
        // that was started by this processor only.
        int numOfJobsRestarted = restartIncompleteJobs();
        logger.debug("Restarted " + numOfJobsRestarted + " interrupted jobs");

        // Job processing loop.
        while (!stopRequested())
        {
            try
            {
                logger.debug("Checking for new jobs");
                T job = getJobSelector().getNewJob();
                if (job != null)
                {
                    logger.debug("Found new job " + job);
                    
                    // Found a new job to process
                    final T finalJob = job;
                    boolean scheduled = false;
                    do
                    {
                        try
                        {
                            // Execute it using the pool.
                            logger.debug("Scheduling new job " + job);
                            threadPool.execute(new Runnable()
                            {
                                public void run()
                                {
                                    try
                                    {
                                        getQueueManager().markJobStarted(finalJob);
                                        logger.debug("Started job " + finalJob);
                                        getJobProcessor().processJob(finalJob);
                                        logger.debug("Processed job " + finalJob);
                                        getQueueManager().markJobSucceeded(finalJob);
                                        logger.debug("Completed job " + finalJob);
                                    }
                                    catch (JobProcessingException exception)
                                    {
                                        logger.error("Failed to process job " + finalJob, exception);
                                        getQueueManager().markJobFailed(finalJob, exception.getErrorMessage());
                                    }
                                }
                            });
                            scheduled = true;
                            logger.debug("Scheduled new job " + job);
                        }
                        catch (RejectedExecutionException rejectedException)
                        {
                            scheduled = false;
                            logger.debug("No more thread. Wait to schedule job " + job);
                            idleSleep();
                        }
                    }
                    while (!scheduled);
                }
                else
                {
                    // There is no new job to process.
                    // Use this "idle" time to delete completed jobs.
                    logger.debug("No new jobs found");
                    
                    int numOfCompletedJobsDeleted = deleteCompletedJobs();
                    logger.debug("Deleted " + numOfCompletedJobsDeleted + " completed jobs");

                    // Sleep for a while before checking again.
                    if (numOfCompletedJobsDeleted == 0)
                    {
                        idleSleep();
                    }
                }
            }
            catch (Throwable throwable)
            {
                logger.error("Failed in run loop. Wait for a while.", throwable);
                idleSleep();
            }
        }
    }

    /**
     * Restart incomplete jobs. Since the definition of incomplete jobs may differ, the subclass can override this
     * method to change the behavior. Note the restart of jobs is done one job at a time to avoid filling up the
     * database transaction logs.
     * 
     * @return The number of jobs restarted.
     */
    public int restartIncompleteJobs()
    {
        // TODO: Need to make this work for clustered job processors.
        int batchSize = getBatchOperationSize();
        int totalRestarted = 0;
        while (true)
        {
            List<T> incompleteJobList = getJobSelector().getIncompleteJobs(batchSize);
            if (incompleteJobList.size() > 0)
            {
                for (T incompleteJob : incompleteJobList)
                {
                    getQueueManager().restartJob(incompleteJob);
                    ++totalRestarted;
                    if (stopRequested())
                    {
                        return totalRestarted;
                    }
                }
            }
            else
            {
                break;
            }
        }
        return totalRestarted;
    }

    /**
     * Delete completed jobs. Since the definition of completed jobs may differ, the subclass can override this method
     * to change the behavior. Note that deletion is done one job at a time to avoid filling up the database transaction
     * log.
     * 
     * @return The total number of jobs deleted.
     */
    public int deleteCompletedJobs()
    {
        int batchSize = getBatchOperationSize();
        int totalDeleted = 0;
        while (true)
        {
            List<T> completedJobList = getJobSelector().getCompletedJobs(batchSize);
            if (completedJobList.size() > 0)
            {
                for (T completedJob : completedJobList)
                {
                    getQueueManager().deleteJob(completedJob);
                    ++totalDeleted;
                    if (stopRequested())
                    {
                        return totalDeleted;
                    }
                }
            }
            else
            {
                break;
            }
        }
        return totalDeleted;
    }
    
    private void idleSleep()
    {
        try
        {
            Thread.sleep(getTimeToSleepInMillis());
        }
        catch (InterruptedException ignore)
        {
            // Ignore
        }
    }
}
