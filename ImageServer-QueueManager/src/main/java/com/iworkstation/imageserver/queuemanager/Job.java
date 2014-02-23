package com.iworkstation.imageserver.queuemanager;


/**
 * The interface Job defines the basic properties for a job in the queue. The specific jobs should be implement this
 * interface and add any additional properties if needed. Those jobs should be implemented as a Hibernate entity class
 * that maps to a database table. Each queue will have its own database table.
 */
public interface Job
{

}
