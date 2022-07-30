package propra22.q8493367.command;




/**
 * Command interface for the command pattern
 */
public interface ICommand {
	
	/**
	 * Executes the command.
	 */
	public  void execute();
	
	/**
	 * Undoes the execution of the command.
	 */
	public void unexecute();
}
