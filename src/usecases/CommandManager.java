package usecases;

import java.util.ArrayList;
import java.util.List;


/**
 * The Class CommandManager is responsible 
 * for handling the commands for the redo
 * and undo functionality of the GUI.
 * 
 * @see <a href="https://www.youtube.com/watch?v=9qA5kw8dcSU">Explanation of the command pattern on youtube</a>
 */
public class CommandManager {

	/** The list of commands in order of execution */
	private List<ICommand> commandList = new ArrayList<>();

	/** 
	 * This index refers to the command which was
	 * executed last. If no commands are in
	 * the command list, the index is set 
	 * to -1.
	 */
	private int commandIndex = -1;

	/**
	 * Adds a command to the command list.
	 * @param command the command added to the command list
	 */
	public void add(ICommand command) {

		if (commandIndex != commandList.size() - 1) {
			removeAllComandsAfterCommandIndex();
		}
		commandList.add(command);
		commandIndex++;
	}

	/**
	 * Undoes the last executed command.
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
	 * Removes the all commands from the commands list whose index is greater 
	 * than the command index. This method is used when one command or
	 * more than one command have been undone an a new command is executed.
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
	 * Checks for commands that can be undone.
	 *
	 * @return  true, if there is at least one command that can be
	 * undone.
	 * False otherwise.
	 */
	public boolean hasUndoableCommands() {
		return commandIndex >= 0;
	}

	/**
	 * Checks for undone commands that can be redone.
	 *
	 * @return true, if there is at least one command that
	 * can be redone.
	 * False otherwise.
	 */
	public boolean hasRedoableCommands() {
		return commandIndex < (commandList.size() - 1);
	}
}
