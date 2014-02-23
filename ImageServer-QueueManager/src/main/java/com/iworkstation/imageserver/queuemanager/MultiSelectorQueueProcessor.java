package com.iworkstation.imageserver.queuemanager;

import java.util.ArrayList;
import java.util.List;

/**
 * MultiSelectorQueueProcessor builds on top of QueueProcessor to allow multiple
 * job selectors to work together, each with its own thread pool.
 * 
 * @author hwang
 * @version 6/10/2011
 *
 * @param <T> Subtype of Job.
 */
public class MultiSelectorQueueProcessor<T extends Job>
{
    /**
     * The job selector factory is used to produce the job selectors and their configurations.
     */
    private JobSelectorFactory<T> jobSelectorFactory;
    
    /**
     * The job processor is used to process jobs for all job selectors.
     */
    private JobProcessor<T> jobProcessor;
    
    /**
     * The queue manager used to interact with the database queue.
     */
    private QueueManager<T> queueManager;
    
    /**
     * The actual queue processors, each for a job selector.
     */
    private List<QueueProcessor<T>> queueProcessors;
    
    /**
     * Set the job selector factory for this multi-selector queue processor.
     * 
     * @param jobSelectorFactory The job selector factory to set to.
     */
    public void setJobSelectorFactory(JobSelectorFactory<T> jobSelectorFactory)
    {
        if (jobSelectorFactory == null)
        {
            throw new IllegalArgumentException("Argument jobSelectorFactory must not be null");
        }
        this.jobSelectorFactory = jobSelectorFactory;
    }
    
    /**
     * Get the job selector factory for this queue processor.
     * @return The job selector factory.
     */
    public JobSelectorFactory<T> getJobSelectorFactory()
    {
        return jobSelectorFactory;
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
     * Set the queue manager used to interface with the database queue.
     * 
     * @param queueManager
     *            The queue manager to set to.
     */
    public void setQueueManager(QueueManager<T> queueManager)
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
    public QueueManager<T> getQueueManager()
    {
        if (queueManager == null)
        {
            throw new IllegalStateException("queueManager is not specified. Must specify queueManager");
        }
        return queueManager;
    }
    
    /**
     * Start the queue processors for the job selectors.
     */
    public void init()
    {
        List<JobSelectorConfiguration<T>> jobSelectorConfigurations = jobSelectorFactory.getJobSelectorConfigurations();
        queueProcessors = new ArrayList<QueueProcessor<T>>(jobSelectorConfigurations.size());
        for (JobSelectorConfiguration<T> jobSelectorConfiguration : jobSelectorConfigurations)
        {
            QueueProcessor<T> queueProcessor = new QueueProcessor<T>();
            queueProcessor.setQueueManager(queueManager);
            queueProcessor.setJobProcessor(jobProcessor);
            queueProcessor.setJobSelector(jobSelectorConfiguration.getJobSelector());
            queueProcessor.setThreadPoolSize(jobSelectorConfiguration.getThreadPoolSize());
            queueProcessor.setTimeToSleepInMillis(jobSelectorConfiguration.getTimeToSleepInMillis());
            queueProcessor.setBatchOperationSize(jobSelectorConfiguration.getBatchOperationSize());
            queueProcessor.init();
            queueProcessors.add(queueProcessor);
        }
    }

    /**
     * Request the queue processors to stop as soon as possible.
     */
    public void requestToStop()
    {
        for (QueueProcessor<T> queueProcessor : queueProcessors)
        {
            queueProcessor.requestToStop();
        }
    }
}
