package propra22.q8493367.command;

import propra22.q8493367.draw.model.IDrawPanelModel;
import propra22.q8493367.point.IPoint;
import propra22.q8493367.point.Point;

// TODO: Auto-generated Javadoc
/**
 * Command for inserting a point into a IDrawPanelModel.
 */

public class InsertPointCommand implements ICommand {
    
    /** The instance of a from IDrawPanelModel derived class, in which the point is inserted. */
    IDrawPanelModel drawPanelModel;
	
	/** The point which is inserted */
	IPoint point;
	
	
	/**
	 * Instantiates a new InsertPointCommand.
	 *
	 * @param x- x coordinate of the point
	 * @param y - y coordinate of the point
	 * @param drawPanelModel - Instance of a from IDrawPanelModel derived class, in which the point is inserted.
	 */
	public InsertPointCommand(int x, int y, IDrawPanelModel drawPanelModel) {
	      point = new Point(x, y);
	      this.drawPanelModel = drawPanelModel;
	}
	
	/**
	 * Execute the command 
	 */
	@Override
	public void execute() {
		drawPanelModel.addPoint(point);
		
	}

	/**
	 * Unexecute the command
	 */
	@Override
	public void unexecute() {
		drawPanelModel.removePoint(point);	
	}

}
