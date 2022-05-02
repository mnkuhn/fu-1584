package propra22.q8493367.command;



/**
 * This event indicates that the user wants to undo a command that was 
 * previously executed, or wants to re-execute a command that was previously 
 * undone.
 */
public class CommandEvent implements ICommandEvent {
	
	/** The type of the commandEvent */
	private	CommandEventType type;
	
	/**
	 * Instantiates a new command event.
	 *
	 * @param type - the type of the commandEvent
	 */
	public CommandEvent(CommandEventType type) {
		this.type = type;
	}
	
	/**
	 * Gets the type of the commandEvent.
	 *
	 * @return the type of the commandEvent
	 */
	@Override
	public CommandEventType getEventType() {
		return type;
	}
	
}
