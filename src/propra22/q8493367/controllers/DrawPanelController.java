package propra22.q8493367.controllers;



import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import propra22.q8493367.entities.Diameter;
import propra22.q8493367.entities.DiameterAndQuadrangleCalculator;
import propra22.q8493367.entities.IHull;
import propra22.q8493367.entities.IMetric;
import propra22.q8493367.entities.ManhattanMetric;
import propra22.q8493367.entities.Point;
import propra22.q8493367.entities.PointSet;
import propra22.q8493367.entities.Quadrangle;
import propra22.q8493367.entities.QuadrangleSequence;
import propra22.q8493367.entities.TangentPair;
import propra22.q8493367.gui.DrawPanel;
import propra22.q8493367.gui.GUISettings;
import propra22.q8493367.main.CHGO_8493367_Kuhn_Manuel;
import propra22.q8493367.usecases.CommandManager;
import propra22.q8493367.usecases.DragPointCommand;
import propra22.q8493367.usecases.ICommand;
import propra22.q8493367.usecases.InsertPointCommand;
import propra22.q8493367.usecases.InsertRandomPointsCommand;
import propra22.q8493367.usecases.RemovePointCommand;
import propra22.q8493367.util.IDrawPanelControllerObserver;


// TODO: Auto-generated Javadoc
/**
 * The controller of the draw panel. It also listens to all events on the draw
 * panel.
 */
public class DrawPanelController implements IDrawPanelController {
	
	// View
	/**  The view. */
	private IDrawPanel view;

	
	//Model
	/**   The point set. */
	private PointSet pointSet;

	/**  First the contour polygon, then the convex hull. */
	private IHull hull;

	/**  The diameter. */
	private Diameter diameter;

	/**  The quadrangle. */
	private Quadrangle quadrangle;
	
	/**  The pair of tangents used by the animation. */
	private TangentPair tangentPair;
	
	/** The quadrangle sequence as used by the animation. */
	private QuadrangleSequence quadrangleSequence;
	
	
	
	// Calculations
	/**  The contour polygon calculator. */
	//private ContourPolygonCalculator contourPolygonCalculator;

	/**  The convex hull calculator. */
	//private ConvexHullCalculator convexHullCalculator;

	/**  The calculator for the diameter, the quadrangle and the quadrangle sequence */
	private DiameterAndQuadrangleCalculator diameterAndQuadrangleCalulator;

	
	
	// Animation
	/**  The thread for the animation. */
	private AnimationThread animationThread;
	
	
	
	// Dragging points
	/**  The point which is selected for dragging. */
	private Point selected = null;

	/**  The previous x coordinate of the mouse. */
	private int previousMouseX;

	/**  The previous y coordinate of the mouse. */
	private int previousMouseY;

	/** The x coordinate of the mouse when the dragging started. */
	private int startMouseX;

	/**  The y coordinate of the mouse when the dragging ended. */
	private int startMouseY;

	
	
	// Commands
	/**  The command list. */
	private CommandManager commandManager = new CommandManager();

	
	// Mouse
	/**  True iff the mouse pointer is above the draw panel. */
	private boolean mousePositionIsOverPanel;
    
	/** The x coordinate of the mouse pointer if it is above the draw panel.
	 *  The last x coordinate before leaving the draw panel otherwise. 
	 */
	private int mouseX;
    
	/** The y coordinate of the mouse pointer if it is above the draw panel.
	 * The last y coordinate before leaving the draw panel otherwise.
	 *  */
	private int mouseY;
	
	
	
	// Observer
	/**  The observers. */
	private List<IDrawPanelControllerObserver> observers = new ArrayList<>();
    
	
	
	



