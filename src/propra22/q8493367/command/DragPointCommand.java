package propra22.q8493367.command;
import propra22.q8493367.point.IPoint;


/**
 * 
 * The command for dragging a point from one position to
 * another position.
 *
 */


public class DragPointCommand implements ICommand {
    
	
	// New x coordinate minus old x coordinate for the whole drag operation
	private int dx;
	
	// New y coordinate minus old y coordinate for the whole drag operation
	private int dy;
	
	// Point which is dragged
	private IPoint point;
	
	
	/**
	 * Instantiates a new drag point command.
	 *
	 * @param dx - new x coordinate minus old x coordinate for the whole drag operation
	 * @param dy - new y coordinate minus old y coordinate for the whole drag operation
	 * @param point - the point which is dragged
	 */
	public DragPointCommand(int dx, int dy, IPoint point) {
		this.dx = dx;
		this.dy = dy;
		this.point = point;
	}
	
	
	/**
	 * Execute the command
	 */
	@Override
	public void execute() {
		point.translate(dx, dy);	
	}

	/**
	 * Unexecute the command
	 */
	@Override
	public void unexecute() {
		point.translate(-dx, -dy);	
	}
}
