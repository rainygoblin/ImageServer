package com.iworkstation.imageserver.queuemanager;

/**
 * The different states for the job. There are 6 job states:
 * 1. NEW: When the job is added to the queue to be processed.
 * 2. SELECTED: WHen the job is selected for processing.
 * 3. STARTED: The job processor has started processing the job.
 * 4. SUCCEEDED: The job has been successfully processed.
 * The job remain in the queue and to be deleted automatically later.
 * 5. FAILED: The job failed to be processed. The job
 * remain in the queue and to be deleted later manually.
 * 6. DELETED: Not define in this Enum because the job will be
 * physically deleted from the table.
 */
public enum DefaultJobState implements JobState
{
    NEW("New"), SELECTED("Selected"), STARTED("Started"), OK("Succeeded"), FAILED("Failed");

    private String description;

    /**
     * Private constructor of the enum value.
     * 
     * @param description
     */
    private DefaultJobState(String description)
    {
        this.description = description;
    }

    /**
     * Get the description for the value.
     * 
     * @return
     */
    @Override
    public String getDescription()
    {
        return description;
    }
}
