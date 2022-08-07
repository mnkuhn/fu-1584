package propra22.q8493367.controllers;

import propra22.q8493367.util.IMainWindowListener;

/**
 * The Interface for a main window.
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
	 * @param b if true, undo is enabled 
	 * otherwise undo is disabled.
	 */
	void setUndoEnabled(boolean b);
	
	/**
	 * Sets the redo item in the edit menu
	 * enabled or disabled.
	 *
	 * @param b if true, redo is enabled
	 * otherwise redo is disabled.
	 */
	void setRedoEnabled(boolean b);
	
	/**
	 * Shows the user manual.
	 *
	 * @param path the file path to the manual.
	 */
	void showManual(String path);
	
	
	
	
	/**
	 * Sets the convex hull visible or invisible.
	 * 
	 * @param b true to make the convex hull visible, false to make it invisible.
	 */
	void setConvexHullIsShown(boolean b);
	
	/**
	 * Sets the diameter visible or invisible.
	 *
	 * @param b true to make the diameter visible, false to make it invisible.
	 */
	void setDiameterIsShown(boolean b);
	
	/**
	 * Sets the quadrangle visible or invisible.
	 *
	 * @param b true to make the quadrangle visible, false to make it invisible.
	 */
	void setQuadrangleIsShown(boolean b);
    
    /**
     * Sets the triangle visible or invisible.
     *
     * @param b true to make the triangle visible false to make it invisible.
     */
   
     
    /**
     * Sets the animation visible or invisible.
     *
     * @param b true to make the animation visible, false make it invisible.
     */
	void setAnimationIsShown(boolean b);

}
