package propra22.q8493367.controllers;



import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import propra22.q8493367.entities.Diameter;
import propra22.q8493367.entities.DiameterAndQuadrangleCalculator;
import propra22.q8493367.entities.IHull;
import propra22.q8493367.entities.ITriangleCalculator;
import propra22.q8493367.entities.Point;
import propra22.q8493367.entities.PointSet;
import propra22.q8493367.entities.Quadrangle;
import propra22.q8493367.entities.QuadrangleSequence;
import propra22.q8493367.entities.TangentPair;
import propra22.q8493367.entities.Triangle;
import propra22.q8493367.gui.DrawPanel;
import propra22.q8493367.gui.GUISettings;
import propra22.q8493367.main.CHGO_8493367_Kuhn_Manuel;
import propra22.q8493367.usecases.AnimationThread;
import propra22.q8493367.usecases.CommandManager;
import propra22.q8493367.usecases.DragPointCommand;
import propra22.q8493367.usecases.ICommand;
import propra22.q8493367.usecases.InsertPointCommand;
import propra22.q8493367.usecases.InsertRandomPointsCommand;
import propra22.q8493367.usecases.RemovePointCommand;
import propra22.q8493367.util.IMetric;
import propra22.q8493367.util.ManhattanMetric;

/**
 * The controller of the draw panel. It handles the updates for the model
 * and the view based on various events.
 */
public class DrawPanelController implements IDrawPanelController {
	
	// View
	/**  The draw panel. */
	private IDrawPanel view;

	
	//Model
	/**  The point set. */
	private PointSet pointSet;

	/** First the contour polygon, then the convex hull. */
	private IHull hull;

	/** The diameter. */
	private Diameter diameter;

	/** The biggest quadrangle. */
	private Quadrangle quadrangle;
	
	/** The biggest triangle */
	private Triangle triangle;
	
	/** The pair of tangents as used by the animation. */
	private TangentPair tangentPair;
	
	/** The quadrangle sequence as used by the animation. */
	private QuadrangleSequence quadrangleSequence;
	
	/** 
	 * The closest point to the mouse pointer within a certain radius.
	 * */
	private Point selected = null;
	
	
    // Calculation
	/**   The calculator for the diameter, the quadrangle and the quadrangle sequence. */
	private DiameterAndQuadrangleCalculator diameterAndQuadrangleCalulator;
	
	/** The calculator for the biggest triangle */
	private ITriangleCalculator triangleCalculator;

	
	
	// Animation
	/**  The thread for the animation. */
	private AnimationThread animationThread;
	
	
	
	// Dragging a point
	/**  The previous x coordinate of the mouse pointer as used for dragging a point. */
	private int previousMouseX;

	/**  The previous y coordinate of the mouse pointer as used for dragging a point. */
	private int previousMouseY;

	/** The x coordinate of the mouse pointer when the dragging starts. */
	private int startMouseX;

	/**  The y coordinate of the mouse pointer when the dragging starts. */
	private int startMouseY;

	
	
	// Commands
	/**  The command manager is responsible for the undo and redo
	 * functionality. 
	 * */
	private CommandManager commandManager = new CommandManager();

	
	// Mouse pointer
	/** True if the mouse pointer is above the draw panel,
	 *  false otherwise. 
	 */
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
	/**  The observers of the draw panel controller. In this application there is one observer. It is 
	 * the controller of the status bar.
	 * */
	private List<IDrawPanelControllerObserver> observers = new ArrayList<>();
    
	
	
	/**
	 * Instantiates a new draw panel controller.
	 *
	 * @param pointSet the point set
	 * @param convexHull the convex hull
	 * @param diameter the diameter
	 * @param quadrangle the biggest quadrangle
	 * @param triangle the biggest triangle
	 * @param tangentPair the tangent pair needed for the animation
	 * @param quadrangleSequence the sequence of quadrangles needed for the animation
	 * @param diameterAndQuadrangleCalculator the calculator for the diameter, the biggest quadrangle and the quadrangle sequence
	 * @param triangleCalculator the calculator for the biggest triangle
	 * @param view the view
	 */
	public DrawPanelController(PointSet pointSet, IHull convexHull, Diameter diameter, Quadrangle quadrangle, Triangle triangle,
			TangentPair tangentPair, QuadrangleSequence quadrangleSequence, DiameterAndQuadrangleCalculator diameterAndQuadrangleCalculator, 
			ITriangleCalculator triangleCalculator, DrawPanel view) {
		this.pointSet = pointSet;
		this.hull = convexHull;
		this.diameter = diameter;
		this.quadrangle = quadrangle;
		this.triangle = triangle;
		this.tangentPair = tangentPair;
		this.quadrangleSequence = quadrangleSequence;
		
		this.diameterAndQuadrangleCalulator = diameterAndQuadrangleCalculator;
		this.triangleCalculator = triangleCalculator;
		
		this.view = view;
		
		this.diameterAndQuadrangleCalulator = new DiameterAndQuadrangleCalculator(convexHull);
		
	}