	/**
	 * Instantiates a new draw panel controller.
	 *
	 * @param pointSet the point set
	 * @param convexHull the convex hull
	 * @param diameter the diameter
	 * @param quadrangle the quadrangle
	 * @param tangentPair the tangent pair needed for the animation
	 * @param quadrangleSequence the sequence of quadrangles needed for the animation
	 * @param view the view
	 */
	public DrawPanelController(PointSet pointSet, IHull convexHull, Diameter diameter, Quadrangle quadrangle, 
			TangentPair tangentPair, QuadrangleSequence quadrangleSequence, DrawPanel view) {
		this.pointSet = pointSet;
		this.hull = convexHull;
		this.diameter = diameter;
		this.quadrangle = quadrangle;
		this.tangentPair = tangentPair;
		this.quadrangleSequence = quadrangleSequence;
		
		this.view = view;
		
		//this.contourPolygonCalculator = new ContourPolygonCalculator(pointSet, convexHull);
		//this.convexHullCalculator = new ConvexHullCalculator(convexHull);
		this.diameterAndQuadrangleCalulator = new DiameterAndQuadrangleCalculator(convexHull);
		
	}

	/**
	 * Instantiates a new draw panel controller which only takes a model. Used for
	 * calculating the data without displaying it. In this application it is used for
	 * testing.
	 *
	 * @param pointSet the draw panel model
	 * @param hull the convex hull
	 * @param diameter the diameter
	 * @param quadrangle the quadrangle
	 * @param quadrangleSequence the quadrangle sequence
	 */
	public DrawPanelController(PointSet pointSet, IHull hull, Diameter diameter, Quadrangle quadrangle, 
			QuadrangleSequence quadrangleSequence) {

		this.pointSet = pointSet;
		this.hull = hull;
		this.diameter = diameter;
		this.quadrangle = quadrangle;
		this.quadrangleSequence = quadrangleSequence;
		this.view = null;
		
		//this.contourPolygonCalculator = new ContourPolygonCalculator(pointSet, hull);
		//this.convexHullCalculator = new ConvexHullCalculator(hull);
		this.diameterAndQuadrangleCalulator = new DiameterAndQuadrangleCalculator(hull);
	}

	/**
	 * Determines if a point is within a certain radius to the mouse pointer.
	 *
	 * @param point  the point
	 * @param mouseX the x coordinate of the mouse pointer
	 * @param mouseY the y coordinate of the mouse pointer
	 * @param metric the metric, which is used to determine the distance
	 * @param radius the radius
	 * @return true, the point is within the given radius of the mouse pointer,
	 * false otherwise.
	 */
	
	
	private boolean pointIsWithinMouseRadius(Point point, int mouseX, int mouseY, 
			IMetric metric, double radius) {
		return metric.distance(point.getX(), point.getY(), mouseX, mouseY) <= radius;
	}

	/**
	 * Gets the closest point to mouse pointer. Returns null if there is no point on
	 * the draw panel.
	 *
	 * @param mouseX the x coordinate of the mouse pointer
	 * @param mouseY the y coordinate of the mouse pointer
	 * @param metric the metric which is used to determine the distance
	 * @return the closest point to mouse pointer
	 */

	private Point getClosestPointToMouse(int mouseX, int mouseY, IMetric metric) {
		if (pointSet.isEmpty()) {
			return null;
		} else {
			Point closest = pointSet.getPointAt(0);
			for (int i = 1; i < pointSet.getNumberOfPoints(); i++) {
				Point other = pointSet.getPointAt(i);
				if (metric.distance(mouseX, mouseY, other.getX(), other.getY()) < metric.distance(mouseX, mouseY,
						closest.getX(), closest.getY())) {
					closest = other;
				}
			}
			return closest;
		}
	}

	
	/**
	 * Update view.
	 */
	@Override
	public void updateView() {
		view.update();
	}
	

