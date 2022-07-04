package propra22.q8493367.command;

import java.util.Random;

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
	
	/**  The number of the inserted points. */
	private int number;
	
	/** The drawPanelModel in which the points are inserted. */
	private IPointSet pointSet;
	
	
	
	/**
	 * Instantiates a new insert random points command.
	 *
	 * @param number the number
	 * @param minX the minimum x coordinate for all the random points
	 * @param minY the minimum y coordinate for all the random points
	 * @param maxX the maximum x coordinate for all the random points
	 * @param maxY the maximum y coordinate for all the random points
	 * @param pointSet the point set
	 */
	public InsertRandomPointsCommand(int number, int minX, int minY, int maxX, int maxY, IPointSet pointSet) {
		this.points = new IPoint[number];
		this.number = number;
		this.pointSet = pointSet;
		Random random = new Random();
		for(int i = 0; i < number; i++) {
			int x = random.nextInt(maxX - minX) + minX;
			int y = random.nextInt(maxY - minY) + minY;

			points[i] = new Point(x, y);	
		}
	}
	
	/**
	 * Execute the command.
	 */
	@Override
	public void execute() {
		for(int i = 0; i < number; i++) {
			pointSet.addPoint(points[i]);
		}	
	}

	/**
	 * Unexecute the command.
	 */
	@Override
	public void unexecute() {
		for(int i = 0; i < number; i++) {
			pointSet.removePoint(points[i]);
		}	
	}	
}
