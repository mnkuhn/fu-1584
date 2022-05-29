package propra22.q8493367.command;

import propra22.q8493367.draw.model.IPointSet;
import propra22.q8493367.point.IPoint;
import propra22.q8493367.point.Point;

// TODO: Auto-generated Javadoc
/**
 * 
 * Command for inserting a number of randomly generated 
 * points into a IDrawPanelModel. The minimal x and y coorindates
 * are 0, the maximal x and y coordinates are arguments of the
 * constructor of the command.
 * 
 */

public class InsertRandomPointsCommand implements ICommand {

	/** The inserted points. */
	private IPoint[] points;
	
	/** The number of the inserted points */
	private int number;
	
	/** The drawPanelModel in which the points are inserted. */
	private IPointSet pointSet;
	
	
	/**
	 * Instantiates a new insert random points command.
	 *
	 * @param number - number the insert points
	 * @param maxX - the maximal x coordinate a point is allowed to have
	 * @param maxY - the maximal y coordinate a point is allowed to have
	 * @param pointSet - the DrawPanelModel, in which the points are inserted.
	 */
	public InsertRandomPointsCommand(int number, int maxX, int maxY, IPointSet pointSet) {
		this.points = new IPoint[number];
		this.number = number;
		this.pointSet = pointSet;
		
		for(int i = 0; i < number; i++) {
			int x = (int) Math.floor (Math.random() * (maxX + 1 - 20)) + 10;
			int y = (int) Math.floor (Math.random() * (maxY + 1 - 20)) + 10;
			points[i] = new Point(x, y);	
		}
	}
	
	/**
	 * Execute the command
	 */
	@Override
	public void execute() {
		for(int i = 0; i < number; i++) {
			pointSet.addPoint(points[i]);
		}	
	}

	/**
	 * Unexecute the command
	 */
	@Override
	public void unexecute() {
		for(int i = 0; i < number; i++) {
			pointSet.removePoint(points[i]);
		}	
	}	
}
