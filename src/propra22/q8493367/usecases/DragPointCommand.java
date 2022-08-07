package propra22.q8493367.usecases;
import propra22.q8493367.entities.Point;
import propra22.q8493367.entities.PointSet;




/**
 * The command for dragging a point from one position to
 * another position.
 */


public class DragPointCommand implements ICommand {
    
	
	/** The x coordinate after dragging minus the x coordinate before dragging */
	private int dx;
	
	/** The y coordinate after dragging minus the y coordinate before dragging */
	private int dy;
	
	/** Point which is dragged */
	private Point draggedPoint;
	
	
	/**
	 * If the point set contains a point with different identity from
	 * the dragged point but has the same coordinates as the 
	 * dragged point after dragging, this point is deleted from 
	 * the point set. The variable deletedPoint refers to it.
	 * If there is no such point, deletedPoint is set to null.
	 */
	private Point deletedPoint;

	/** The point set. */
	private PointSet pointSet;
	
	
	
	/**
	 * Instantiates a new drag point command.
	 *
	 * @param dx the x coordinate after dragging minus the x coordinate
	 * before dragging.
	 * @param dy the y coordinate after dragging minus the y coordinate
	 * before dragging.
	 * @param draggedPoint the dragged point.
	 * @param deletedPoint the deleted point, if any. Null otherwise.
	 * @param pointSet the point set.
	 */
	public DragPointCommand(int dx, int dy, Point draggedPoint, Point deletedPoint, PointSet pointSet) {
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
	 * Unexecute the command
	 */
	@Override
	public void unexecute() {
		draggedPoint.translate(-dx, -dy);	
		if(deletedPoint != null) {
			pointSet.addPoint(deletedPoint);
		}
	}
}
