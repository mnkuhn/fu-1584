package propra22.q8493367.command;


/**
 * The Enum CommandEventType specifies the type of a CommandEvent. There
 * are two types of command events: the event for a command which was executed before
 * and is to be undone again, and the event for a command that was previously 
 * undone is to be executed again.
 */
public enum CommandEventType {
	
	/** Event for a command which was executed before is to be undone again. */
	UNDO, 
	
	/** Event for a command that was previously undone is to be executed again. */
	REDO;
}
