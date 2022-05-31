package propra22.q8493367.point;

import java.awt.Dimension;
import java.awt.Point;

public class RandomPointsEvent implements IRandomPointsEvent {
	private final RandomPointsEventType type;
	private final double upperLeftCornerOfViewPortX;
	private final double upperLeftCornerOfViewPortY;
	private final Dimension viewportSize;
	
	public RandomPointsEvent(RandomPointsEventType type, 
			double upperLeftCornerOfViewPortX, 
			double upperLeftCornerOfViewPortY, 
			Dimension viewportSize) {
		this.type = type;
		this.upperLeftCornerOfViewPortX = upperLeftCornerOfViewPortX;
		this.upperLeftCornerOfViewPortY = upperLeftCornerOfViewPortY;
		this.viewportSize = viewportSize;
	}
	
	public double getUpperLeftCornerOfViewPortX() {
		return upperLeftCornerOfViewPortX;
	}

	public double getUpperLeftCornerOfViewPortY() {
		return upperLeftCornerOfViewPortY;
	}

	public Dimension getViewportSize() {
		return viewportSize;
	}

	@Override
	public RandomPointsEventType  getType(){
		return type;
	}
}
