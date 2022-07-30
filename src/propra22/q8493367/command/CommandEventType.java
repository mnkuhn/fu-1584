package propra22.q8493367.command;


/**
 * The Enum CommandEventType specifies the type of a command event. There
 * are two types of command events: the event for a command which was executed before
 * and is now to be undone, and the event for a command that was previously 
 * undone and is now to be executed again.
 */
public enum CommandEventType {
	
	/** Event for a command that was previously executed and is now to be undone. */
	UNDO, 
	
	/** Event for a command that was previously undone and is now to be executed again. */
	REDO;
}
