package propra22.q8493367.controllers;

import propra22.q8493367.entities.Diameter;
import propra22.q8493367.entities.Quadrangle;


/**
 * The Interface for the draw panel controller. The draw
 * panel controller handles the updates for the model
 * and the view based on various events.
 *
 */
public interface IDrawPanelController {
	
	/**
	 * Returns true, if the point set does not contain any point.
	 * Returns false otherwise.
	 *
	 * @return true, if the point set does not contain any point, 
	 * false otherwise.
	 */
	public boolean pointSetIsEmpty();
	

	/**
	 * Creates a new empty draw panel.
	 */
	public void createNewDrawPanel();
	
	
	/**
	 * Undoes the last executed
	 * command.
	 */
	public void undoCommand();
	
	/**
	 *Executes the last undone command.
	 */
	public void redoCommand();
	
	/**
	 * Returns true, if a command can be undone.
	 * Returns false otherwise.
	 *
	 * @return true, if a command can be undone,
	 * false otherwise.
	 */
	public boolean undoIsEnabled();
	
	/**
	 * Returns true, if a undone command can be 
	 * executed again. Returns false otherwise.
	 *
	 * @return true, if successful
	 */
	public boolean redoIsEnabled();

	/**
	 * Updates the model.
	 */
	public void updateModel();
	
	/**
	 * Updates the draw panel.
	 */
	public void updateView();


	/**
	 * Removes all points from the point set.
	 */
	void clearModel();


	/**
	 * Inserts a point into the point set as result of an action
	 * by the user.
	 *
	 * @param x the x coordinate of the point.
	 * @param y the y coordinate of the point.
	 */
	void insertPointToPointSetByCommand(int x, int y);
	
	
	/**
	 * Inserts a point to the point set 
	 * but does not create a command. The point is 
	 * only inserted if there is no other point 
	 * with the same coordinates in the point set. If 
	 * a point was inserted, the point set is sorted afterwards.
	 *
	 * @param x the x coordinate of the point
	 * @param y the y coordinate of the point
	 */
	void insertPointToPointSetCheckedWithSorting(int x, int y);
	
	/**
	 * Sorts the points of the point set
	 * and removes duplicates.
	 */
	void sortAndCheckPoints();
	
	
	/**
	 * Deletes a point from the point set as result of an action
	 * by the user and creates the corresponding command.
	 *
	 * @param mouseX the x coordinate of the mouse.
	 * @param mouseY the y coordinate of the mouse.
	 * @param totalScale needed for the translation of distances from the model 
	 * coordinate system into the view coordinate system. It is the scale of the representation in
	 * the draw panel.
	 */
	void deletePointFromPointSetByCommand(int mouseX, int mouseY, double totalScale);


	/**
	 * Initializes a point drag.
	 *
	 * @param mouseX the x coordinate of the mouse which is the starting x coordinate of the
	 * drag 
	 * @param mouseY the y coordinate of the mouse which is the starting y coordinate of the
	 * drag
	 */
	void initializePointDrag(int mouseX, int mouseY);


	/**
	 * Drags a point after the point drag has been initialized.
	 *
	 * @param mouseX the x coordinate of the mouse
	 * @param mouseY the y coordinate of the mouse
	 */
	void dragPoint(int mouseX, int mouseY);


	/**
	 * Terminates a point drag. Called, when the
	 * user releases the mouse or the alt button.
	 *
	 * @param mouseX the x coordinate of the mouse
	 * @param mouseY the y coordinate of the mouse
	 */
	void terminatePointDrag(int mouseX, int mouseY);


	/**
	 * Returns true, if the convex hull is shown, 
	 * false otherwise.
	 *
	 * @return true, if the convex hull is shown. False otherwise.
	 */
	boolean getConvexHullIsshown();
	
	
	
	/**
	 * Returns true if the diameter is shown. False otherwise.
	 * 
	 * @return true if the diameter is shown, otherwise returns false.
	 */
	public boolean getDiameterIsShown();



	/**
	 *Returns true if the quadrangle is shown. False otherwise.
	 * 
	 * @return true if the quadrangle is shown, false otherwise.
	 */
	public boolean getQuadrangleIsShown();



