package com.iworkstation.imageserver.queuemanager;

import java.util.List;


/**
 * Responsible for selecting new, incomplete, and completed jobs.
 * 
 * @param <T>
 *            The type of Job to process.
 */
public interface JobSelector<T extends Job>
{
    /**
     * Get the a new job from the queue and set the job state to STARTED in the database queue. If there are no new
     * jobs, return null.
     * 
     * @return The new job.
     */
    T getNewJob();

    /**
     * Get a list of incomplete jobs in batch. The batchSize determines the maximum number of jobs to return.
     * 
     * @param batchSize
     *            The number of jobs to return.
     * @return The list of incomplete jobs.
     */
    List<T> getIncompleteJobs(int batchSize);

    /**
     * Get a list of completed jobs in batch. The batchSize determines the maximum number of jobs to return.
     * 
     * @param batchSize
     *            The number of jobs to return.
     * @return The list of completed jobs.
     */
    List<T> getCompletedJobs(int batchSize);
}
