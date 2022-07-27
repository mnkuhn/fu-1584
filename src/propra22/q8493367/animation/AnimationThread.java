package propra22.q8493367.animation;


import propra22.q8493367.draw.view.IDrawPanel;

public class AnimationThread extends Thread {
	
	
	private boolean isRunning = true;
	private TangentPair tangentPair;
	private IDrawPanel view;
	private int duration;
	
	public AnimationThread(TangentPair tangentPair, IDrawPanel view) {
		this.tangentPair = tangentPair;
		this.view = view;
		setDuration();
	}


	private void setDuration() {
		float t = -.0019f*tangentPair.getNumberOfAntipodalPairs() + 20f;
		duration = t < 0 ? 1 : (int)t;
	}
	
	
	@Override
	public void run() {
		while(isRunning) {
			view.update();
			try {
				tangentPair.step();
				Thread.sleep(duration);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			} catch(ArithmeticException e) {
				e.printStackTrace();
			}
		}	
	}
	

	public void terminate() {
		isRunning = false;
	}	
}
