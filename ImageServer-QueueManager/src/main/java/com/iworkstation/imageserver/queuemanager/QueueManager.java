package com.iworkstation.imageserver.queuemanager;

import java.util.Date;
import java.util.List;


/**
 * The QueueManager defines the operations needed to add, remove, search, and manage the states of jobs in the queue.
 * 
 * @param <T>
 *            T should be a sub-type of interface Job.
 */
public interface QueueManager<T extends Job>
{
    /**
     * Get the name of the queue manager.
     * 
     * @return The name of the queue manager.
     */
    String getName();

    /**
     * Create a new job in the queue. The QueueManager will override the job submissionTime with the current time, set
     * the job startTime and completedTime to null, set the job errorMessage to null, set the job state to NEW. The
     * caller must provide the priority and description. The caller should also optionally provide the submitter.
     * 
     * @param job
     *            The job to create.
     */
    void createNewJob(T job);

    /**
     * Search the job queue for jobs that are in state NEW ordered by descending priority and ascending submissionTime.
     * The first job is then changed to STARTED state, and the startTime set to the current time and returned. If there
     * is not job matching the search criteria, null is returned. This call should be called by the processor before
     * working on the job. If there is no job returned, the caller should wait for sometime before calling this method
     * again to avoid a tight loop.
     * 
     * @return The first job to be processed in the queue. If there is no job to be processed, null is returned.
     */
    T getNewJob();

    /**
     * Search the job queue for jobs that are in state NEW ordered by descending priority and ascending submissionTime.
     * The selector of type Job is used to further filter the search. For example, if the submitter is filled in, this
     * will only select new jobs for the specified submitter. If the Job is implemented to include other fields, those
     * fields can be used to specify the search. But the state, priority and submissionTime will not be used in the
     * search, and the caller should not specify those fields. The first job is then changed to SELECTED state and returned. 
     * If there is not job matching the search criteria, null is
     * returned. This call should be called by the processor before working on the job. If there is no job returned, the
     * caller should wait for sometime before calling this method again to avoid a tight loop.
     * 
     * @param selector
     *            The selector job template.
     * @return The first job to be processed in the queue. If there is no job to be processed, null is returned.
     */
    T getNewJob(T selector);
    
    /**
     * Mark the job state to STARTED. The startTime is set to current time.
     * @param job
     *            The job to mark started.
     */
    void markJobStarted(T job);

    /**
     * Mark the job completed by setting the state to SUCCEEDED, and set the completedTime to the current time. No other
     * fields are changed.
     * 
     * @param job
     *            The job to mark succeeded.
     */
    void markJobSucceeded(T job);

    /**
     * Mark the job failed by setting the state to FAILED, and set the completedTime to the current time. The
     * errorMessage field is set to the error message provided by the caller. No other fields are changed.
     * 
     * @param job
     *            The job mark failed.
     * @param errorMessage
     *            The error message to indicate the cause of failure.
     */
    void markJobFailed(T job, String errorMessage);

    /**
     * Re-queue the job by setting the state to NEW, the submissionTime to the current time, the startTime and
     * completedTime to null, and errorMessage to null.
     * 
     * @param job
     *            The job to be restarted.
     */
    void restartJob(T job);

    /**
     * Delete the job from the queue. This will physically remove the job from the queue regardless of what state it is
     * in.
     * 
     * @param job
     *            The job to be deleted.
     */
    void deleteJob(T job);

    /**
     * Get the jobs that are completed before the cutoff date.
     * @param cutoffDate
     *            The cutoff date.
     * @param batchSize
     *            The number of results to return.
     */
    List<T> getJobsCompletedBefore(Date cutoffDate, int batchSize);

    /**
     * Find jobs matching the selector. The selector is an instance of type Job with the fields to be filtered on
     * specified. For example, if we want to find the jobs that are submitted by a person and are in failed state, we
     * can set selector.jobState to FAILED, and selector.submitter to the person.
     * 
     * @param selector
     *            The selector template for searching.
     * @return The list of matching jobs.
     */
    List<T> getJobs(T selector);

    /**
     * Similar to getJobs(T selector), this method only returns the first batchSize number of jobs. This is to limit the
     * amount of memory to hold the result. The ordering is determined by the ascending order of submissionTime.
     * 
     * @param selector
     *            The selector template for searching.
     * @param batchSize
     *            The number of result to return.
     * @return The list of matching jobs.
     */
    List<T> getJobs(T selector, int batchSize);

    /**
     * Get jobs in the specified job states. At most batchSize items are returned.
     * @param jobStates The job states to include.
     * @param batchSize The max number of items returned.
     * @return The jobs in the specified job states.
     */
    List<T> getJobsInJobStates(JobState[] jobStates, int batchSize);

    /**
     * Similar to getJobs(T selector, int batchSize), this method returns the matching jobs in pages. The specified
     * pageSize determines the number of rows per page. The pageIndex determines the index of the page to be returned.
     * The page index starts at 0.
     * 
     * @param selector
     *            The selector template for searching.
     * @param pageSize
     *            The number of result per page.
     * @param pageIndex
     *            The index of page.
     * @return The jobs for the specified page.
     */
    List<T> getJobs(T selector, int pageSize, int pageIndex);
    
}
