package propra22.q8493367.command;


/**
 * 
 * Implementation of the ICommandEvent. This class is used for
 * specifying the type of the CommandEvent in context with the
 * command pattern.
 *
 */ 


public interface ICommandEvent {

	/**
	 * 
	 * @return the type of the CommandEvent
	 */
	CommandEventType getEventType();

}
