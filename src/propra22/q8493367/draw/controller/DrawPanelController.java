package propra22.q8493367.draw.controller;



import java.util.ArrayList;
import java.util.List;

import propra22.q8493367.animation.AnimationThread;
import propra22.q8493367.animation.QuadrangleSequence;
import propra22.q8493367.animation.TangentPair;
import propra22.q8493367.command.CommandManager;
import propra22.q8493367.command.DragPointCommand;
import propra22.q8493367.command.ICommand;
import propra22.q8493367.command.InsertPointCommand;
import propra22.q8493367.command.InsertRandomPointsCommand;
import propra22.q8493367.command.RemovePointCommand;
import propra22.q8493367.contour.ContourPolygonCalculator;
import propra22.q8493367.contour.ConvexHullCalculator;
import propra22.q8493367.contour.DiameterAndQuadrangleCalculator;
import propra22.q8493367.contour.IDiameter;
import propra22.q8493367.contour.IHull;
import propra22.q8493367.contour.IPointSet;
import propra22.q8493367.contour.IQuadrangle;
import propra22.q8493367.draw.view.DrawPanel;
import propra22.q8493367.draw.view.IDrawPanel;
import propra22.q8493367.metric.IMetric;
import propra22.q8493367.metric.ManhattanMetric;
import propra22.q8493367.point.IPoint;
import propra22.q8493367.point.Point;
import propra22.q8493367.settings.Settings;

// TODO: Auto-generated Javadoc
/**
 * The controller of the draw panel. It also listens to all events on the draw
 * panel.
 */
public class DrawPanelController implements IDrawPanelController {
	// View
	/** The view */
	private IDrawPanel view;

	//Model
	/**  The point set */
	private IPointSet pointSet;

	
	// Shapes
	/**  The hull */
	private IHull hull;

	/**  The diameter */
	private IDiameter diameter;

	/**  The quadrangle */
	private IQuadrangle quadrangle;
	
	/**  The pair of tangents for the animation. */
	//private TangentPair tangentPair;

	// Dragging points
	/** The point which is selected for dragging */
	private IPoint selected = null;

	/**  The previous x coordinate of the mouse */
	private int previousMouseX;

	/** The previous y coordinate of the mouse */
	private int previousMouseY;

	/**
	 * The x coordinate of the mouse when the dragging started
	 */
	private int startMouseX;

	/** The y coordinate of the mouse when the dragging ended */
	private int startMouseY;

	// Commands
	/** The command list */
	private CommandManager commandManager = new CommandManager();

	// Calculations
	/** The contour polygon calculator */
	private ContourPolygonCalculator contourPolygonCalculator;

	/** The convex hull calculator */
	private ConvexHullCalculator convexHullCalculator;

	/** The calculator for the diameter */
	private DiameterAndQuadrangleCalculator diameterAndQuadrangleCalulator;
	
	/** The thread for the animation */
	private AnimationThread animationThread;
	
	private TangentPair tangentPair;
	
	/** The observers */
	private List<IDrawPanelControllerObserver> observers = new ArrayList<>();

	private boolean mousePositionIsOverPanel;

	private int mouseX;

	private int mouseY;

	private QuadrangleSequence quadrangleSequence;
	



	/**
	 * Instantiates a new draw panel controller.
	 *
	 * @param pointSet the point set
	 * @param convexHull the convex hull
	 * @param diameter the diameter
	 * @param quadrangle the quadrangle
	 * @param tangentPair the tangent pair
	 * @param drawPanel the draw panel
	 */
	public DrawPanelController(IPointSet pointSet, IHull convexHull, IDiameter diameter, IQuadrangle quadrangle, 
			TangentPair tangentPair, QuadrangleSequence quadrangleSequence, DrawPanel drawPanel) {
		this.pointSet = pointSet;
		this.hull = convexHull;
		this.diameter = diameter;
		this.quadrangle = quadrangle;
		this.tangentPair = tangentPair;
		this.quadrangleSequence = quadrangleSequence;
		
		this.view = drawPanel;
		
		this.contourPolygonCalculator = new ContourPolygonCalculator(pointSet, convexHull);
		this.convexHullCalculator = new ConvexHullCalculator(convexHull);
		this.diameterAndQuadrangleCalulator = new DiameterAndQuadrangleCalculator(convexHull);
		
	}

	/**
	 * Instantiates a new draw panel controller which only takes a model. Used for
	 * testing or calculating the data without displaying it.
	 *
	 * @param pointSet the draw panel model
	 * @param hull the convex hull
	 * @param diameter the diameter
	 * @param quadrangle the quadrangle
	 */
	public DrawPanelController(IPointSet pointSet, IHull hull, IDiameter diameter, IQuadrangle quadrangle, 
			QuadrangleSequence quadrangleSequence) {

		this.pointSet = pointSet;
		this.hull = hull;
		this.diameter = diameter;
		this.quadrangle = quadrangle;
		this.quadrangleSequence = quadrangleSequence;
		this.view = null;
		
		
		this.contourPolygonCalculator = new ContourPolygonCalculator(pointSet, hull);
		this.convexHullCalculator = new ConvexHullCalculator(hull);
		this.diameterAndQuadrangleCalulator = new DiameterAndQuadrangleCalculator(hull);
	}

