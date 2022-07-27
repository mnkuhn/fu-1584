package propra22.q8493367.command;

import propra22.q8493367.contour.IPointSet;
import propra22.q8493367.point.IPoint;


/**
 * Command for inserting a point into a point set.
 */

public class InsertPointCommand implements ICommand {
    
    /** The instance of a pointset, into which the point is inserted. */
    IPointSet pointSet;
	
	/** The point which is inserted */
	IPoint point;
	
	
	/**
	 * Instantiates a new InsertPointCommand.
	 *
	 * @param x - the x coordinate of the point
	 * @param y - the y coordinate of the point
	 * @param pointSet - the point set.
	 */
	public InsertPointCommand(IPoint point, IPointSet pointSet) {
	      this.point = point;
	      this.pointSet = pointSet;
	}
	
	/**
	 * Execute the insert point command 
	 */
	@Override
	public void execute() {
		pointSet.addPoint(point);
		
	}

	/**
	 * Unexecute the insert point command
	 */
	@Override
	public void unexecute() {
		pointSet.removePoint(point);	
	}

}
