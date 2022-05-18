package propra22.q8493367.command;

import propra22.q8493367.draw.model.IPointSet;
import propra22.q8493367.point.IPoint;
import propra22.q8493367.point.Point;

// TODO: Auto-generated Javadoc
/**
 * Command for inserting a point into a IDrawPanelModel.
 */

public class InsertPointCommand implements ICommand {
    
    /** The instance of a from IDrawPanelModel derived class, in which the point is inserted. */
    IPointSet pointSet;
	
	/** The point which is inserted */
	IPoint point;
	
	
	/**
	 * Instantiates a new InsertPointCommand.
	 *
	 * @param x- x coordinate of the point
	 * @param y - y coordinate of the point
	 * @param pointSet - Instance of a from IDrawPanelModel derived class, in which the point is inserted.
	 */
	public InsertPointCommand(int x, int y, IPointSet pointSet) {
	      point = new Point(x, y);
	      this.pointSet = pointSet;
	}
	
	/**
	 * Execute the command 
	 */
	@Override
	public void execute() {
		pointSet.addPoint(point);
		
	}

	/**
	 * Unexecute the command
	 */
	@Override
	public void unexecute() {
		pointSet.removePoint(point);	
	}

}
