package propra22.q8493367.controllers;

import propra22.q8493367.entities.Diameter;
import propra22.q8493367.entities.Quadrangle;
import propra22.q8493367.util.IDrawPanelControllerObserver;


/**
 * The interface for the draw panel controller.
 */
public interface IDrawPanelController {
	
	/**
	 * Returns true, if the point set does not contain a point.
	 * Returns false otherwise.
	 *
	 * @return true, if the point set does not contain a point, 
	 * false otherwise.
	 */
	public boolean pointSetIsEmpty();
	

	/**
	 * Creates a new empty draw panel.
	 */
	public void createNewDrawPanel();
	
	
	/**
	 * Undoes a command.
	 */
	public void undoCommand();
	
	/**
	 * Redoes a command.
	 */
	public void redoCommand();
	
	/**
	 * Returns true, if the undo functionality is enabled.
	 * Returns false otherwise.
	 *
	 * @return True, if the undo functionality is enabled,
	 * false otherwise.
	 */
	public boolean undoIsEnabled();
	
	/**
	 * Returns true, if the redo functionality is enabled.
	 * Returns false otherwise.
	 *
	 * @return True, if the redo functionality is enabled, 
	 * false otherwise.
	 */
	public boolean redoIsEnabled();

	/**
	 * Updates the model of the draw panel. This means, the point set, the
	 * convex hull, the diamter, the quadrangle and the quadrangle sequence 
	 * are updated. Afterwards, all observers are notified.
	 */
	public void updateModel();
	
	/**
	 * Updates the draw panel.
	 */
	public void updateView();


	/**
	 * Removes all points from the point set. As a consequence
	 * all points are removed from the hull.
	 */
	void clearModel();


	/**
	 * Inserts a point into the point set as result of an action
	 * by the user. Afterwards, the model is updated.
	 *
	 * @param x the x coordinate of the point.
	 * @param y the y coordinate of the point.
	 */
	void insertPointToPointSetByUserInput(int x, int y);
	
	
	/**
	 * Inserts point to point set from file input. 
	 * Model is not updated afterwards.
	 *
	 * @param x the x coordinate of the point
	 * @param y the y coordinate of the point
	 */
	void insertPointToPointSetByFileInput(int x, int y);
	
	
	/**
	 * Deletes a point from the point set as result of an action
	 * by the user. Afterwards, the model is updated.
	 *
	 * @param mouseX the x coordinate of the mouse.
	 * @param mouseY the y coordinate of the mouse.
	 * @param totalScale the total scale
	 */
	void deletePointFromPointSetByUserInput(int mouseX, int mouseY, double totalScale);


	/**
	 * Initializes a point drag.
	 *
	 * @param mouseX the x coordinate of the mouse which is the starting x coordinate of the
	 * drag 
	 * @param mouseY the y coordinate of the mouse which is the starting y coordinate of the
	 * drag
	 * @param totalScale scale * panelScale. This value is needed to calculate the 
	 * distance in the model.
	 */
	void initializePointDrag(int mouseX, int mouseY, double totalScale);


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
	 * Returns true if the diameter is shown on the draw panel. Returns false otherwise.
	 * 
	 * @return true if the diameter is shown, otherwise returns false.
	 */
	public boolean getDiameterIsShown();



	/**
	 *Returns true if the quadrangle is shown on the draw panel. Returns false otherwise.
	 * 
	 * @return true if the quadrangle is shown, false otherwise.
	 */
	public boolean getQuadrangleIsShown();



	/**
	 *Returns true, if the animation is shown on the draw panel. Returns false otherwise.
	 * 
	 * @return true if the animation is shown, false otherwise.
	 */
	public boolean getAnimationIsShown();


	
	
	/**
	 * Returns true, if the triangle is shown on the draw panel,
	 * false otherwise.
	 *
	 * @return true, if the triangle is shown, false otherwise.
	 */
	boolean getTriangleIsShown();
	
	
	/**
	 * Determines, if the convex hull
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
	 * Insert a certain number of randomly generated points into the point
	 * set.
	 *
	 * @param number the number of random points to be inserted into the point set.
	 * @param minX the minimum x coordinate which the points are allowed to have
	 * @param minY the minimum y coordinate which the points are allowed to have
	 * @param maxX the maximum x coordinate which the points are allowed to have
	 * @param maxY the maximum y coordinate which the points are allowed to have
	 */
	public void insertRandomPoints(int number, int minX, int minY, int maxX, int maxY);



	/**
	 * Adds an observer.
	 *
	 * @param observer the observer which is added
	 */
	void addObserver(IDrawPanelControllerObserver observer);



	/**
	 * Removes an observer.
	 *
	 * @param observer the observer which is removed
	 */
	void removeObserver(IDrawPanelControllerObserver observer);



	/**
	 * Notifies all observers.
	 */
	void notifyObservers();



	/**
	 * Resets the draw panel controller.
	 * This method is used, if a new point set
	 * is loaded from a file or if a new draw
	 * panel is created.
	 */
	void reset();



    /**
     * Updates the mouse data.
     *
     * @param mouseX the x coordinate of the mouse.
     * @param mouseY the y coordinate of the mouse.
     * @param totalScale the product of scale and panelScale. This value
     * is needed to calculate distances in the model. In this 
     * application we need it to determine the selected point.
     */
    public void updateMouseData(int mouseX, int mouseY, double totalScale);
	

    /**
	 * Sets the state of the mouse. b is true if the mouse pointer is 
	 * located over the draw panel. b is false, if the mouse pointer 
	 * is not located over the draw panel.
	 *
	 * @param b true, if the mouse is located over the draw panel. 
	 * False otherwise.
	 */
	public void setMousePositionIsOverPanel(boolean b);


	/**
	 * Gets the diameter of the point set.
	 * 
	 * @return the diameter
	 */
	Diameter getDiameter();



	/**
	 * Gets the length of the diameter of the point set.
	 * 
	 * @return the diameter length
	 */
	double getDiameterLength();



	/**
	 * Gets the biggest quadrangle of the point set
	 * 
	 * @return the biggest quadrangle
	 */
	Quadrangle getBiggestQuadrangle();



	/**
	 * Returns the hull as an array.
	 * 
	 * @return the n x 2 int array which contains the coordinate of all points of the hull 
	 * moving clockwise along the hull. We refer to a standard cartesian coordinate system.
	 */
	int[][] hullAsArray();
	
	/**
	 * Centers the representation on the view.
	 */
	public void centerView();

}