	/**
	 * Determines, if a point is within a certain radius to the mouse pointer.
	 *
	 * @param point  - the point
	 * @param mouseX - the x coodinate of the mouse pointer
	 * @param mouseY - the y coordinate of the mouse pointer
	 * @param norm   - the norm, which is used to determine the distance
	 * @param radius - the radius
	 * @return true, if successful
	 */
	
	
	private boolean pointIsWithinMouseRadius(IPoint point, int mouseX, int mouseY, IMetric metric, double radius) {
		return metric.distance(point.getX(), point.getY(), mouseX, mouseY) <= radius;
	}

	/**
	 * Gets the closest point to mouse pointer. Returns null if there is no point on
	 * the draw panel.
	 *
	 * @param mouseX - the x coordinate of the mouse pointer
	 * @param mouseY - the y coordinate of the mouse pointer
	 * @param metric - the metric which is used to determine the distance
	 * @return the closest point to mouse pointer
	 */

	private IPoint getClosestPointToMouse(int mouseX, int mouseY, IMetric metric) {
		if (pointSet.isEmpty()) {
			return null;
		} else {
			IPoint closest = pointSet.getPointAt(0);
			for (int i = 1; i < pointSet.getNumberOfPoints(); i++) {
				IPoint other = pointSet.getPointAt(i);
				if (metric.distance(mouseX, mouseY, other.getX(), other.getY()) < metric.distance(mouseX, mouseY,
						closest.getX(), closest.getY())) {
					closest = other;
				}
			}
			return closest;
		}
	}

	/**
	 * Updates the the draw panel.
	 */
	public void updateView() {
		view.update();
	}
	
	// TODO es wird nicht nur das Model upgedated -> Kein guter Name

	/**
	 * Updates the draw panel model.
	 */
	public void updateModel() {
		if (!pointSet.isEmpty()) {
			long start = System.currentTimeMillis();
			pointSet.lexSort();
			long end = System.currentTimeMillis();
			System.out.println("Punktmenge sortieren: " + (end - start) + " ms");
			
			start = end;
			contourPolygonCalculator.calculateContourPolygon();
			end = System.currentTimeMillis();
			System.out.println("Konturpolygon berechnen: " + (end - start) + " ms");

			start = end;
			convexHullCalculator.calculateConvexHull();
			end = System.currentTimeMillis();
			System.out.println("Konvexe Hülle berechnen: " + (end - start) + " ms");
             
			terminateAnimationThread();
			
			start = end;
			diameterAndQuadrangleCalulator.calculate(diameter, quadrangle, quadrangleSequence);
			end = System.currentTimeMillis();
			System.out.println("Durchmesser und Viereck berechen: " + (end - start) + " ms \n \n");
			
			
		}
		
		if(view != null) {
			updateAnimation();
		}
		
		notifyObservers();
	}

