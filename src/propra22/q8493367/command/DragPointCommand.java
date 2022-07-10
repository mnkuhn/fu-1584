package propra22.q8493367.command;
import propra22.q8493367.draw.model.IPointSet;
import propra22.q8493367.point.IPoint;


// TODO: Auto-generated Javadoc
/**
 * 
 * The command for dragging a point from one position to
 * another position.
 *
 */


public class DragPointCommand implements ICommand {
    
	
	/** New x coordinate minus old x coordinate for the drag operation. */
	private int dx;
	
	/** New y coordinate minus old y coordinate for the whole drag operation. */
	private int dy;
	
	/** Point which is dragged. */
	private IPoint draggedPoint;
	
	
	/**
	 * Point which is deleted if the dragged point
	 * is dragged onto a spot, where already another
	 * point exists.
	 */
	private IPoint deletedPoint;

	/** The point set. */
	private IPointSet pointSet;
	
	
	
	/**
	 * Instantiates a new drag point command. If the
	 * point set contains a point, which has the same
	 * coordinated as the dragged point after the 
	 * dragging, this point is deleted from 
	 * the point set.
	 *
	 * @param dx the dx
	 * @param dy the dy
	 * @param draggedPoint the dragged point
	 * @param deletedPoint the eventually deleted point
	 * @param pointSet the point set
	 */
	public DragPointCommand(int dx, int dy, IPoint draggedPoint, IPoint deletedPoint, IPointSet pointSet) {
		this.dx = dx;
		this.dy = dy;
		this.draggedPoint = draggedPoint;
		this.deletedPoint = deletedPoint;
		this.pointSet = pointSet;
	}
	
	
	/**
	 * Execute the command.
	 */
	@Override
	public void execute() {
		draggedPoint.translate(dx, dy);
		if(deletedPoint != null) {
			pointSet.removePoint(deletedPoint);
		}
		
	    
	}

	/**
	 * Unexecute the command.
	 */
	@Override
	public void unexecute() {
		draggedPoint.translate(-dx, -dy);	
		if(deletedPoint != null) {
			pointSet.addPoint(deletedPoint);
		}
	}
}
