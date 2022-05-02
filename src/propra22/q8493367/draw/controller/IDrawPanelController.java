package propra22.q8493367.draw.controller;

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
	 * Saves all points which are registered in the draw panel
	 * model to disc.
	 *
	 * @param path the path
	 */
	public void saveModel(String path);
	
	/**
	 * Creates the new draw panel.
	 */
	public void createNewDrawPanel();
	
	/**
	 * Loads points from a file into the draw
	 * panel model.
	 *
	 * @param file - the file from which the points are loaded.
	 */
	public void loadPointsToModel(File file);
	
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


	public void insertPoint(IPoint point);

	
}
