package com.iworkstation.imageserver.queuemanager;

import java.util.List;

/**
 * JobSelectorFactory is used to produce the JobSelectorConfigurations for
 * a multi-selector queue processor. For example, if a queue is to send
 * messages to multiple targets, each selector can represent the queue for
 * a target. It can have its own thread pool to process the messages.
 * 
 * @author hwang
 * @version 6/10/2011
 *
 * @param <T> Subtype of Job.
 */
public interface JobSelectorFactory<T extends Job>
{
    /**
     * Get the list of JobSelectorConfigurations used in a multi-selector queue processor.
     * @return The job selector configuration list.
     */
    List<JobSelectorConfiguration<T>> getJobSelectorConfigurations();
}
