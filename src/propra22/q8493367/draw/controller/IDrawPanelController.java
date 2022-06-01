package propra22.q8493367.draw.controller;

import java.awt.Dimension;
import java.awt.Graphics;
import java.io.File;

import propra22.q8493367.command.ICommand;



// TODO: Auto-generated Javadoc
/**
 * The interface for the draw panel controller.
 */
public interface IDrawPanelController {
	
	/**
	 * Returns true, if there are no points registered in the draw panel model.
	 *
	 * @return true, if there are no points registered in the
	 * point set
	 */
	public boolean pointSetIsEmpty();
	

	
	/**
	 * Creates a new empty draw panel.
	 */
	public void createNewDrawPanel();
	
	
	
	
	
	
	/**
	 * Returns true, if points where added or deleted or if 
	 * the coordinates of a point changed.
	 *
	 * @return true, if points where added or deleted or if 
	 * the coordinated of a point changed.
	 */
	
	/*
	public boolean dataHasChangedSinceLastSave();
	*/
	
	/**
	 * Undoes a command.
	 */
	public void undoCommand();
	
	/**
	 * Redoes a command.
	 */
	public void redoCommand();
	
	/**
	 * Undo is enabled.
	 *
	 * @return True, if there are commands which can be undone, false otherwise.
	 */
	public boolean undoIsEnabled();
	
	/**
	 * Redo is enabled.
	 *
	 * @return True, if there are commands which can be redone, false otherwise.
	 */
	public boolean redoIsEnabled();

	/**
	 * Inserts a number of random points into the draw panel model which all
	 * fit onto the visible part of the draw panel.
	 *
	 * @param number -  the number of random points to be inserted
	 * @param d the d
	 * @param e the e
	 * @param dimension the dimension
	 */
	public void insertRandomPoints(int number, double d, double e, Dimension dimension);
	
	/**
	 * Updates the model (of the draw panel).
	 */
	public void updateModel();
	
	/**
	 * Updates the draw panel.
	 */
	public void updateView();

    


	/**
	 * Removes all points from the model (i.e. the point set and the hull)
	 */
	void clearModel();


	/**
	 * Insert a point into the point set if the user enters the point on
	 * the user interface. Model is updated afterwards.
	 *
	 * @param x the x
	 * @param y the y
	 */
	void insertPointToPointSetByUserInput(int x, int y);
	
	
	/**
	 * Insert point to point set from file input. 
	 * Model is not updated afterwards.
	 *
	 * @param x - the x coordinate of the point
	 * @param y - the y coordinate of the point
	 */
	void insertPointToPointSetByFileInput(int x, int y);
	
	
	/**
	 * Deletes a point from the point set as result of an action
	 * by the user.
	 *
	 * @param mouseX the mouse X
	 * @param mouseY the mouse Y
	 */
	void deletePointFromPointSetByUserInput(int mouseX, int mouseY);


	/**
	 * Initializes a point drag.
	 *
	 * @param mouseX - the x coordinate of the mouse which is the starting x coordinate of the 
	 * drag 
	 * @param mouseY - the y coordinate of the mouse which is the starting y coordinate of the 
	 * drag
	 */
	void initializePointDrag(int mouseX, int mouseY);


	/**
	 * Drags a point after the point drag has been initialized.
	 *
	 * @param mouseX - the x coordinate of the mouse
	 * @param mouseY - the y coordinate of the mouse
	 */
	void dragPoint(int mouseX, int mouseY);


	/**
	 * Terminates a point drag.
	 *
	 * @param mouseX - the x coordinate of the mouse
	 * @param mouseY - the y coordinate of the mouse
	 */
	void terminatePointDrag(int mouseX, int mouseY);


	/**
	 * Paints the draw panel.
	 *
	 * @param g the g
	 */
	void paintDrawPanel(Graphics g);


    
	/**
	 * Saves all points which are registered in the point set
	 * to disc.
	 *
	 * @return true, if successful
	 */
	
	/*
	void savePointSet(String path);
    */


	/**
	 * Loads points from a file into the point set.
	 *
	 * @param file - the file from which the points are loaded.
	 */
	
	/*
	void loadPointsToPointSet(File file);
	*/


	/**
	 * Convex hull is shown.
	 *
	 * @return true, if the user chose to display the 
	 * convex hull on the draw panel.
	 */
	boolean convexHullIsShown();
	
	/**
	 * Diameter is shown.
	 *
	 * @return true, if the user chose to display the 
	 * diameter on the draw panel.
	 */
	boolean diameterIsShown();
	
	/**
	 * Quadrangle is shown.
	 *
	 * @return true, if the user chose to display the 
	 * quadrangle on the draw panel.
	 */
	boolean quadrangleIsShown();
	
	/**
	 * Triangle is shown.
	 *
	 * @return true, if the user chose to display the 
	 * triangle on the draw panel.
	 */
	boolean triangleIsShown();
	
	/**
	 * Determines, if the convex hull
	 * should be shown.
	 *
	 * @param b - true, if the convex hull should 
	 * be shown. False otherwise.
	 */
	void setShowConvexHull(boolean b);
	
	/**
	 * Determines whether the diameter is to be shown.
	 *
	 * @param b the new show diameter
	 */
	void setShowDiameter(boolean b);
	
	/**
	 * Determines whether the quadrangle is to be shown.
	 *
	 * @param b the new show quadrangle
	 */
	void setShowQuadrangle(boolean b);
	
	/**
	 * Determines whether the triangle is to be shown.
	 *
	 * @param b the new show triangle
	 */
	void setShowTriangle(boolean b);

}
