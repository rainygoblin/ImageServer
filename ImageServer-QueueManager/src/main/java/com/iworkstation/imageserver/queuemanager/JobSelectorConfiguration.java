package com.iworkstation.imageserver.queuemanager;

/**
 * Specify the configuration for a job selector. A job selector can have its own
 * thread pool in a multi-selector queue processor.
 * 
 * @author hwang
 * @version 6/10/2011
 * 
 * @param <T> The subtype of Job.
 */
public class JobSelectorConfiguration<T extends Job>
{
    /**
     * The thread pool size for the thread pool associated with the job selector.
     */
    private int threadPoolSize;
    
    /**
     * Time to sleep in milliseconds if there are no new job found.
     */
    private long timeToSleepInMillis;
    
    /**
     * Batch operation size for restarting or deleting jobs.
     */
    private int batchOperationSize;
    
    /**
     * The job selector. 
     */
    private JobSelector<T> jobSelector;
    
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
     * Get the job selector used to select jobs.
     * 
     * @return The job selector.
     */
    public JobSelector<T> getJobSelector()
    {
        return jobSelector;
    }
    
    /**
     * Set the job selector.
     * 
     * @param jobSelector The job selector to set to.
     */
    public void setJobSelector(JobSelector<T> jobSelector)
    {
        this.jobSelector = jobSelector;
    }
}
