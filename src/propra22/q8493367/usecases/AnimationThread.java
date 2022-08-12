package propra22.q8493367.usecases;


import propra22.q8493367.controllers.IDrawPanel;
import propra22.q8493367.entities.TangentPair;

/**
 * The Class AnimationThread is responsible for
 * updating the view and updating the tangent pair. 
 * This is done in regular intervals.
 */
public class AnimationThread extends Thread {
	
	
	/** 
	 * The thread is executed as long as this
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
	 * Sets the duration of the time the thread sleeps after updating the view and 
	 * the tangent pair. The duration of the sleep time depends on the number 
	 * of antipodal pairs. The antipodal pairs have the points A and C as ending 
	 * points as described in the specification on page 23. 
	 * If we do not distinguish two antipodal pairs with the same endpoints 
	 * by A and C, it would be twice the number of antipodal pairs.
	 */
	private void setDuration() {
		float t = -.0019f*tangentPair.getNumberOfAntipodalPairs() + 20f;
		duration = t < 0 ? 1 : (int)t;
	}
	
	
	/**
	 * This method is executed if the thread is started. It updates the view
	 * and the tangent pair. Afterwards it causes the thread to temporarily
	 * cease the execution for a certain amount of time.
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
