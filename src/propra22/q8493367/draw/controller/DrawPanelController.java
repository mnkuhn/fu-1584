package propra22.q8493367.draw.controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import propra22.q8493367.animation.ITangentPair;
import propra22.q8493367.command.DragPointCommand;
import propra22.q8493367.command.ICommand;
import propra22.q8493367.command.InsertPointCommand;
import propra22.q8493367.command.InsertRandomPointsCommand;
import propra22.q8493367.command.RemovePointCommand;
import propra22.q8493367.contour.ContourPolygonCalculator;
import propra22.q8493367.contour.SectionType;
import propra22.q8493367.convex.DiameterAndQuadrangleCalculator;
import propra22.q8493367.convex.ConvexHullCalculator;
import propra22.q8493367.draw.model.Diameter;
import propra22.q8493367.draw.model.Hull;
import propra22.q8493367.draw.model.IDiameter;
import propra22.q8493367.draw.model.IHull;
import propra22.q8493367.draw.model.IPointSet;
import propra22.q8493367.draw.model.IQuadrangle;
import propra22.q8493367.draw.model.Quadrangle;
import propra22.q8493367.draw.view.DrawPanel;
import propra22.q8493367.draw.view.IDrawPanel;
import propra22.q8493367.metric.IMetric;
import propra22.q8493367.metric.ManhattanDistance;
import propra22.q8493367.point.IPoint;
import propra22.q8493367.point.Point;
import propra22.q8493367.settings.Settings;

/**
 * The controller of the draw panel. It also listens to all events on the draw
 * panel.
 */
public class DrawPanelController implements IDrawPanelController {
	// view
	/** The view. */
	private IDrawPanel view;

	// model
	/** The point set */
	private IPointSet pointSet;

	/** The hull */
	// should be an argument of the controller constructor
	private IHull hull;

	/** The diameter */
	private IDiameter diameter;

	/** The quadrangle */
	private IQuadrangle quadrangle;
	
	private ITangentPair tangentPair;

	/** The draw panel reference width to which all calculations refer. */
	private int drawPanelReferenceWidth = 800;

	/** The draw panel reference height to which all calculations refer. */
	private int drawPanelReferenceHeight = 500;

	// Dragging points
	/** The point which is selected for dragging. */
	private IPoint forDragSelected = null;

	/** The previous x coordinate of the mouse */
	private int previousMouseX;

	/** The previous y coordinate of the mouse. */
	private int previousMouseY;

	/**
	 * The x coordinate of the mouse when the dragging started.
	 */
	private int startMouseX;

	/**
	 * The y coordinate of the mouse when the dragging ended
	 */
	private int startMouseY;

	// commands
	/** The command list. */
	private List<ICommand> commandList = new ArrayList<>();

	/** The actual position in the command list */
	private int commandIndex = -1;

	// calculations
	/** The contour polygon calculator. */
	private ContourPolygonCalculator contourPolygonCalculator;

	/** The convex hull calculator. */
	private ConvexHullCalculator convexHullCalculator;

	/** The calculator for the diameter. */
	private DiameterAndQuadrangleCalculator diameterAndQuadrangleCalulator;
	
	
	



	/**
	 * Instantiates a new draw panel controller.
	 *
	 * @param drawPanelModel the draw panel model
	 * @param drawPanel      the draw panel
	 */
	public DrawPanelController(IPointSet drawPanelModel, IHull hull, IDiameter diameter, IQuadrangle quadrangle, ITangentPair tangentPair,  DrawPanel drawPanel) {
		this.pointSet = drawPanelModel;
		this.hull = hull;
		this.diameter = diameter;
		this.quadrangle = quadrangle;
		this.tangentPair = tangentPair;
		
		this.view = drawPanel;
		
		this.contourPolygonCalculator = new ContourPolygonCalculator(drawPanelModel, hull);
		this.convexHullCalculator = new ConvexHullCalculator(hull);
		this.diameterAndQuadrangleCalulator = new DiameterAndQuadrangleCalculator(hull);
		this.drawPanelReferenceWidth = drawPanel.getPreferredSize().width;
		this.drawPanelReferenceHeight = drawPanel.getPreferredSize().height;
	}

