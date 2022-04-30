package propra22.q8493367.command;
import propra22.q8493367.point.IPoint;

/**
 * 
 * The command for dragging a point from one position to
 * another position.
 *
 */


public class DragPointCommand implements ICommand {
    
	// new x coordinate minus old x coordinate
	private int dx;
	//new y coordinate minus old y coordinate
	private int dy;
	//point which is dragged
	private IPoint point;
	
	
	public DragPointCommand(int dx, int dy, IPoint point) {
		this.dx = dx;
		this.dy = dy;
		this.point = point;
	}
	
	
	@Override
	public void execute() {
		point.translate(dx, dy);	
	}

	@Override
	public void unexecute() {
		point.translate(-dx, -dy);	
	}
}
