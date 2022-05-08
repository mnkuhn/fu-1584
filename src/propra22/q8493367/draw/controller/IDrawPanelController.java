package propra22.q8493367.draw.controller;

import java.awt.Graphics;
import java.awt.Point;
import java.io.File;

import propra22.q8493367.point.IPoint;

// TODO: Auto-generated Javadoc
/**
 * The interface for the draw panel controller.
 */
public interface IDrawPanelController {
	
	/**
	 * Returns true, if there are no points registered in the draw panel model.
	 *
	 * @return true, if there are no points registered in the
	 * draw panel model.
	 */
	public boolean drawPanelModelIsEmpty();
	

	
	/**
	 * Creates the new draw panel.
	 */
	public void createNewDrawPanel();
	
	
	/**
	 * Returns true, if points where added or deleted or if 
	 * the coordinated of a point changed.
	 *
	 * @return true, if points where added or deleted or if 
	 * the coordinated of a point changed.
	 */
	public boolean dataChangedSinceLastSave();
	
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
	 * @return true, if there are commands which can be undone.
	 */
	public boolean undoIsEnabled();
	
	/**
	 * Redo is enabled.
	 *
	 * @return true, if there are commands which can be redone.
	 */
	public boolean redoIsEnabled();

	/**
	 * Inserts a number of random points into the draw panel model which all
	 * fit onto the reference draw panel (the 'unzoomed' draw panel).
	 *
	 * @param number the number
	 */
	public void insertRandomPoints(int number);
	
	/**
	 * Updates  the draw panel model.
	 */
	public void updateModel();
	
	/**
	 * Updates the draw panel.
	 */
	public void updateView();

    
	/**
	 * Insert a point to the draw panel model
	 *
	 * @param point the point
	 */
	public void insertPointToModel(IPoint point);


	/**
	 * Removes all points from the draw panel
	 */
	void clearModel();


	/**
	 * Returns the hull as one array, starting with the smallest point (minimal x coordinate, maximal y coordinate)
	 * and moving counterclockwise. The last point is the second point from the upper left section.
	 *
	 * @return the int[][]
	 */
	int[][] hullAsArray();


	public void deletePointFromModel(int mouseX, int mouseY);


	public void initializePointDrag(int mouseX, int mouseY);


	public void dragPoint(int mouseX, int mouseY);


	public void terminatePointDrag(int mouseX, int mouseY);


	public void paintDrawPanel(Graphics g);



	/**
	 * Saves all points which are registered in the draw panel
	 * model to disc.
	 *
	 * @param path the path
	 */
	void saveModel(String path);



	/**
	 * Loads points from a file into the draw
	 * panel model.
	 *
	 * @param file - the file from which the points are loaded.
	 */
	void loadPointsToModel(File file);

	
}
