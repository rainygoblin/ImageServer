package com.iworkstation.imageserver.command;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class ServerCommandProcessor {
	private static final Log LOG = LogFactory
			.getLog(ServerCommandProcessor.class);

	private String description;
	private String failureReason;
	private Stack<IServerCommand> stack = new Stack<IServerCommand>();
	private Queue<IServerCommand> queue = new LinkedList<IServerCommand>();
	private List<IServerCommand> list = new ArrayList<IServerCommand>();

	public ServerCommandProcessor(String description) {
		this.description = description;
	}

	public void addCommand(IServerCommand command) {
		queue.add(command);
		list.add(command);
	}

	public boolean execute() {

		while (queue.size() > 0) {
			// get first value and remove that object from queue
			IServerCommand command = queue.poll();
			stack.push(command);
			try {
				if (command != null) {
					command.execute(this);
				}

			} catch (Exception e) {
				if (command.isRequiresRollback()) {
					failureReason = String.format("%s: %s", command.getClass(),
							e.getMessage());

					rollback();
					return false;
				} else {
					stack.pop(); // Pop it off the stack, since it failed.
				}
			}

		}

		return true;
	}

	public void rollback() {

		while (stack.size() > 0) {
			IServerCommand command = stack.pop();

			try {
				command.undo();
			} catch (Exception e) {
				LOG.error(String.format(
						"Unexpected exception rolling back command {%s}",
						command.getDescription()), e);
			}
		}
	}

	public String getFailureReason() {
		return failureReason;
	}

	public String getDescription() {
		return description;
	}
}
