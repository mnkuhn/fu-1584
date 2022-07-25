package propra22.q8493367.main;


import java.net.URL;

// TODO: Auto-generated Javadoc
/**
 * The Interface IMainWindow for all main windows
 * of the application.
 */
public interface IMainWindow {

	
	
	/**
	 * Sets the main window listener.
	 *
	 * @param mainWindowListener the new main window listener
	 */
	void setMainWindowListener(IMainWindowListener mainWindowListener);
	
	
	
	
	
	/**
	 * Sets the undo item in the edit menu 
	 * enabled or disabled.
	 *
	 * @param b - if true, undo is enabled 
	 * otherwise it is disabled.
	 */
	void setUndoEnabled(boolean b);
	
	/**
	 * Sets the redo item in the edit menu
	 * enabled or disabled.
	 *
	 * @param b - if tue, redo is enabled
	 * otherwise it is disabled.
	 */
	void setRedoEnabled(boolean b);
	
	/**
	 * Shows the user manual.
	 *
	 * @param url - the url of the user manual
	 */
	void showManual(URL url);
	
	
	
	
	/**
	 * Sets the convex hull visible or invisible.
	 * 
	 *
	 * @param b -  true to make the convex hull visible; false to make it invisible
	 */
	void setConvexHullIsShown(boolean b);
	
	/**
	 * Sets the diameter visible or invisible.
	 *
	 * @param b - true to make the diameter visible; false to make it invisible.
	 */
	void setDiameterIsShown(boolean b);
	
	/**
	 * Sets the quadrangle visible or invisible.
	 *
	 * @param b - true to make the quadrangle visible; false to make it invisible.
	 */
	void setQuadrangleIsShown(boolean b);
    
    /**
     * Sets the triangle visible or invisible.
     *
     * @param b - true to make the triangle visible; false to make it invisible.
     */
    
	//void setTriangleIsShown(boolean b);


     
    /**
     * Sets the animation running or not running.
     *
     * @param b true to make the animation running; false to stop it running.
     */
	void setAnimationIsShown(boolean b);

}
