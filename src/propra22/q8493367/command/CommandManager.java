package propra22.q8493367.command;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {

	/** The command list. */
	private List<ICommand> commandList = new ArrayList<>();

	/** The actual position in the command list */
	private int commandIndex = -1;

	public void add(ICommand command) {

		if (commandIndex != commandList.size() - 1) {
			removeAllComandsAfterCommandIndex();
		}
		commandList.add(command);
		commandIndex++;
	}

	/**
	 * Undoes a command.
	 */

	public void undoCommand() {
		if (hasUndoableCommands()) {
			ICommand lastCommand = commandList.get(commandIndex);
			lastCommand.unexecute();
			commandIndex--;
		}
	}

	public void redoCommand() {
		if (hasRedoableCommands()) {
			ICommand command = commandList.get(commandIndex + 1);
			command.execute();
			commandIndex++;
		}
	}


	/**
	 * Removes the all comands after command index. This method is used when one ore
	 * more command have been undone an a new command is executed.
	 */
	private void removeAllComandsAfterCommandIndex() {
		for (int i = commandIndex + 1; i < commandList.size(); i++) {
			commandList.remove(i);
		}
	}

	public void clear() {
		commandList.clear();
		commandIndex = -1;
	}

	public boolean hasUndoableCommands() {
		return commandIndex >= 0;
	}

	public boolean hasRedoableCommands() {
		return commandIndex < (commandList.size() - 1);
	}

}
