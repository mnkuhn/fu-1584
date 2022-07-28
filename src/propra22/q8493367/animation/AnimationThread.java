package propra22.q8493367.animation;


import propra22.q8493367.draw.view.IDrawPanel;

/**
 * The Class AnimationThread is responsible for
 * updating the view which is the draw panel 
 * and afterwards updating the tangent pair. 
 * This is done in regular intervals.
 */
public class AnimationThread extends Thread {
	
	
	/** The Thread is running as long this
	 * variable is true.
	 * 
	 */
	private boolean isRunning = true;
	
	/** The tangent pair. */
	private TangentPair tangentPair;
	
	/** The view. */
	private IDrawPanel view;
	
	/** The duration of the sleep
	 * time (after updating view and tangent
	 * pair) in milliseconds.
	 */
	private int duration;
	
	/**
	 * Instantiates a new animation thread.
	 *
	 * @param tangentPair the tangent pair
	 * @param view the view
	 */
	public AnimationThread(TangentPair tangentPair, IDrawPanel view) {
		this.tangentPair = tangentPair;
		this.view = view;
		setDuration();
	}


	/**
	 * Sets the duration depending on the duration
	 * of the numbers of antipodal pairs. The antipodal pairs
	 * have the points a and c as ending points. If we do not 
	 * distinguish two antipodal pairs with the same endpoints by a and c, 
	 * it would be twice the number of antipodal pairs.
	 */
	private void setDuration() {
		float t = -.0019f*tangentPair.getNumberOfAntipodalPairs() + 20f;
		duration = t < 0 ? 1 : (int)t;
	}
	
	
	/**
	 * This method is executed if the thread is started.
	 */
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
	

	/**
	 * Terminates the thread.
	 */
	public void terminate() {
		isRunning = false;
	}	
}
