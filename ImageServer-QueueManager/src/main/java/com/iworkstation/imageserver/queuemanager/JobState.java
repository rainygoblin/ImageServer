package com.iworkstation.imageserver.queuemanager;

/**
 * JobState represents the different states for a job in a queue.
 * The actual implementation should use a Java enum class that 
 * implements this interface. See DefaultJobState as an example.
 * 
 * @author hwang
 * @version 6/10/2011
 */
public interface JobState
{
    /**
     * Get the name of the JobState to be used in the database.
     *
     * @return The name of the job state.
     */
    String name();
    
    /**
     * Get the description of the JobState to be used for display.
     * 
     * @return The display name for the job state.
     */
    String getDescription();
}