	/**
	 * Update model.
	 */
	@Override
	public void updateModel() {
		long start = System.currentTimeMillis();
		pointSet.lexSort();
		long end = System.currentTimeMillis();
		if(CHGO_8493367_Kuhn_Manuel.showConsoleOutput)
			System.out.println("Punktmenge sortieren: " + (end - start) + " ms");

		start = end;
		hull.set(pointSet);
		end = System.currentTimeMillis();
		if(CHGO_8493367_Kuhn_Manuel.showConsoleOutput)
			System.out.println("Konturpolygon berechnen: " + (end - start) + " ms");

		start = end;
		hull.clean();
		end = System.currentTimeMillis();
		if(CHGO_8493367_Kuhn_Manuel.showConsoleOutput)
			System.out.println("Konvexe Hülle berechnen: " + (end - start) + " ms");
        
		//Terminates the animation thread so that the quadrangle sequence can be updated.
		if (view != null) {terminateAnimationThread();}

		start = end;
		diameterAndQuadrangleCalulator.calculate(diameter, quadrangle, quadrangleSequence);
		end = System.currentTimeMillis();
		if(CHGO_8493367_Kuhn_Manuel.showConsoleOutput)
			System.out.println("Durchmesser und Viereck berechen: " + (end - start) + " ms \n \n");
		
		//Updates the animation thread after the update of the quadrangle sequence.
		if (view != null) {updateAnimationThread();}

		notifyObservers();
	}
	