	/**
	 * Instantiates a new draw panel controller which only takes a model. Used for
	 * testing or calculating the data without displaying it.
	 *
	 * @param pointSet the draw panel model
	 * @param drawPanel 
	 * @param tangentPair 
	 */
	public DrawPanelController(IPointSet pointSet, IHull hull, IDiameter diameter, IQuadrangle quadrangle) {

		this.pointSet = pointSet;
		this.hull = hull;
		this.diameter = diameter;
		this.quadrangle = quadrangle;
		tangentPair = null;
		this.view = null;
		
		
		contourPolygonCalculator = new ContourPolygonCalculator(pointSet, hull);
		convexHullCalculator = new ConvexHullCalculator(hull);
		diameterAndQuadrangleCalulator = new DiameterAndQuadrangleCalculator(hull);
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
	
	
	//float müsste reichen
	private boolean pointIsWithinMouseRadius(IPoint point, int mouseX, int mouseY, IMetric norm, double radius) {
		return norm.distance(point.getX(), point.getY(), mouseX, mouseY) <= radius;
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

			start = end;
			diameterAndQuadrangleCalulator.calculate(diameter, quadrangle);
			end = System.currentTimeMillis();
			System.out.println("Durchmesser und Viereck berechen: " + (end - start) + " ms \n \n");
		}
	}

	/**
	 * Paints the points, the convex hull und was noch alles kommt on the draw
	 * panel.
	 *
	 * @param g the g
	 */
	
	/*
	@Override
	public void paintDrawPanel(Graphics g) {
		if (!pointSet.isEmpty()) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			drawPoints(g2);
			if (convexHullIsShown) {
				drawHull(g2, Settings.convexHullColor);
			}
			if (diameterIsShown) {
				drawDiameter(g2, Settings.diameterColor);
			}
			if (quadrangleIsShown) {
				drawQuadrangle(g2, Settings.quadrangleColor);
			}
			// if(triangleIsShown) {drawTriangle(g2, Settings.triangleColor);}
		}
	}
	
	*/

	/**
	 * Draws all points from the point set onto the draw panel.
	 *
	 * @param g2 - the Graphics2D Object which is used for painting
	 */
	
	/*
	private void drawPoints(Graphics2D g2) {

		for (int i = 0; i < pointSet.getNumberOfPoints(); i++) {
			IPoint p = pointSet.getPointAt(i);
			g2.fillOval(p.getX() - Settings.radius, p.getY() - Settings.radius, 2 * Settings.radius,
					2 * Settings.radius);
		}
	}
	*/

	/**
	 * Draws the convex hull.
	 *
	 * @param g2    - the Graphics2D Object which is used for painting
	 * @param color - the color for the hull
	 */
	/*
	private void drawHull(Graphics2D g2, Color color) {
		g2.setColor(color);

		for (SectionType sectionType : SectionType.values()) {
			int sectionSize = hull.getSizeOfSection(sectionType);
			if (sectionSize > 1) {
				for (int i = 0; i < sectionSize - 1; i++) {

					IPoint first = hull.getPointFromSection(i, sectionType);
					IPoint second = hull.getPointFromSection(i + 1, sectionType);
					g2.drawLine(first.getX(), first.getY(), second.getX(), second.getY());
				}
			}
			sectionSize = hull.getSizeOfSection(SectionType.LOWERLEFT);
			IPoint lastLeft = hull.getPointFromSection(sectionSize - 1, SectionType.LOWERLEFT);
			sectionSize = hull.getSizeOfSection(SectionType.LOWERRIGHT);
			IPoint lastRight = hull.getPointFromSection(sectionSize - 1, SectionType.LOWERRIGHT);
			g2.drawLine(lastLeft.getX(), lastLeft.getY(), lastRight.getX(), lastRight.getY());

			sectionSize = hull.getSizeOfSection(SectionType.UPPERLEFT);
			lastLeft = hull.getPointFromSection(sectionSize - 1, SectionType.UPPERLEFT);
			sectionSize = hull.getSizeOfSection(SectionType.UPPERRIGHT);
			lastRight = hull.getPointFromSection(sectionSize - 1, SectionType.UPPERRIGHT);
			g2.drawLine(lastLeft.getX(), lastLeft.getY(), lastRight.getX(), lastRight.getY());

		}
		g2.setColor(Color.BLACK);
	}
	*/

