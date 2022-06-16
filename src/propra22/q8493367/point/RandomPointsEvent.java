package propra22.q8493367.point;



public class RandomPointsEvent implements IRandomPointsEvent {
	private final RandomPointsEventType type;
	private final int minX;
	private final int minY; 
	private final int maxX;
	private final int maxY;
	
	public RandomPointsEvent(RandomPointsEventType type, 
			int minX, 
			int minY, 
			int maxX,
			int maxY) {
		this.type = type;
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
	}
	
	

	public int getMinX() {
		return minX;
	}



	public int getMinY() {
		return minY;
	}



	public int getMaxX() {
		return maxX;
	}



	public int getMaxY() {
		return maxY;
	}



	@Override
	public RandomPointsEventType  getType(){
		return type;
	}
}
