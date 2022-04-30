package propra22.q8493367.command;

import propra22.q8493367.draw.model.IDrawPanelModel;
import propra22.q8493367.point.IPoint;
import propra22.q8493367.point.Point;

/**
 * 
 * Command for inserting a number of points into a IDrawPanelModel.
 * 
 *
 */

public class InsertRandomPointsCommand implements ICommand {

	IPoint[] points;
	int number;
	IDrawPanelModel drawPanelModel;
	
	public InsertRandomPointsCommand(int number, int maxX, int maxY, IDrawPanelModel drawPanelModel) {
		this.points = new IPoint[number];
		this.number = number;
		this.drawPanelModel = drawPanelModel;
		
		for(int i = 0; i < number; i++) {
			int x = (int) Math.floor (Math.random() * (maxX + 1));
			int y = (int) Math.floor (Math.random() * (maxY + 1));
			points[i] = new Point(x, y);	
		}
	}
	
	@Override
	public void execute() {
		for(int i = 0; i < number; i++) {
			drawPanelModel.addPoint(points[i]);
		}	
	}

	@Override
	public void unexecute() {
		for(int i = 0; i < number; i++) {
			drawPanelModel.removePoint(points[i]);
		}	
	}	
}
