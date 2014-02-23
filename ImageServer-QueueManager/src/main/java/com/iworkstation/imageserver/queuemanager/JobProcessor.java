package com.iworkstation.imageserver.queuemanager;


/**
 * The job process is responsible to process a single job.
 * 
 * @param <T>
 *            The type of job.
 */
public interface JobProcessor<T extends Job>
{
    /**
     * Process the job by actually doing the work. If the job processing fails, it will throw JobProcessingException.
     * 
     * @param job
     *            The job to be processed.
     * @throws JobProcessingException
     *             If job processing fails.
     */
    void processJob(T job) throws JobProcessingException;
}
