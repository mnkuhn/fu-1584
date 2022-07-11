package propra22.q8493367.command;

import java.util.ArrayList;
import java.util.List;


/**
 * The Class CommandManager is responsible 
 * for handling the commands for the redo
 * and undo functionality of the GUI.
 */
public class CommandManager {

	/** The list of commands in order of execution */
	private List<ICommand> commandList = new ArrayList<>();

	/**  This index points to the command which was
	 * executed last.  
	 */
	private int commandIndex = -1;

	/**
	 * Adds a command to the command list.
	 * @param command the command
	 */
	public void add(ICommand command) {

		if (commandIndex != commandList.size() - 1) {
			removeAllComandsAfterCommandIndex();
		}
		commandList.add(command);
		commandIndex++;
	}

	/**
	 * Undoes the last executed command
	 */
	public void undoCommand() {
		if (hasUndoableCommands()) {
			ICommand lastCommand = commandList.get(commandIndex);
			lastCommand.unexecute();
			commandIndex--;
		}
	}

	/**
	 * Redoes the last undone command.
	 */
	public void redoCommand() {
		if (hasRedoableCommands()) {
			ICommand command = commandList.get(commandIndex + 1);
			command.execute();
			commandIndex++;
		}
	}


	/**
	 * Removes the all commands from the commands list which have index more than 
	 * commandIndex. This method is used when one command or
	 * more than one commands have been undone an a new command is executed.
	 */
	private void removeAllComandsAfterCommandIndex() {
		for (int i = commandList.size() - 1; i > commandIndex; i--) {
			commandList.remove(i);
		}
	}

	/**
	 * Clears the list of commands
	 * and resets the command index.
	 */
	public void clear() {
		commandList.clear();
		commandIndex = -1;
	}

	/**
	 * Checks for undoable commands.
	 *
	 * @return  true, if some commands have 
	 * been executed.
	 * False otherwise.
	 */
	public boolean hasUndoableCommands() {
		return commandIndex >= 0;
	}

	/**
	 * Checks for redoable commands.
	 *
	 * @return true, if some commands have been undone, false otherwise.
	 */
	public boolean hasRedoableCommands() {
		return commandIndex < (commandList.size() - 1);
	}

}