	/**
	 * Terminates the animation thread.
	 */
	private void terminateAnimationThread() {
		if(animationThread != null) {
			if(animationThread.isAlive()) {
				animationThread.terminate();
				try {
					animationThread.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}	
		}
	}
	
	/**
	 * Updates the animation thread.
	 */
	private void updateAnimationThread() {

		if (view.animationIsShown() && !pointSet.isEmpty()) {
			tangentPair.fitToAngle();
			animationThread = new AnimationThread(tangentPair, view);
			animationThread.start();
		}
	}

	
	
	/**
	 * Reset.
	 */
	@Override
	public void reset() {
		commandManager.clear();
		updateModel();
		centerView();
	}



	/**
	 * Creates the new draw panel.
	 */
	@Override
	public void createNewDrawPanel() {
		clearModel();
		updateView();
	}

	
	/**
	 * Clear model.
	 */
	@Override
	public void clearModel() {
		pointSet.clear();
		hull.clear();
		pointSet.setHasChanged(false);
	}

	
	/**
	 * Point set is empty.
	 *
	 * @return true, if successful
	 */
	@Override
	public boolean pointSetIsEmpty() {
		return pointSet.isEmpty();
	}


	
	/**
	 * Undo command.
	 */
	@Override
	public void undoCommand() {
		if(commandManager.hasUndoableCommands()) {
			commandManager.undoCommand();
			updateModel();
			updateView();
		}	
	}
	

	
	/**
	 * Redo command.
	 */
	@Override
	public void redoCommand() {
		if(commandManager.hasRedoableCommands()) {
			commandManager.redoCommand();
			updateModel();
			updateView();
		}	
	}
	

	/**
	 * Undo is enabled.
	 *
	 * @return true, if successful
	 */
	@Override
	public boolean undoIsEnabled() {
		return commandManager.hasUndoableCommands();
	}
	

	
	/**
	 * Redo is enabled.
	 *
	 * @return true, if successful
	 */
	@Override
	public boolean redoIsEnabled() {
		return commandManager.hasRedoableCommands();
	}
    
	
	/**
	 * Tries to insert a certain number of randomly generated points into the visible area
	 * of the draw panel.
	 *
	 * @param number the number of randomly generated points.
	 * @param minX the smallest possible x coordinate without the margin.
	 * @param minY the smallest possible y coordinate without the margin.
	 * @param maxX the biggest possible x coordinate without the margin.
	 * @param maxY the biggest possible y coordinate without the margin.
	 */
	@Override
	public void insertRandomPoints(int number, int minX, int minY, int maxX, int maxY) {
		List<Point> points = new ArrayList<>();
		Random random = new Random();
		int i = 0;
		// Only
		if (maxX - minX > 0 && maxY - minY > 0) {
			for (i = 0; i < number; i++) {

				Point point = tryPoint(number * 10, minX, minY, maxX, maxY, random);
				if (point == null) {
					break;
				}
				points.add(point);
				pointSet.addPoint(point);
			}
		}
		
		if(i == number) 
		{
			ICommand insertRandomPointsCommand = new InsertRandomPointsCommand(points, pointSet);
			commandManager.add(insertRandomPointsCommand);
			// No execute needed since points are already in the point set.
			pointSet.setHasChanged(true);
			
			updateModel();
			updateView();	
		} else {
			// Attempt has failed: Remove already generated points from the point set.
			for(Point point : points) {
				pointSet.removePoint(point);
			}
		}	
	}
	
	
	/**
	 * Tries to find a randomly generated point for the visible area of the draw panel.
	 *
	 * @param attempts the number of attempts.
	 * @param minX the smallest possible x coordinate without the additional margin which is added.
	 * @param minY the smallest possible y coordinate without the margin which is added.
	 * @param maxX the largest possible x coordinate without the margin which is subtracted.
	 * @param maxY the largest possible y coordinate without the margin which is subtracted.
	 * @param random the generator which generates the random points.
	 * @return the point that was generated. Null if no point could be generated.
	 */
	private Point tryPoint(int attempts, int minX, int minY, int maxX, int maxY, Random random) {
		Point point;
		int attempt = 1;
		do {
			int x = random.nextInt(maxX - minX) + minX;
			int y = random.nextInt(maxY - minY) + minY;
			point = new Point(x, y);
		}while(pointSet.hasPoint(point) >= 0  && ++attempt <= attempts);
		return attempt > attempts ? null : point;
	}

	
	/**
	 * Insert point to point set by user input.
	 *
	 * @param x the x
	 * @param y the y
	 */
	@Override
	public void insertPointToPointSetByUserInput(int x, int y) {
		Point point = new Point(x, y);
		//If the point is not in the point set, create command and continue.
		if(pointSet.hasPoint(point) < 0) {
			ICommand insertPointCommand = new InsertPointCommand(point, pointSet);
			commandManager.add(insertPointCommand);
		    insertPointCommand.execute();
			pointSet.setHasChanged(true);
			
			if(selected != null) {
				selected.setSelected(false);
			}
			point.setSelected(true);
			selected = point;
			
			updateModel();
			updateView();
		}	
	}

	/**
	 * Insert point to point set by file input.
	 *
	 * @param x the x
	 * @param y the y
	 */
	@Override
	public void insertPointToPointSetByFileInput(int x, int y) {
		pointSet.addPoint(new Point(x, y));
	}

	
	/**
	 * Delete point from point set by user input.
	 *
	 * @param mouseX the x coordinate of the mouse
	 * @param mouseY the y coordinate of the mouse
	 * @param totalScale the total scale
	 */
	@Override
	public void deletePointFromPointSetByUserInput(int mouseX, int mouseY, double totalScale) {
		if (selected != null) {
			if (pointIsWithinMouseRadius(selected, mouseX, mouseY, new ManhattanMetric(), GUISettings.mouseRadius/totalScale)) {
				ICommand removePointCommand = new RemovePointCommand(selected, pointSet);
				commandManager.add(removePointCommand);
				removePointCommand.execute();
				pointSet.setHasChanged(true);
				
				setSelectedPoint(totalScale);
				
				updateModel();
				updateView();	
			}
		}
	}

	
	/**
	 * Initializes a point drag.
	 *
	 * @param mouseX the x coordinate of the mouse in the coordinate system of the model.
	 * @param mouseY the y coordinate of the mouse in the coordinate system of the model.
	 * @param totalScale the product of scale and panel scale.
	 */
	@Override
	public void initializePointDrag(int mouseX, int mouseY, double totalScale) {
		if (selected != null) {
			startMouseX = mouseX;
			startMouseY = mouseY;
			previousMouseX = mouseX;
			previousMouseY = mouseY;
		}
	}

	
	/**
	 * Drag point.
	 *
	 * @param mouseX the mouse X
	 * @param mouseY the mouse Y
	 */
	@Override
	public void dragPoint(int mouseX, int mouseY) {
		if (selected != null) {
			int dx = mouseX - previousMouseX;
			int dy = mouseY - previousMouseY;

			selected.translate(dx, dy);
			pointSet.lexSort();

			previousMouseX = mouseX;
			previousMouseY = mouseY;

			updateModel();
			updateView();
		}
	}

	
	/**
	 * Terminate point drag.
	 *
	 * @param mouseX the mouse X
	 * @param mouseY the mouse Y
	 */
	@Override
	public void terminatePointDrag(int mouseX, int mouseY) {
		if (selected != null) {
			// Deltas of the mouse movement
			int dx = mouseX - startMouseX;
			int dy = mouseY - startMouseY;

			/* Remove dragged point from point set
			 * to check, if another point with 
			 * the same coordinates is contained
			 * by the point set
			 */
			pointSet.removePoint(selected);
			Point removedPoint = null;
			int pointIndex = pointSet.hasPoint(selected);
			
			/*If another point with the same coordinates
			 * exists, it is removed from the point set.
			 */
			if (pointIndex >= 0) {
				removedPoint = pointSet.getPointAt(pointIndex);
				pointSet.removePoint(removedPoint);
			}

			pointSet.addPoint(selected);

			// Create command and put it into the command list
			ICommand dragPointCommand = new DragPointCommand(dx, dy, selected, removedPoint, pointSet);
			commandManager.add(dragPointCommand);

			// Various updates
			pointSet.setHasChanged(true);
			updateModel();
			updateView();
		}
	}

	
	
	
	/**
	 * Sets the show triangle.
	 *
	 * @param b the new show triangle
	 */
	@Override
	public void setShowTriangle(boolean b) {
		view.setTriangleIsShown(b);
	}
	
	/**
	 * Sets the show animation.
	 *
	 * @param animationRequested the new show animation
	 */
	@Override
	public void setShowAnimation(boolean animationRequested) {
		view.setShowAnimation(animationRequested);
		if(animationRequested == true) {
			if(animationThread == null || (animationThread != null && !animationThread.isAlive())) {
				if(!hull.isEmpty()) {
					tangentPair.fitToAngle();
					animationThread = new AnimationThread(tangentPair, view);
					animationThread.start();
				}
				else {
					if(animationThread != null) {
						animationThread.terminate();
						try {
							animationThread.join();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}	
					}
				}
			}			
		}
		
		if(animationRequested == false) {
			if(animationThread != null) {
				try {
					animationThread.terminate();
					animationThread.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	/**
	 * Adds the observer.
	 *
	 * @param observer the observer
	 */
	@Override
	public void addObserver(IDrawPanelControllerObserver observer) {
		this.observers.add(observer);
	}
	

	/**
	 * Removes the observer.
	 *
	 * @param observer the observer
	 */
	@Override
	public void removeObserver(IDrawPanelControllerObserver observer) {
		this.observers.remove(observer);
	}
	
	/**
	 * Notify observers.
	 */
	@Override
	public void notifyObservers() {
		for(IDrawPanelControllerObserver observer : observers) {
			
			String mouseXasString = "";
			String mouseYasString = "";
			if(mousePositionIsOverPanel) {
				mouseXasString = String.valueOf(mouseX);
				mouseYasString = String.valueOf(mouseY);
			}
			
			String selectedXasString = "";
			String selectedYasString = "";
			if(selected != null) {
				selectedXasString = String.valueOf(selected.getX());
				selectedYasString = String.valueOf(selected.getY());
			}	
			
			observer.update(String.valueOf(pointSet.getNumberOfPoints()), mouseXasString, mouseYasString,
					selectedXasString, selectedYasString);
		}
	}

	

	
	/**
	 * Gets the diameter.
	 *
	 * @return the diameter
	 */
	@Override
	public Diameter getDiameter() {
		return this.diameter;
	}

	
	/**
	 * Gets the diameter length.
	 *
	 * @return the diameter length
	 */
	@Override
	public double getDiameterLength() {
		if (diameter != null) {
			return diameter.length();
		}
		return -1;
	}

	
	/**
	 * Gets the biggest quadrangle.
	 *
	 * @return the biggest quadrangle
	 */
	@Override
	public Quadrangle getBiggestQuadrangle() {
		return quadrangle;
	}

	/**
	 * Hull as array.
	 *
	 * @return the int[][]
	 */
	@Override
	public int[][] hullAsArray() {
		return hull.toArray();
	}
	
	
	
	
	/**
	 * Sets the coordinates of the mouse pointer.
	 *
	 * @param mouseX the x coordinate of the mouse pointer.
	 * @param mouseY the y coordinate of the mouse pointer.
	 */
	private void setMouseCoordinates(int mouseX, int mouseY) {
		this.mouseX = mouseX;
		this.mouseY = mouseY;
	}

	
	/**
	 * Sets the mouse position is over panel.
	 *
	 * @param b the new mouse position is over panel
	 */
	@Override
	public void setMousePositionIsOverPanel(boolean b) {
		mousePositionIsOverPanel = b;
		notifyObservers();	
	}

	/**
	 * Sets the selected point.
	 *
	 * @param totalScale scale * panelScale. This value is needed to calculate distances in
	 * the model. We need it to determine the selected point.
	 */
	private void setSelectedPoint(double totalScale) {
		if(mousePositionIsOverPanel) {
			Point closest = getClosestPointToMouse(mouseX, mouseY, new ManhattanMetric());
			if(closest != null) {
				if(pointIsWithinMouseRadius(closest, mouseX, mouseY, new ManhattanMetric(), GUISettings.mouseRadius/totalScale)) {
					if(selected != null) {
						if(selected != closest) {
							selected.setSelected(false);
							closest.setSelected(true);
							selected = closest;
							view.update();
						}
					}
					else {
						closest.setSelected(true);
						selected = closest;
						view.update();
					}
				}
				else {
					if(selected != null){
						selected.setSelected(false);
					}
					selected = null;
					view.update();
				}
			}
			else {
				if(selected != null){
					selected.setSelected(false);
				}
				selected = null;
				view.update();
			}
		}
	}

	
	/**
	 * Update mouse data.
	 *
	 * @param mouseX the mouse X
	 * @param mouseY the mouse Y
	 * @param totalScale the total scale
	 */
	@Override
	public void updateMouseData(int mouseX, int mouseY, double totalScale) {
		setMouseCoordinates(mouseX, mouseY);
		setSelectedPoint(totalScale);
		notifyObservers();
	}

	
	/**
	 * Center view.
	 */
	@Override
	public void centerView() {
		view.center();
		
	}
	
	/**
	 * Gets the convex hull isshown.
	 *
	 * @return the convex hull isshown
	 */
	@Override
	public boolean getConvexHullIsshown() {
		return view.convexHullIsShown();
	}
   

	
   
	/**
	 * Gets the triangle is shown.
	 *
	 * @return the triangle is shown
	 */
	@Override
	public boolean getTriangleIsShown() {
		return view.triangleIsShown();
	}
	

	

	/**
	 * Sets the show convex hull.
	 *
	 * @param b the new show convex hull
	 */
	@Override
	public void setShowConvexHull(boolean b) {
		view.setConvexHullIsShown(b);

	}

	/**
	 * Sets the show diameter.
	 *
	 * @param b the new show diameter
	 */
	@Override
	public void setShowDiameter(boolean b) {
		view.setDiameterIsShown(b);

	}

	/**
	 * Sets the show quadrangle.
	 *
	 * @param b the new show quadrangle
	 */
	@Override
	public void setShowQuadrangle(boolean b) {
		view.setQuadrangleIsShown(b);

	}

	
	/**
	 * Gets the diameter is shown.
	 *
	 * @return the diameter is shown
	 */
	@Override
	public boolean getDiameterIsShown() {
		return view.diameterIsShown();
	}

	
	/**
	 * Gets the quadrangle is shown.
	 *
	 * @return the quadrangle is shown
	 */
	@Override
	public boolean getQuadrangleIsShown() {
		return view.quadrangleIsShown();
	}

	/**
	 * Gets the animation is shown.
	 *
	 * @return the animation is shown
	 */
	@Override
	public boolean getAnimationIsShown() {
		return view.animationIsShown();
	}

}
