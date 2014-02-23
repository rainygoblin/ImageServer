package com.iworkstation.imageserver.command;

public abstract class ServerCommand implements IServerCommand {
	private String description;
	private boolean requiresRollback;

	public ServerCommand(String description, boolean requiresRollback) {
		this.description = description;
		this.requiresRollback = requiresRollback;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public boolean isRequiresRollback() {
		return requiresRollback;
	}

	@Override
	public void execute(ServerCommandProcessor serverCommandProcessor)
			throws CommandException {
		onExecute(serverCommandProcessor);
	}

	@Override
	public void undo() throws CommandException {
		onUndo();
	}

	public abstract void onExecute(ServerCommandProcessor theProcessor)
			throws CommandException;

	public abstract void onUndo() throws CommandException;

}
