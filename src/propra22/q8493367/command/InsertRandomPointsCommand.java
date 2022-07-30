package propra22.q8493367.command;

import java.util.Random;

import propra22.q8493367.contour.IPointSet;
import propra22.q8493367.point.IPoint;
import propra22.q8493367.point.Point;


/**
 * 
 * Command for inserting a number of randomly generated 
 * points into a point set. 
 * 
 */

public class InsertRandomPointsCommand implements ICommand {

	/** The inserted points. */
	private IPoint[] points;
	
	/** The point set in which the points are inserted. */
	private IPointSet pointSet;
	
	
	
	/**
	 * Instantiates a new insert random points command. The limits within which the 
     * coordinates of the randomly generated points
     * must lie are given by minX, minY, maxX and maxY.
	 *
	 * @param number the number of points to be inserted
	 * @param minX the minimum x coordinate for all the random points
	 * @param minY the minimum y coordinate for all the random points
	 * @param maxX the maximum x coordinate for all the random points
	 * @param maxY the maximum y coordinate for all the random points
	 * @param pointSet the point set
	 */
	public InsertRandomPointsCommand(int number, int minX, int minY, int maxX, int maxY, IPointSet pointSet) {
		this.points = new IPoint[number];
		this.pointSet = pointSet;
		Random random = new Random();
		for(int i = 0; i < number; i++) {
			IPoint point;
			do {
				int x = random.nextInt(maxX - minX) + minX;
				int y = random.nextInt(maxY - minY) + minY;
				point = new Point(x, y);
			}while(pointSet.hasPoint(point) >= 0);

			points[i] = point;	
		}
	}
	
	/**
	 * Execute the command.
	 */
	@Override
	public void execute() {
		for(int i = 0; i < points.length; i++) {
			pointSet.addPoint(points[i]);
		}	
	}

	/**
	 * Unexecute the command.
	 */
	@Override
	public void unexecute() {
		for(int i = 0; i < points.length; i++) {
			pointSet.removePoint(points[i]);
		}	
	}	
}
