package propra22.q8493367.command;

import propra22.q8493367.draw.model.IDrawPanelModel;
import propra22.q8493367.point.IPoint;
import propra22.q8493367.point.Point;

/**
 * Command for removing a point from the drawPanelModel
 */

public class RemovePointCommand implements ICommand {

	IDrawPanelModel drawPanelModel;
	IPoint point;
	
	public RemovePointCommand(IPoint point, IDrawPanelModel drawPanelModel) {
	      this.point = point;
	      this.drawPanelModel = drawPanelModel;
	}
	
	@Override
	public void execute() {
		drawPanelModel.removePoint(point);
		
	}

	@Override
	public void unexecute() {
		drawPanelModel.addPoint(point);	
	}
}
