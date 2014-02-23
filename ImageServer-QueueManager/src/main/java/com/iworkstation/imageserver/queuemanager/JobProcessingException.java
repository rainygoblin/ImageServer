package com.iworkstation.imageserver.queuemanager;

public class JobProcessingException extends Exception
{
    private static final long serialVersionUID = 2881324573302711635L;
    private String errorMessage;

    public JobProcessingException(Throwable cause, String errorMessage)
    {
        initCause(cause);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage()
    {
        return errorMessage;
    }
}
