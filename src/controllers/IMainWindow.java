package controllers;

import gui.IMainWindowListener;

/**
 * The Interface for the main window. It declares methods 
 * for setting the main window listener and for enabling or 
 * disabling various functionalities of the menus 
 * of the menu bar.
 * 
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
	 * @param b if true undo is enabled, 
	 * otherwise undo is disabled.
	 */
	void setUndoEnabled(boolean b);
	
	/**
	 * Sets the redo item in the edit menu
	 * enabled or disabled.
	 *
	 * @param b if true redo is enabled,
	 * otherwise redo is disabled.
	 */
	void setRedoEnabled(boolean b);
	
	/**
	 * Shows the operational manual.
	 *
	 * @param path the file path of the operational manual.
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
	 * @param b true to make the triangle visible, false to make it invisible.
	 */
	public void setTriangleIsShown(boolean b);
     
    /**
     * Sets the animation visible or invisible.
     *
     * @param b true to make the animation visible, false to  make it invisible.
     */
	void setAnimationIsShown(boolean b);

}
