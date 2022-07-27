package propra22.q8493367.draw.controller;




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
	 */
	//public void insertRandomPoints(int number, int x, int y, Dimension dimension);
	
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
	 * @param totalScale the total scale
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
	 * If the point set contains a point with the 
	 * same coordinates as the dragged point after
	 * dragging, this point is deleted from 
	 * the point set.
	 *
	 * @param mouseX the x coordinate of the mouse
	 * @param mouseY the y coordinate of the mouse
	 */
	void terminatePointDrag(int mouseX, int mouseY);


	/**
	 * Returns true, if the convex hull is shown, 
	 * false otherwise.
	 *
	 * @return true, if the user chose to display the 
	 * convex hull on the draw panel.
	 */
	boolean convexHullIsShown();
	
	/**
	 * Returns true, if the diameter is shown, 
	 * false otherwise.
	 *
	 * @return true, if the user chose to display the 
	 * diameter on the draw panel.
	 */
	boolean diameterIsShown();
	
	/**
	 * Returns true, if the quadrangle is shown, 
	 * false otherwise.
	 *
	 * @return true, if the user chose to display the 
	 * quadrangle on the draw panel.
	 */
	boolean quadrangleIsShown();
	
	/**
	 * Returns true, if the triangle is shown,
	 * false otherwise.
	 *
	 * @return true, if the user chose to display the 
	 * triangle on the draw panel.
	 */
	boolean triangleIsShown();
	
	/**
	 * Returns true, if the animation is shown,
	 * false otherwise.
	 *
	 * @return true, if successful
	 */
	boolean animationIsShown();
	
	/**
	 * Determines, if the convex hull
	 * should be shown.
	 *
	 * @param b  true, if the convex hull should 
	 * be shown. False otherwise.
	 */
	void setShowConvexHull(boolean b);
	
	/**
	 * Determines whether the diameter is to be shown.
	 *
	 * @param b true, if the diameter should 
	 * be shown. False otherwise.
	 */
	void setShowDiameter(boolean b);
	
	/**
	 * Determines whether the quadrangle is to be shown.
	 *
	 * @param b true, if the convex hull should 
	 * be shown. False otherwise.
	 */
	void setShowQuadrangle(boolean b);
	
	/**
	 * Determines whether the triangle is to be shown.
	 *
	 * @param b true, if the triangle should 
	 * be shown. False otherwise.
	 */
	void setShowTriangle(boolean b);

    
	
	/**
	 * Determines whether the animation is to be running.
	 *
	 * @param animationIsShown true if the animation should
	 * be shown.False otherwise
	 */
	void setShowAnimation(boolean animationIsShown);


	/**
	 * Insert random points.
	 *
	 * @param number the number of random points to be inserted
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
	 * Reset the draw panel controller.
	 * This method is used, if a new point set
	 * is loaded from a file or if a new draw
	 * panel is created.
	 */
	void reset();



	public boolean getConvexHullIsShown();



	public boolean getDiameterIsShown();



	public boolean getQuadrangleIsShown();



	public boolean getAnimationIsShown();


    public void updateMouseData(int mouseX, int mouseY, double totalScale);
	

	public void setMousePositionIsOverPanel(boolean b);



	public void centerView();



	

}
