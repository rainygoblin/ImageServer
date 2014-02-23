package com.iworkstation.imageserver.workqueue;

public class WorkQueueProcessingException extends Exception
{
    private static final long serialVersionUID = 2881324573302711635L;
    private String errorMessage;

    public WorkQueueProcessingException(Throwable cause, String errorMessage)
    {
        initCause(cause);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage()
    {
        return errorMessage;
    }
}