	/**
	 *Returns true, if the animation is shown. False otherwise.
	 * 
	 * @return true if the animation is shown, false otherwise.
	 */
	public boolean getAnimationIsShown();


	
	
	/**
	 * Returns true, if the triangle is shown.
	 * False otherwise.
	 *
	 * @return true, if the triangle is shown, false otherwise.
	 */
	boolean getTriangleIsShown();
	
	
	/**
	 * Determines whether the convex hull
	 * is be shown.
	 *
	 * @param b  true, whether the convex hull is to 
	 * be shown. False otherwise.
	 */
	void setShowConvexHull(boolean b);
	
	/**
	 * Determines whether the diameter is to be shown.
	 *
	 * @param b true, if the diameter is 
	 * be shown. False otherwise.
	 */
	void setShowDiameter(boolean b);
	
	/**
	 * Determines whether the quadrangle is to be shown.
	 *
	 * @param b true, if the quadrangle is 
	 * be shown. False otherwise.
	 */
	void setShowQuadrangle(boolean b);
	
	/**
	 * Determines whether the triangle is to be shown.
	 *
	 * @param b true, if the triangle is 
	 * be shown. False otherwise.
	 */
	void setShowTriangle(boolean b);

    
	
	/**
	 * Determines whether the animation is to be shown.
	 *
	 * @param animationIsShown true if the animation is to
	 * be shown.False otherwise
	 */
	void setShowAnimation(boolean animationIsShown);


	/**
	 * Tries to insert a certain number of randomly generated points into the visible area
	 * of the draw panel. This can fail if the visible area does not 
	 * provide enough space.
	 *
	 * @param number the number of random points to be inserted into the point set.
	 * @param minX the minimum x coordinate which the points are allowed to have
	 * @param minY the minimum y coordinate which the points are allowed to have
	 * @param maxX the maximum x coordinate which the points are allowed to have
	 * @param maxY the maximum y coordinate which the points are allowed to have
	 */
	public void insertRandomPoints(int number, int minX, int minY, int maxX, int maxY);



	/**
	 * Adds an observer to the observer list.
	 *
	 * @param observer the observer which is added.
	 */
	void addObserver(IDrawPanelControllerObserver observer);



	/**
	 * Removes an observer from the observer list.
	 *
	 * @param observer the observer which is removed.
	 */
	void removeObserver(IDrawPanelControllerObserver observer);



	/**
	 * Notifies all observers.
	 */
	void notifyObservers();



	/**
	 * Resets the draw panel controller.
	 * 
	 */
	void reset();



    /**
     * Updates the coordinates of the mouse pointer. The
     * coordinates refer to the coordinate system of
     * the model.
     *
     * @param mouseX the x coordinate of the mouse pointer.
     * @param mouseY the y coordinate of the mouse pointer.
     * @param totalScale needed for the translation of distances from the model 
	 * coordinate system into the view coordinate system. It is the scale of the representation in
	 * the draw panel.
     */
    public void updateMouseData(int mouseX, int mouseY, double totalScale);
	

    /**
	 * Sets the state of the mouse. b is true if the mouse pointer is 
	 * located over the draw panel. It is false, if the mouse pointer 
	 * is not located over the draw panel.
	 *
	 * @param b true, if the mouse is located over the draw panel. 
	 * False otherwise.
	 */
	public void setMousePositionIsOverPanel(boolean b);


	/**
	 * Returns the diameter of the point set.
	 * 
	 * @return the diameter
	 */
	Diameter getDiameter();



	/**
	 * Returns the length of the diameter of the point set.
	 * 
	 * @return the diameter length
	 */
	double getDiameterLength();



	/**
	 * Returns the biggest quadrangle of the point set.
	 * 
	 * @return the biggest quadrangle
	 */
	Quadrangle getBiggestQuadrangle();



	/**
	 * Returns the hull as an array of integers.
	 * 
	 * @return the array of integers.
	 */
	int[][] getHullAsArray();
	
	/**
	 * Centers the representation on the draw panel.
	 */
	public void centerView();


	

}
