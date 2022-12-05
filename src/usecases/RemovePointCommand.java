package usecases;

import entities.Point;
import entities.PointSet;



/**
 * Command for removing a point from the point set.
 */

public class RemovePointCommand implements ICommand {

	/** The point set from which the point is removed. */
	PointSet pointSet;
	
	/** The point which is removed */
	Point point;
	
	
	/**
	 * Instantiates a new remove point command.
	 *
	 * @param point the point which is removed from the point set.
	 * @param pointSet the point set from which the point is removed.
	 */
	public RemovePointCommand(Point point, PointSet pointSet) {
	      this.point = point;
	      this.pointSet = pointSet;
	}
	
	@Override
	public void execute() {
		pointSet.removePoint(point);
		
	}

	@Override
	public void unexecute() {
		pointSet.addCheckedWithSorting(point);	
	}
}