	/**
	 * Instantiates a new draw panel controller which only takes a model. Used for
	 * calculating the data without displaying it.
	 *
	 * @param pointSet the draw panel model
	 * @param hull the convex hull
	 * @param diameter the diameter
	 * @param quadrangle the quadrangle
	 * @param triangle the biggest triangle
	 * @param quadrangleSequence the quadrangle sequence
	 * @param diameterAndQuadrangleCalculator the calculator for the diameter, the biggest quadrangle and the quadrangle sequence
	 * @param triangleCalculator the calculator for the biggest triangle
	 */
	public DrawPanelController(PointSet pointSet, IHull hull, Diameter diameter, Quadrangle quadrangle, Triangle triangle,
			QuadrangleSequence quadrangleSequence, DiameterAndQuadrangleCalculator diameterAndQuadrangleCalculator, 
			ITriangleCalculator triangleCalculator) {

		this.pointSet = pointSet;
		this.hull = hull;
		this.diameter = diameter;
		this.quadrangle = quadrangle;
		this.triangle = triangle;
		this.quadrangleSequence = quadrangleSequence;
		this.view = null;
		
		this.diameterAndQuadrangleCalulator = diameterAndQuadrangleCalculator;
		this.triangleCalculator = triangleCalculator;
	}
	
	/**
	 * Adds a point into the point set if
	 * the point is not already in the point set.
	 */
	
	@Override
	public void insertPointToPointSetCheckedWithSorting(int x, int y) {
		pointSet.addCheckedWithSorting(new Point(x, y));
		
	}
    
	
	@Override
	public void sortAndCheckPoints() {
		pointSet.sortAndCheck();
		
	}

	/**
	 * Determines if a point is within a certain radius to the mouse pointer. All
	 * coordinates refer to the coordinate system of the model.
	 *
	 * @param point  the point to be examined.
	 * @param mouseX the x coordinate of the mouse pointer.
	 * @param mouseY the y coordinate of the mouse pointer.
	 * @param metric the metric, which is used to determine the distance.
	 * @param radius the radius which is compared with the distance.
	 * @return true, if the point is within the given radius of the mouse pointer,
	 * false otherwise.
	 */
	private boolean pointIsWithinMouseRadius(Point point, int mouseX, int mouseY, 
			IMetric metric, double radius) {
		return metric.distance(point.getX(), point.getY(), mouseX, mouseY) <= radius;
	}

