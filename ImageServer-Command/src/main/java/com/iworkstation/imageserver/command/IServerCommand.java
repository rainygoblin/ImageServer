package com.iworkstation.imageserver.command;

public interface IServerCommand {

	String getDescription();

	// / <summary>
	// / Gets a value describing if the ServerCommand requires a rollback of the
	// operation its included in if it fails during execution.
	// / </summary>
	boolean isRequiresRollback();

	/**
	 * Execute the ServerCommand.
	 * 
	 * @param serverCommandProcessor
	 */
	void execute(ServerCommandProcessor serverCommandProcessor)
			throws CommandException;

	/**
	 * 
	 */
	void undo() throws CommandException;
}
