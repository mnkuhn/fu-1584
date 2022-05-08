package propra22.q8493367.point;

public class RandomPointsEvent implements IRandomPointsEvent {
	RandomPointsEventType type;
	
	
	public RandomPointsEvent(RandomPointsEventType type) {
		this.type = type;
	}
	
	public RandomPointsEventType  getType(){
		return type;
	}
}
