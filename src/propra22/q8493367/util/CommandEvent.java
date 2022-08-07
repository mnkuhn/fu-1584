package propra22.q8493367.util;



/**
 * This event indicates that the user wants to undo a command that was 
 * previously executed, or wants to execute again a command that was previously 
 * undone.
 */
public class CommandEvent  {
	
	/** The type of the command event */
	private	CommandEventType type;
	
	/**
	 * Instantiates a new command event.
	 *
	 * @param type the type of the command event
	 */
	public CommandEvent(CommandEventType type) {
		this.type = type;
	}
	
	/**
	 * Gets the type of the command event.
	 *
	 * @return The type of the command event
	 */
	
	public CommandEventType getEventType() {
		return type;
	}
	
}
