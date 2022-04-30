package propra22.q8493367.command;

import propra22.q8493367.draw.model.IDrawPanelModel;
import propra22.q8493367.point.IPoint;
import propra22.q8493367.point.Point;

/**
 * Command for inserting a point into a IDrawPanelModel
 */

public class InsertPointCommand implements ICommand {
    IDrawPanelModel drawPanelModel;
	IPoint point;
	
	public InsertPointCommand(int x, int y, IDrawPanelModel drawPanelModel) {
	      point = new Point(x, y);
	      this.drawPanelModel = drawPanelModel;
	}
	
	@Override
	public void execute() {
		drawPanelModel.addPoint(point);
		
	}

	@Override
	public void unexecute() {
		drawPanelModel.removePoint(point);	
	}

}