	/**
	 * Gets the closest point to mouse pointer. Returns null if there is no point on
	 * the draw panel. All coordinates refer to the coordinate system of the model.
	 *
	 * @param mouseX the x coordinate of the mouse pointer.
	 * @param mouseY the y coordinate of the mouse pointer.
	 * @param metric the metric which is used to determine the distance from
	 * the mouse pointer to a point.
	 * @return the closest point to mouse pointer.
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
	 * Updates the draw panel.
	 */
	@Override
	public void updateView() {
		view.update();
	}
	
	
	/**
	 *{@inheritDoc}
	 *
	 * This means, the point set is sorted,
	 * the contour polygon is calculated, the convex hull is calculated, 
	 * the diameter, the biggest quadrangle and the quadrangle sequence are calculated, 
	 * the biggest triangle is calculated,
	 * the animation thread is updated and the observers are notified.
	 * It also can print the respective duration of the calculations to the console. 
	 */
	@Override
	public void updateModel() {
	
		long start = System.currentTimeMillis();
		hull.set(pointSet);
		long end = System.currentTimeMillis();
		if(CHGO_8493367_Kuhn_Manuel.showConsoleOutput)
			System.out.println("Konturpolygon berechnen: " + (end - start) + " ms");

		start = end;
		hull.clean();
		end = System.currentTimeMillis();
		if(CHGO_8493367_Kuhn_Manuel.showConsoleOutput)
			System.out.println("Konvexe Hülle berechnen: " + (end - start) + " ms");
        
		// Terminates the animation thread so that the quadrangle sequence can be updated.
		if (view != null) {terminateAnimationThread();}

		start = end;
		diameterAndQuadrangleCalulator.calculate(diameter, quadrangle, quadrangleSequence);
		end = System.currentTimeMillis();
		if(CHGO_8493367_Kuhn_Manuel.showConsoleOutput)
			System.out.println("Durchmesser und Viereck berechen: " + (end - start) + " ms \n \n");
		
		start = end;
		triangleCalculator.calculate(triangle);
		end = System.currentTimeMillis();
		if(CHGO_8493367_Kuhn_Manuel.showConsoleOutput)
			System.out.println("Dreieck berechnen: " + (end - start) + " ms \n \n");
		
		// Updates the animation thread after the update of the quadrangle sequence.
		if (view != null) {updateAnimationThread();}
        
		// In this application the controller of the status bar is notified.
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
	 * Updates the animation thread by searching
	 * an antipodal pair, which fits to the current
	 * angle of the tangent pair.
	 */
	private void updateAnimationThread() {

		if (view.animationIsShown() && !pointSet.isEmpty()) {
			tangentPair.fitToAngle();
			animationThread = new AnimationThread(tangentPair, view);
			animationThread.start();
		}
	}

	
	/**
	 * {@inheritDoc}
	 * 
	 * This method is used, if a new point set
	 * is loaded from a file or if a new draw
	 * panel is created.
	 */
	@Override
	public void reset() {
		commandManager.clear();
		updateModel();
		centerView();
	}


	/**
	 * Creates a new draw panel by
	 * clearing the point set and 
	 * updating the draw panel
	 * afterwards.
	 */
	@Override
	public void createNewDrawPanel() {
		clearModel();
		updateView();
	}

	
	/**
	 * {@inheritDoc}
	 * It also sets the point set to
	 * 'has not changed'.
	 */
	@Override
	public void clearModel() {
		pointSet.clear();
		pointSet.setHasChanged(false);
	}

	
	
	@Override
	public boolean pointSetIsEmpty() {
		return pointSet.isEmpty();
	}


	
	@Override
	public void undoCommand() {
		if(commandManager.hasUndoableCommands()) {
			commandManager.undoCommand();
			updateModel();
			updateView();
		}	
	}
	

	
	@Override
	public void redoCommand() {
		if(commandManager.hasRedoableCommands()) {
			commandManager.redoCommand();
			updateModel();
			updateView();
		}	
	}
	
	
	
	@Override
	public boolean undoIsEnabled() {
		return commandManager.hasUndoableCommands();
	}
	

	
	@Override
	public boolean redoIsEnabled() {
		return commandManager.hasRedoableCommands();
	}
    
	
	/**
	 * {@inheritDoc}
	 * 
	 * If the method fails to insert the randomly generated points
	 * into the area, because there is not enough space, no points are inserted into
	 * the point set. All coordinates refer to the coordinate system of the model.
	 *
	 * @param number the number of randomly generated points.
	 * @param minX the smallest possible x coordinate.
	 * @param minY the smallest possible y coordinate.
	 * @param maxX the biggest possible x coordinate.
	 * @param maxY the biggest possible y coordinate.
	 */
	@Override
	public void insertRandomPoints(int number, int minX, int minY, int maxX, int maxY) {
		List<Point> points = new ArrayList<>();
		Random random = new Random();
		int i = 0;
		// There must be some space for the points
		if (maxX - minX > 0 && maxY - minY > 0) {
			for (i = 0; i < number; i++) {
                //number * 10 attempts to find a point which is not already in the point set
				Point point = tryPoint(number * 10, minX, minY, maxX, maxY, random);
				
				// All attempts have failed
				if (point == null) {
					break;
				}
				// Add the point to the list for the command
				points.add(point);
				
				/*
				 * We know for sure that this point is not in the the point set. So we can add it
				 * without checking again.
				 */
				pointSet.addUncheckedWithSorting(point);
				
			}
		}
		
		if(i == number) 
		{
			ICommand insertRandomPointsCommand = new InsertRandomPointsCommand(points, pointSet);
			commandManager.add(insertRandomPointsCommand);
			
		
			// No execution of the command needed since points are already in the point set. 
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
	 * All coordinates refer to the coordinate system of the model.
	 *
	 * @param numberOfAttempts the number of attempts to find that point.
	 * @param minX the smallest possible x coordinate
	 * @param minY the smallest possible y coordinate 
	 * @param maxX the biggest possible x coordinate 
	 * @param maxY the biggest possible y coordinate
	 * @param random the generator which generates the random points.
	 * @return the point that was generated. Null if no point could be generated.
	 */
	private Point tryPoint(int numberOfAttempts, int minX, int minY, int maxX, int maxY, Random random) {
		Point point;
		int attempt = 1;
		do {
			int x = random.nextInt(maxX - minX) + minX;
			int y = random.nextInt(maxY - minY) + minY;
			point = new Point(x, y);
		}while(pointSet.hasPoint(point) >= 0  && ++attempt <= numberOfAttempts);
		
		/* return null if all attempts were not enough to create a new randomly 
		generated point. */
		return attempt > numberOfAttempts ? null : point;
	}

	
	/**
	 * {@inheritDoc}
	 * If there is  already another point in the point set with the same coordinates, 
	 * the point is not inserted and no command is inserted into the command list.
	 * The coordinates refer to the coordinate system of the model.
	 *
	 * @param x the x coordinate of the point
	 * @param y the y coordinate of the point
	 */
	@Override
	public void insertPointToPointSetByCommand(int x, int y) {
		Point point = new Point(x, y);
		//If the point is not in the point set, create command and continue.
		if(pointSet.hasPoint(point) < 0) {
			ICommand insertPointCommand = new InsertPointCommand(point, pointSet);
			commandManager.add(insertPointCommand);
		    insertPointCommand.execute();
			pointSet.setHasChanged(true);
			
			/* There might be a point very close to the new point. 
			This point could be selected. */
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
	 * {@inheritDoc}
	 * Updates the model and the view afterwards. 
	 * The coordinates refer to the coordinate system of the model.
	 *
	 * @param mouseX the x coordinate of the mouse.
	 * @param mouseY the y coordinate of the mouse.
	 * @param totalScale needed for the translation of distances from the model 
	 * coordinate system into the view coordinate system. It is the scale of the representation in
	 * the draw panel.
	 * 
	 */
	@Override
	public void deletePointFromPointSetByCommand(int mouseX, int mouseY, double totalScale) {
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
	 * Initializes a point drag. It sets the mouse pointer coordinates
	 * of the starting position. It also sets the previous mouse pointer position which in the moment of 
	 * starting the drag has the same coordinates as the starting position.
	 * The coordinates refer to the coordinate system of the model.
	 *
	 * @param mouseX the x coordinate of the mouse.
	 * @param mouseY the y coordinate of the mouse.
	 */
	@Override
	public void initializePointDrag(int mouseX, int mouseY) {
		if (selected != null) {
			startMouseX = mouseX;
			startMouseY = mouseY;
			previousMouseX = mouseX;
			previousMouseY = mouseY;
		}
	}

	
	/**
	 * {@inheritDoc}
	 * This method adds the difference of the current mouse
	 * pointer position to the previous mouse pointer position
	 * to the point coordinates.
	 * The coordinates refer to the coordinate system of the model.
	 * @param mouseX the x coordinate of the mouse pointer.
	 * @param mouseY the y coordinate of the mouse pointer.
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
	 * Terminates a point drag.
	 * The command is added to the command list
	 * without being executed, because the point has already its new 
	 * position. If another point with the same coordinates as the 
	 * dragged point exists in the poinst set, this point is removed
	 * from the point set and kept in the command for the purpose
	 * of restoring it in the point set if the command is undone.
	 * The coordinates refer to the coordinate system of the model.
	 *
	 * @param mouseX the x coordinate of the mouse pointer.
	 * @param mouseY the y coordinate of the mouse pointer..
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
            
			// Inserts the dragged point again
			pointSet.addCheckedWithSorting(selected);

			// Create command and put it into the command list
			ICommand dragPointCommand = new DragPointCommand(dx, dy, selected, removedPoint, pointSet);
			commandManager.add(dragPointCommand);

			// Various updates
			pointSet.setHasChanged(true);
			updateModel();
			updateView();
		}
	}

	
	
	
	
	
	@Override
	public void addObserver(IDrawPanelControllerObserver observer) {
		this.observers.add(observer);
	}
	

	
	@Override
	public void removeObserver(IDrawPanelControllerObserver observer) {
		this.observers.remove(observer);
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * In this application the controller of
	 * the status bar gets the informations, which are displayed 
	 * in the status bar.
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
	 * Returns the diameter.
	 *
	 * @return the diameter
	 */
	@Override
	public Diameter getDiameter() {
		return this.diameter;
	}

	
	/**
	 * Returns the diameter length.
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
	 * Returns the biggest quadrangle.
	 *
	 * @return the biggest quadrangle
	 */
	@Override
	public Quadrangle getBiggestQuadrangle() {
		return quadrangle;
	}
    
	
	
	/**
	 * {@inheritDoc}
	 *
	 * @return the n x 2 array of integers which contains the coordinate of all points of the hull 
	 * moving clockwise along the hull. We refer to a standard cartesian coordinate system.
	 */
	@Override
	public int[][] getHullAsArray() {
		return hull.toArray();
	}
	
	
	
	
	/**
	 * Sets the coordinates of the mouse pointer.
	 * The coordinates refer to the coordinate system of the model.
	 *
	 * @param mouseX the x coordinate of the mouse pointer.
	 * @param mouseY the y coordinate of the mouse pointer.
	 */
	private void setMouseCoordinates(int mouseX, int mouseY) {
		this.mouseX = mouseX;
		this.mouseY = mouseY;
	}

	
	@Override
	public void setMousePositionIsOverPanel(boolean b) {
		mousePositionIsOverPanel = b;
		notifyObservers();	
	}

	/**
	 * Sets the selected point.
	 *
	 * @param totalScale  needed for the translation of distances from the model 
	 * coordinate system into the view coordinate system. It is the scale of the representation in
	 * the draw panel.
	 * In this case it is used to determine the selected point because the mouse radius refers to 
	 * the coordinate system of the view. {@link GUISettings#mouseRadius}
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
	 * {@inheritDoc}
	 *
	 * This method updates the position of the mouse pointer, sets the selected point and 
	 * updates all observers i.e. the controller of the status bar. The coordinates refer to the 
	 * coordinate system of the model.
	 * @param mouseX the x coordinate of the mouse pointer.
	 * @param mouseY the y coordinate of the mouse pointer.
	 * @param totalScale needed for the translation of distances from the model 
	 * coordinate system into the view coordinate system. It is the scale of the representation in
	 * the draw panel.
	 */
	@Override
	public void updateMouseData(int mouseX, int mouseY, double totalScale) {
		setMouseCoordinates(mouseX, mouseY);
		setSelectedPoint(totalScale);
		notifyObservers();
	}

	
	@Override
	public void centerView() {
		view.center();
		
	}
	
	@Override
	public boolean getConvexHullIsshown() {
		return view.convexHullIsShown();
	}
	

	@Override
	public boolean getDiameterIsShown() {
		return view.diameterIsShown();
	}
   
	@Override
	public boolean getTriangleIsShown() {
		return view.triangleIsShown();
	}
	
	@Override
	public boolean getQuadrangleIsShown() {
		return view.quadrangleIsShown();
	}

	@Override
	public boolean getAnimationIsShown() {
		return view.animationIsShown();
	}
	

	@Override
	public void setShowConvexHull(boolean b) {
		view.setConvexHullIsShown(b);

	}

	@Override
	public void setShowDiameter(boolean b) {
		view.setDiameterIsShown(b);

	}
	
	
	@Override
	public void setShowQuadrangle(boolean b) {
		view.setQuadrangleIsShown(b);

	}
	
	@Override
	public void setShowTriangle(boolean b) {
		view.setTriangleIsShown(b);
	}
	
	
	/**
	 * {@inheritDoc}
	 * 
	 * Creates a new animation thread if necessary, or terminates 
	 * a running animation thread, if the argument is false.
	 *
	 * @param animationRequested true if the animation is to
	 * be shown. False otherwise.
	 */
	@Override
	public void setShowAnimation(boolean animationRequested) {
		view.setShowAnimation(animationRequested);
		if(animationRequested == true) {
			if(animationThread == null || !animationThread.isAlive()) {
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
    
	
}