	private void terminateAnimationThread() {
		if(animationThread != null) {
			if(animationThread.isAlive()) {
				System.out.println("Beende Animations-Thread");
				animationThread.terminate();
				try {
					animationThread.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}	
		}
	}
	
	private void updateAnimation() {

		if (view.animationIsShown()) {
			tangentPair.fitToAngle();
			animationThread = new AnimationThread(tangentPair, view);
			System.out.println("Starte Animations-Thread");
			animationThread.start();
		}

	}

	/**
	 * Updates the model and the view
	 */
	public void update() {
		updateModel();
		updateView();
	}
	
	/**
	  *{@inheritDoc}
	 */
	@Override
	public void reset() {
		commandManager.clear();
		updateModel();
		initializeView();	
	}



	/**
	 *{@inheritDoc}
	 */
	@Override
	public void createNewDrawPanel() {
		clearModel();
		updateView();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clearModel() {
		pointSet.clear();
		hull.clear();
		pointSet.setHasChanged(false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean pointSetIsEmpty() {
		return pointSet.isEmpty();
	}


	/**
	 * {@inheritDoc}
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
	 * {@inheritDoc}
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
	 *{@inheritDoc}
	 */
	@Override
	public boolean undoIsEnabled() {
		return commandManager.hasUndoableCommands();
	}
	

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public boolean redoIsEnabled() {
		return commandManager.hasRedoableCommands();
	}
    
	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void insertRandomPoints(int number, int minX, int minY, int maxX, int maxY) {
	    
		ICommand insertRandomPointsCommand = new InsertRandomPointsCommand(number, minX, minY, maxX, maxY, pointSet);
		commandManager.add(insertRandomPointsCommand);
		insertRandomPointsCommand.execute();
		pointSet.setHasChanged(true);
		
		//in case the mouse pointer is close to a randomly generated point
		
		updateModel();
		updateView();	
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void insertPointToPointSetByUserInput(int x, int y) {
		IPoint point = new Point(x, y);
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
	 *{@inheritDoc}
	 */
	@Override
	public void insertPointToPointSetByFileInput(int x, int y) {
		pointSet.addPoint(new Point(x, y));
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public void deletePointFromPointSetByUserInput(int mouseX, int mouseY, double totalScale) {
		if (selected != null) {
			if (pointIsWithinMouseRadius(selected, mouseX, mouseY, new ManhattanMetric(), Settings.mouseRadius/totalScale)) {
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
	 *{@inheritDoc}
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
	 *{@inheritDoc}
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

			/* TODO: check if point is within convex hull 
			 * -> no new convex hull calculation needed
			 */
			updateModel();
			updateView();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void terminatePointDrag(int mouseX, int mouseY) {
		if (selected != null) {
			// Deltas of the mouse movement
			int dx = mouseX - startMouseX;
			int dy = mouseY - startMouseY;

			// Remove dragged point from point set
			pointSet.removePoint(selected);

			/*
			 * Check if another point with same coordinates as the dragged point exists.
			 * Remove the point if so.
			 */
			IPoint removedPoint = null;
			int pointIndex = pointSet.hasPoint(selected);
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
	 *{@inheritDoc}
	 */
	
	@Override
	public boolean convexHullIsShown() {
		return view.convexHullIsShown();

	}
   
	/**
	 *{@inheritDoc}
	 */
	@Override
	public boolean diameterIsShown() {
		return view.diameterIsShown();
	}
	

	/**
	 *{@inheritDoc}
	 */
	@Override
	public boolean quadrangleIsShown() {
		return view.quadrangleIsShown();
	}
   
	/**
	 *{@inheritDoc}
	 */
	@Override
	public boolean triangleIsShown() {
		return view.triangleIsShown();
	}
	
	/**
	 *{@inheritDoc}
	 */
	@Override
	public boolean animationIsShown() {
		return view.animationIsShown();
	}
	

	/**
	 *{@inheritDoc}
	 */
	@Override
	public void setShowConvexHull(boolean b) {
		view.setConvexHullIsShown(b);

	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public void setShowDiameter(boolean b) {
		view.setDiameterIsShown(b);

	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public void setShowQuadrangle(boolean b) {
		view.setQuadrangleIsShown(b);

	}
	
	/**
	 *{@inheritDoc}
	 */
	@Override
	public void setShowTriangle(boolean b) {
		view.setTriangleIsShown(b);
	}
	
	/**
	 *{@inheritDoc}
	 */
	@Override
	public void initializeView() {
		view.initialize();	
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public void setShowAnimation(boolean animationRequested) {
		view.setShowAnimation(animationRequested);
		if(animationRequested == true) {
			if(animationThread == null || (animationThread != null && !animationThread.isAlive())) {
				if(!hull.empty()) {
					//tangentPair.updateAntipodalPairs(hull);
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
	 *{@inheritDoc}
	 */
	@Override
	public void addObserver(IDrawPanelControllerObserver observer) {
		this.observers.add(observer);
	}
	
	/**
	 *{@inheritDoc}
	 */
	@Override
	public void removeObserver(IDrawPanelControllerObserver observer) {
		this.observers.remove(observer);
	}
	
	/**
	 *{@inheritDoc}
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

	
	
	
	// for testing only

	/**
	 * Gets the diameter.
	 * 
	 * @return the diameter
	 */
	public IDiameter getDiameter() {
		return this.diameter;
	}

	/**
	 * Gets the length of the diameter.
	 * 
	 * @return the diameter length in pixels
	 */
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
	public IQuadrangle getBiggestQuadrangle() {
		return quadrangle;
	}

	/**
	 * Returns the hull as an array.
	 * 
	 * @return the nX2 int array which contains the coordinate of all points of the hull 
	 * moving counterclockwise along the hull.
	 */

	public int[][] hullAsArray() {
		return hull.toArray();
	}

	@Override
	public boolean getConvexHullIsShown() {
		return view.convexHullIsShown();
	}

	@Override
	public boolean getDiameterIsShown() {
		return view.diameterIsShown();
	}

	@Override
	public boolean getQuadrangleIsShown() {
		return view.quadrangleIsShown();
	}

	@Override
	public boolean getAnimationIsShown() {
		return view.animationIsShown();
	}

	
	private void setMouseCoordinates(int mouseX, int mouseY) {
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		//notifyObservers();
	}

	@Override
	public void setMousePositionIsOverPanel(boolean b) {
		mousePositionIsOverPanel = b;
		notifyObservers();
		
	}

	private void setSelectedPoint(double totalScale) {
		if(mousePositionIsOverPanel) {
			IPoint closest = getClosestPointToMouse(mouseX, mouseY, new ManhattanMetric());
			if(closest != null) {
				if(pointIsWithinMouseRadius(closest, mouseX, mouseY, new ManhattanMetric(), Settings.mouseRadius/totalScale)) {
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

	@Override
	public void updateMouseData(int mouseX, int mouseY, double totalScale) {
		setMouseCoordinates(mouseX, mouseY);
		setSelectedPoint(totalScale);
		notifyObservers();
	}
}
