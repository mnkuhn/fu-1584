package propra22.q8493367.command;

import propra22.q8493367.draw.model.IDrawPanelModel;
import propra22.q8493367.point.IPoint;
import propra22.q8493367.point.Point;


/**
 * Command for removing a point from the drawPanelModel.
 */

public class RemovePointCommand implements ICommand {

	/** The draw panel model. */
	IDrawPanelModel drawPanelModel;
	
	/** The point. */
	IPoint point;
	
	
	/**
	 * Instantiates a new command.
	 *
	 * @param point - the point which is removed from the drawPanelModel
	 * @param drawPanelModel - the drawPanelModel from which the point is removed
	 */
	public RemovePointCommand(IPoint point, IDrawPanelModel drawPanelModel) {
	      this.point = point;
	      this.drawPanelModel = drawPanelModel;
	}
	
	/**
	 * Execute the command
	 */
	@Override
	public void execute() {
		drawPanelModel.removePoint(point);
		
	}

	/**
	 * Unexecute the command.
	 */
	@Override
	public void unexecute() {
		drawPanelModel.addPoint(point);	
	}
}