	/**
	 * Draw diameter.
	 *
	 * @param g2    the g 2
	 * @param color the color
	 */
	
	/*
	private void drawDiameter(Graphics2D g2, Color color) {
		if (diameter != null) {
			g2.setColor(color);
			IPoint a = diameter.getA();
			IPoint b = diameter.getB();
			g2.drawLine(a.getX(), a.getY(), b.getX(), b.getY());
			g2.setColor(Color.BLACK);
		}
	}
	*/

	/**
	 * Draw quadrangle.
	 *
	 * @param g2    the g 2
	 * @param color the color
	 */
	
	/*
	private void drawQuadrangle(Graphics2D g2, Color color) {
		if (quadrangle != null) {
			g2.setColor(color);

			IPoint a = quadrangle.getA();
			IPoint b = quadrangle.getB();
			IPoint c = quadrangle.getC();
			IPoint d = quadrangle.getD();

			g2.drawLine(a.getX(), a.getY(), b.getX(), b.getY());
			g2.drawLine(b.getX(), b.getY(), c.getX(), c.getY());
			g2.drawLine(c.getX(), c.getY(), d.getX(), d.getY());
			g2.drawLine(d.getX(), d.getY(), a.getX(), a.getY());

			g2.setColor(Color.BLACK);
		}
	}
	
	*/

	/**
	 * Creates a new draw panel.  ?? Clear pointSet updateModel updateView ??
	 */
	@Override
	public void createNewDrawPanel() {
		clearModel();
		updateView();
	}

	/**
	 * Removes all points from the point set.
	 */
	@Override
	public void clearModel() {
		pointSet.clear();
		hull.clear();
		pointSet.setHasChanged(false);
	}

	/**
	 * Returns true, if there are no points registered in the draw panel model.
	 *
	 * @return true, if there are no points registered in the draw panel model.
	 */
	@Override
	public boolean pointSetIsEmpty() {
		return pointSet.isEmpty();
	}

	/**
	 * Saves all points which are registered in the draw panel model to disc.
	 *
	 * @param path - the path of the file
	 */
	
	/*
	@Override
	
	public void savePointSet(String path) {

		if (pointSet.hasChangedSinceLastSave()) {
			File file = new File(path);
			try {
				FileWriter fileWriter = new FileWriter(file);
				PrintWriter printWriter = new PrintWriter(fileWriter);
				for (int i = 0; i < pointSet.getNumberOfPoints(); i++) {
					IPoint point = pointSet.getPointAt(i);
					printWriter.println(point.toString());
				}
				printWriter.close();
				pointSet.setHasChangedSinceLastSave(false);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	*/

	/**
	 * Loads points from a file into the draw panel model.
	 *
	 * @param file - the file from which the points are loaded.
	 */
	
	/*
	@Override
	public void loadPointsToPointSet(File file) {

		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader reader = new BufferedReader(fileReader);

			String line;
			pointSet.clear();
			while ((line = reader.readLine()) != null) {
				IPoint point = parser.parseLine(line);
				if (point != null) {
					pointSet.addPoint(point);
				}
			}
			reader.close();
			pointSet.setHasChangedSinceLastSave(false);
        // for FileReader(file)
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		// for readLine()
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	*/

	/**
	 * Returns true, if points where added or deleted or if the coordinated of a
	 * point changed.
	 *
	 * @return true, if points where added or deleted or if the coordinated of a
	 *         point changed.
	 */
	/*
	public boolean dataHasChangedSinceLastSave() {
		return dataChangedSinceLastSave;
	}
	*/

	/**
	 * Adds a command to command list.
	 *
	 * @param command - the command which is added to the command list
	 */
	// Commands
	
	
	private void addCommandToCommandList(ICommand command) {
		commandList.add(command);
		commandIndex++;
	}

	/**
	 * Undoes a command.
	 */
	@Override
	public void undoCommand() {
		if (commandIndex >= 0 && commandIndex < commandList.size()) {
			ICommand lastCommand = commandList.get(commandIndex);
			lastCommand.unexecute();
			commandIndex--;
			updateModel();
			updateView();
		}
	}

