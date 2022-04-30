package propra22.q8493367.command;


/**
 * The Enum CommandEventType specifies the type of a CommandEvent.
 */
public enum CommandEventType {
	
	/** A command which was executed before is to be undone again. */
	UNDO, 
 /** A command that was previously undone is to be executed again. */
 REDO;
}
