package propra22.q8493367.point;



public enum GenerationEventType {
	
	TEN(10), FIFTY(50), HUNDRED(100), FIVEHUNDRED(500), THOUSAND(1000);
	
	private final int number;
	
	private GenerationEventType(int number) {
		this.number = number;
	}
	
	public int getNumber() {
		return number;
	}
}