	/**
	 * Redoes command.
	 */
	@Override
	public void redoCommand() {
		if (commandIndex >= -1 && commandIndex < commandList.size() - 1) {
			ICommand lastCommand = commandList.get(commandIndex + 1);
			lastCommand.execute();
			commandIndex++;
			updateModel();
			updateView();
		}
	}

	/**
	 * Undo is enabled.
	 *
	 * @return true, if there are commands which can be undone.
	 */
	@Override
	public boolean undoIsEnabled() {
		if (commandIndex >= 0 && commandIndex < commandList.size()) {
			return true;
		}
		return false;
	}

	/**
	 * Redo is enabled.
	 *
	 * @return true, if there are commands which can be redone.
	 */
	@Override
	public boolean redoIsEnabled() {
		if (commandIndex >= -1 && commandIndex < commandList.size() - 1) {
			return true;
		}
		return false;
	}

	/**
	 * Removes the all comands after command index. This method is used when one ore
	 * more command have been undone an a new command is executed.
	 */
	private void removeAllComandsAfterCommandIndex() {
		for (int i = commandIndex + 1; i < commandList.size(); i++) {
			commandList.remove(i);
		}
	}

	/**
	 * Inserts a number of random points into the draw panel model which all fit
	 * onto the reference draw panel (the 'unzoomed' draw panel).
	 *
	 * @param number the number
	 */
	@Override
	public void insertRandomPoints(int number, int minX, int minY, int maxX, int maxY) {
		if (commandIndex != commandList.size() - 1) {
			removeAllComandsAfterCommandIndex();
		}
	    
		ICommand insertRandomPointsCommand = new InsertRandomPointsCommand(number, minX, minY, maxX, maxY, pointSet);
		insertRandomPointsCommand.execute();
		addCommandToCommandList(insertRandomPointsCommand);

		updateModel();
		updateView();

		pointSet.setHasChanged(true);
	}

	/**
	 * Insert a point into the point set if the user enters the point on the user
	 * interface. Model is updated afterwards.
	 *
	 * @param x the x
	 * @param y the y
	 */
	@Override
	public void insertPointToPointSetByUserInput(int x, int y) {
		if (commandIndex != commandList.size() - 1) {
			removeAllComandsAfterCommandIndex();
		}
		ICommand insertPointCommand = new InsertPointCommand(x, y, pointSet);
		insertPointCommand.execute();
		addCommandToCommandList(insertPointCommand);
		updateModel();
		updateView();
		pointSet.setHasChanged(true);
	}

	/**
	 * Insert point to point set from file input. Model is not updated afterwards.
	 *
	 * @param x - the x coordinate of the point
	 * @param y - the y coordinate of the point
	 */
	@Override
	public void insertPointToPointSetByFileInput(int x, int y) {
		pointSet.addPoint(new Point(x, y));
	}

	/**
	 * Deletes a point from the point set.
	 * 
	 * @param mouseX the mouse X
	 * @param mouseY the mouse Y
	 */
	@Override
	public void deletePointFromPointSetByUserInput(int mouseX, int mouseY, double totalScale) {
		System.out.println("in drawPanel, deletePointFromPointSetByUserInput, totalScale: " + totalScale + "\n \n \n");
		
		IPoint closest = getClosestPointToMouse(mouseX, mouseY, new ManhattanDistance());
		if (closest != null) {
			if (pointIsWithinMouseRadius(closest, mouseX, mouseY, new ManhattanDistance(), Settings.mouseRadius/totalScale)) {
				if (commandIndex != commandList.size() - 1) {
					removeAllComandsAfterCommandIndex();
				}
				ICommand removePointCommand = new RemovePointCommand(closest, pointSet);
				removePointCommand.execute();
				addCommandToCommandList(removePointCommand);
				updateModel();
				updateView();
				pointSet.setHasChanged(true);
			}
		}
	}

	/**
	 * Initializes a point drag.
	 *
	 * @param mouseX - the x coordinate of the mouse which is the starting x
	 *               coordinate of the drag
	 * @param mouseY - the y coordinate of the mouse which is the starting y
	 *               coordinate of the drag
	 */
	@Override
	public void initializePointDrag(int mouseX, int mouseY, double totalScale) {
		IPoint closest = getClosestPointToMouse(mouseX, mouseY, new ManhattanDistance());
		if (closest != null) {
			if (pointIsWithinMouseRadius(closest, mouseX, mouseY, new ManhattanDistance(), Settings.mouseRadius/totalScale)) {
				startMouseX = mouseX;
				startMouseY = mouseY;
				previousMouseX = mouseX;
				previousMouseY = mouseY;
				forDragSelected = closest;
			}
		}
	}

