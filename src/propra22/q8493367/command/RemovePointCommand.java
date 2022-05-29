package propra22.q8493367.command;

import propra22.q8493367.draw.model.IPointSet;
import propra22.q8493367.point.IPoint;
import propra22.q8493367.point.Point;


/**
 * Command for removing a point from the drawPanelModel.
 */

public class RemovePointCommand implements ICommand {

	/** The point set from which the point is removed. */
	IPointSet pointSet;
	
	/** The point which is removed */
	IPoint point;
	
	
	/**
	 * Instantiates a new remove point command.
	 *
	 * @param point - the point which is removed from the drawPanelModel
	 * @param pointSet - the drawPanelModel from which the point is removed
	 */
	public RemovePointCommand(IPoint point, IPointSet pointSet) {
	      this.point = point;
	      this.pointSet = pointSet;
	}
	
	/**
	 * Execute the command
	 */
	@Override
	public void execute() {
		pointSet.removePoint(point);
		
	}

	/**
	 * Unexecute the command.
	 */
	@Override
	public void unexecute() {
		pointSet.addPoint(point);	
	}
}