	/**
	 * Drags a point after the point drag has been initialized.
	 *
	 * @param mouseX - the x coordinate of the mouse
	 * @param mouseY - the y coordinate of the mouse
	 */
	@Override
	public void dragPoint(int mouseX, int mouseY) {
		if (forDragSelected != null) {
			int dx = mouseX - previousMouseX;
			int dy = mouseY - previousMouseY;

			forDragSelected.translate(dx, dy);
			pointSet.lexSort();

			previousMouseX = mouseX;
			previousMouseY = mouseY;

			// a lot to do here..
			updateModel();
			updateView();
		}
	}

	/**
	 * Terminate point drag. It refers to the starting x coordinate and the starting
	 * y coordinate of the mouse, puts the command in the command list. Important:
	 * The command is not executed, because the point has already been dragged.
	 *
	 * @param mouseX - the x coordinate of the mouse
	 * @param mouseY - the y coordinate of the mouse
	 */
	@Override
	public void terminatePointDrag(int mouseX, int mouseY) {
		int dx = mouseX - startMouseX;
		int dy = mouseY - startMouseY;

		if (commandIndex != commandList.size() - 1) {
			removeAllComandsAfterCommandIndex();
		}
		ICommand dragPointCommand = new DragPointCommand(dx, dy, forDragSelected);
		addCommandToCommandList(dragPointCommand);
		forDragSelected = null;
		// a lot to do here..
		updateModel();
		updateView();

		pointSet.setHasChanged(true);
	}

	/**
	 * Convex hull is shown.
	 *
	 * @return true, if the user chose to display the convex hull on the draw panel.
	 */
	
	@Override
	public boolean convexHullIsShown() {
		return view.convexHullIsShown();

	}
   
	/**
	 * Diameter is shown.
	 *
	 * @return true, if the user chose to display the diameter on the draw panel.
	 */
	
	
	@Override
	public boolean diameterIsShown() {
		return view.diameterIsShown();
	}
	

	/**
	 * Quadrangle is shown.
	 *
	 * @return true, if successful
	 */
	 
	
	@Override
	
	public boolean quadrangleIsShown() {
		return view.quadrangleIsShown();
	}
   
	/**
	 * Triangle is shown.
	 *
	 * @return true, if the user chose to display the triangle on the draw panel.
	 */
	 
	
	@Override
	public boolean triangleIsShown() {
		return view.triangleIsShown();
	}
	
	@Override
	public boolean animationIsShown() {
		return view.animationISRunning();
	}
	
	
	

	/**
	 * Determines, if the convex hull should be shown.
	 *
	 * @param b - true, if the convex hull should be shown. False otherwise.
	 */
	@Override
	public void setShowConvexHull(boolean b) {
		view.setConvexHullIsShown(b);

	}

	/**
	 * Determines whether the diameter is to be shown.
	 * 
	 * @param b the new show diameter
	 */
	@Override
	public void setShowDiameter(boolean b) {
		view.setDiameterIsShown(b);

	}

	/**
	 * Determines whether the quadrangle is to be shown.
	 * 
	 * @param b the new show quadrangle
	 */
	@Override
	public void setShowQuadrangle(boolean b) {
		view.setQuadrangleIsShown(b);

	}
	
	@Override
	public void setShowTriangle(boolean b) {
		view.setTriangleIsShown(b);
	}
	
	

	/**
	 * Determines whether the triangle is to be shown.
	 * 
	 * @param b the new show triangle
	 */
	
	
	
	

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
	 * @return the diameter length
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
	 * Hull as array.
	 * 
	 * @return the int[][] which contains all points of the hull in counterclockwise
	 *         direction.
	 */

	public int[][] hullAsArray() {
		return hull.toArray();
	}

	@Override
	public void initializeView() {
		view.initialize();	
	}

	@Override
	public void setShowAnimation(boolean b) {
		view.setShowAnimation(b);
		
	}
}
