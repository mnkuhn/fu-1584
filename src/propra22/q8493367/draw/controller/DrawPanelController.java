package propra22.q8493367.draw.controller;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import propra22.q8493367.command.DragPointCommand;
import propra22.q8493367.command.ICommand;
import propra22.q8493367.command.InsertPointCommand;
import propra22.q8493367.command.InsertRandomPointsCommand;
import propra22.q8493367.command.RemovePointCommand;
import propra22.q8493367.contour.ContourPolygonCalculator;
import propra22.q8493367.contour.SectionType;
import propra22.q8493367.convex.DiameterAndQuadrangleCalculator;
import propra22.q8493367.convex.ConvexHullCalculator;
import propra22.q8493367.convex.Quadrangle;
import propra22.q8493367.draw.model.Diameter;
import propra22.q8493367.draw.model.Hull;
import propra22.q8493367.draw.model.IHull;
import propra22.q8493367.draw.model.IPointSet;
import propra22.q8493367.draw.view.DrawPanel;
import propra22.q8493367.file.FileManager;
import propra22.q8493367.file.Parser;
import propra22.q8493367.metric.IMetric;
import propra22.q8493367.metric.ManhattanDistance;
import propra22.q8493367.point.IPoint;
import propra22.q8493367.point.Point;
import propra22.q8493367.settings.Settings;



// TODO: Auto-generated Javadoc
/**
 * The controller of the draw panel. It also listens to all events on the draw panel.
 */
public class DrawPanelController implements IDrawPanelController {
    //view
	/** The view. */
	private DrawPanel view;
	
	
	
	//model
	/** The draw panel model. */
	private IPointSet pointSet;
	
	/** The hull. */
	//should be an argument of the controller constructor
	private IHull hull = new Hull();
	
	//** The diameter */
	private Diameter diameter = new Diameter();
	
	//** The quadrangle */
	private Quadrangle quadrangle = new Quadrangle();
	
	
	
	
	/** The draw panel reference width to which all calculations refer. */
	private  int drawPanelReferenceWidth = 800;
	
	/** The draw panel reference height to which all calculations refer. */
	private  int drawPanelReferenceHeight = 500;
	
	
	// Dragging points
	/** The for drag selected. */
	private IPoint forDragSelected = null;
	
	/** The previous mouse X. */
	private int previousMouseX;
	
	/** The previous mouse Y. */
	private int previousMouseY;
	
	/** The start mouse X. */
	private int startMouseX;
	
	/** The start mouse Y. */
	private int startMouseY;
	
	
	
	//commands
	/** The command list. */
	private List<ICommand> commandList = new ArrayList<>();
	
	/** The command index. */
	// actual position in the command list
	private int commandIndex =  -1;
	
	
	
	//calculations
	/** The contour polygon calculator. */
	private ContourPolygonCalculator contourPolygonCalculator;
	
	/** The convex hull calculator. */
	private ConvexHullCalculator convexHullCalculator;
	
	/**The calculator for the diameter*/
	private DiameterAndQuadrangleCalculator diameterAndQuadrangleCalulator;
	
	
	//File
	/** The parser. */
	private Parser parser = new Parser();
	
	/** The data changed since last save. */
	// Indicates if the point set has changed since last save
	private boolean dataChangedSinceLastSave = false;

    //default settings for the display of the geometric objects
	private boolean convexHullIsShown = Settings.defaultConvexHullIsShown;
	private boolean diameterIsShown = Settings.defaultDiameterIsShown;
	private boolean quadrangleIsShown = Settings.defaultQuadrangleIsShown;
	private boolean triangleIsShown = Settings.defaultTriangleIsShown;
	
	/**
	 * Instantiates a new draw panel controller.
	 *
	 * @param drawPanelModel the draw panel model
	 * @param drawPanel the draw panel
	 */
	// should take the hull too
	public DrawPanelController(IPointSet drawPanelModel, DrawPanel drawPanel) {
		this.view = drawPanel;
		this.pointSet = drawPanelModel;
		this.contourPolygonCalculator = new ContourPolygonCalculator(drawPanelModel, hull);
		this.convexHullCalculator = new ConvexHullCalculator(hull);
		this.diameterAndQuadrangleCalulator = new DiameterAndQuadrangleCalculator(hull);
		this.drawPanelReferenceWidth = drawPanel.getPreferredSize().width;
		this.drawPanelReferenceHeight = drawPanel.getPreferredSize().height;	
	}
	
	
	/**
	 * Instantiates a new draw panel controller which only takes a model. Used for testing 
	 *
	 * @param pointSet the draw panel model
	 */
	// should take the hull too
	public DrawPanelController(IPointSet pointSet) {
		
		this.pointSet = pointSet;
		this.view = null;
		contourPolygonCalculator = new ContourPolygonCalculator(pointSet, hull);
		convexHullCalculator = new ConvexHullCalculator(hull);
		diameterAndQuadrangleCalulator = new DiameterAndQuadrangleCalculator(hull);
	}
	


	/**
	 * Determines, if a point is within a certain radius to the mouse
	 * pointer.
	 *
	 * @param point - the point
	 * @param mouseX - the x coodinate of the mouse pointer
	 * @param mouseY - the y coordinate of the mouse pointer
	 * @param norm - the norm, which is used to determine the distance
	 * @param radius - the radius
	 * @return true, if successful
	 */
	private boolean pointIsWithinMouseRadius(IPoint point, int mouseX, int mouseY, IMetric norm, int radius) {
		 return norm.distance(point.getX(), point.getY(), mouseX, mouseY) <= radius;
	}

	/**
	 * Gets the closest point to mouse pointer. Returns null if there is
	 * no point on the draw panel.
	 *
	 * @param mouseX - the x coordinate of the mouse pointer
	 * @param mouseY - the y coordinate of the mouse pointer
	 * @param metric - the metric which is used to determine the distance
	 * @return the closest point to mouse pointer
	 */
	
	private IPoint getClosestPointToMouse(int mouseX, int mouseY, IMetric metric) {
		if(pointSet.isEmpty()) {
			return null;
		}
		else {
			IPoint closest = pointSet.getPointAt(0);
			for(int i = 1; i < pointSet.getNumberOfPoints(); i++) {
				IPoint other = pointSet.getPointAt(i);
				if(metric.distance(mouseX, mouseY, other.getX(), other.getY()) < metric.distance(mouseX, mouseY, closest.getX(), closest.getY()) ) {
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
		if(!pointSet.isEmpty()) {
			
			long start = System.currentTimeMillis();
			pointSet.lexSort();
			long end = System.currentTimeMillis();
			System.out.println("Punktmenge sortieren: " + (end - start) + " ms");
			
			start = end;
			contourPolygonCalculator.calculateContourPolygon();
			end = System.currentTimeMillis();
			System.out.println("Konturpolygon berechnen: " + (end - start) +  " ms");
			
			start = end;
			convexHullCalculator.calculateConvexHull();
			end = System.currentTimeMillis();
			System.out.println("Konvexe Hülle berechnen: " + (end - start)  + " ms");
			
			start = end;
			diameterAndQuadrangleCalulator.calculate(diameter, quadrangle);
			end = System.currentTimeMillis();
			System.out.println("Durchmesser und Viereck berechen: " + (end - start)  + " ms");
			
		}
	}

	/**
	 * Paints the points, the convex hull und was noch alles kommt on the 
	 * draw panel
	 *
	 * @param g the g
	 */
	@Override
	public void paintDrawPanel(Graphics g) {
		if(!pointSet.isEmpty()) {
			Graphics2D g2 = (Graphics2D)g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			drawPoints(g2);
			
			if(convexHullIsShown) {drawHull(g2, Settings.convexHullColor);}
			if(diameterIsShown) {drawDiameter(g2, Settings.diameterColor);}
			if(quadrangleIsShown) {drawQuadrangle(g2, Settings.quadrangleColor);}
			//if(triangleIsShown) {drawTriangle(g2, Settings.triangleColor);}
		}	
	}
	
	
	
	/**
	 * Draws all points from the model onto the draw panel
	 *
	 * @param g2 - the Graphics2D Object which is used for painting
	 */
	private void drawPoints(Graphics2D g2) {
		
		for(int i = 0; i < pointSet.getNumberOfPoints(); i++) {
			 IPoint p = pointSet.getPointAt(i);
			 g2.fillOval(p.getX() -  Settings.radius, p.getY() - Settings.radius , 2 * Settings.radius, 2 * Settings.radius);
		}	
	}
	
	
	
	/**
	 * Draws the convex hull.
	 *
	 * @param g2 - the Graphics2D Object which is used for painting
	 * @param color - the color for the hull
	 */
	private void drawHull(Graphics2D g2, Color color) {
		g2.setColor(color);
		
		  for(SectionType sectionType : SectionType.values()) { 
			  int sectionSize = hull.getSizeOfSection(sectionType);
			  if(sectionSize > 1) { 
				  for(int i = 0; i < sectionSize - 1; i++) { 
		
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
	
	private void drawDiameter(Graphics2D g2, Color color) {
		if(diameter != null) {
			g2.setColor(color);
			IPoint a = diameter.getA();
			IPoint b = diameter.getB();
			g2.drawLine(a.getX(), a.getY(), b.getX(), b.getY());
			g2.setColor(Color.BLACK);
		}	
	}
	
	private void drawQuadrangle(Graphics2D g2, Color color) {
		if(quadrangle != null) {
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
    
	
	/**
	 * Creates a new draw panel.
	 */
	@Override
	public void createNewDrawPanel() {
		clearModel();
		updateView();	
	}
	
	/**
	 * Removes all points from the point set
	 */
	@Override
	public void clearModel() {
		pointSet.clear();
		hull.clear();
		dataChangedSinceLastSave = false;
	}


	/**
	 * Returns true, if there are no points registered in the draw panel model.
	 *
	 * @return true, if there are no points registered in the
	 * draw panel model.
	 */
	@Override
	public boolean drawPanelModelIsEmpty() {
		return pointSet.isEmpty();
	}

	
	/**
	 * Saves all points which are registered in the draw panel
	 * model to disc.
	 *
	 * @param path - the path of the file
	 */
	@Override
	public void saveModel(String path) {
		
		if(dataChangedSinceLastSave) {
			File file = new File(path);
			try {
				FileWriter fileWriter = new FileWriter(file);
				PrintWriter printWriter = new PrintWriter(fileWriter);
				for(int i = 0; i < pointSet.getNumberOfPoints(); i++) {
					IPoint point = pointSet.getPointAt(i);
					printWriter.println(point.toString());
				}
				printWriter.close();
				dataChangedSinceLastSave = false;
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}	
	}

	

	/**
	 * Loads points from a file into the draw
	 * panel model.
	 *
	 * @param file - the file from which the points are loaded.
	 */
	@Override
	public void loadPointsToModel(File file) {
		
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader reader = new BufferedReader(fileReader);
			
			pointSet.clear();
			
			String line;
			while((line = reader.readLine()) != null) {
				IPoint point = parser.parseLine(line);
				if(point != null) {
					pointSet.addPoint(point);
				}
			}
			reader.close();
			dataChangedSinceLastSave = false;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
    

	/**
	 * Returns true, if points where added or deleted or if 
	 * the coordinated of a point changed.
	 *
	 * @return true, if points where added or deleted or if 
	 * the coordinated of a point changed.
	 */
	@Override
	public boolean dataHasChangedSinceLastSave() {
		return dataChangedSinceLastSave;
	}

	
	
	/**
	 * Adds a command to command list.
	 *
	 * @param command - the command which is added to the command list
	 */
	//Commands
	private void addCommandToCommandList(ICommand command) {
		commandList.add(command);
		commandIndex++;
	}
	
	/**
	 * Undoes a command.
	 */
	@Override
	public void undoCommand() {
		if (commandIndex >= 0 && commandIndex < commandList.size()){
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
		if(commandIndex >= - 1 && commandIndex < commandList.size() - 1) {
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
		if(commandIndex >= 0 && commandIndex < commandList.size()) {
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
		if(commandIndex >= - 1 && commandIndex < commandList.size() - 1) {
			return true;
		}
		return false;
	}
	
	/**
	 * Removes the all comands after command index. This method is used
	 * when one ore more command have been undone an a new command is executed.
	 */
	private void removeAllComandsAfterCommandIndex() {
		for(int i = commandIndex + 1; i < commandList.size(); i++) {
			commandList.remove(i);
		}
	}


	/**
	 * Inserts a number of random points into the draw panel model which all
	 * fit onto the reference draw panel (the 'unzoomed' draw panel).
	 *
	 * @param number the number
	 */
	@Override
	public void insertRandomPoints(int number) {
		if(commandIndex != commandList.size() - 1) {
			removeAllComandsAfterCommandIndex();
		}
		ICommand insertRandomPointsCommand = new InsertRandomPointsCommand(number, 
				drawPanelReferenceWidth, drawPanelReferenceHeight, pointSet);
		insertRandomPointsCommand.execute();
		addCommandToCommandList(insertRandomPointsCommand);
		
		updateModel();
		updateView();
		
		dataChangedSinceLastSave = true;	
	}


	/**
	 * Insert a point into the point set
	 *
	 * @param point the point
	 */
	@Override
	public void insertPointToPointSetFromUserInput(int x, int y) {
		if(commandIndex != commandList.size() - 1) {
			removeAllComandsAfterCommandIndex();
		}
		ICommand insertPointCommand = new InsertPointCommand(x, y, pointSet);
		insertPointCommand.execute();
		addCommandToCommandList(insertPointCommand);
		updateModel();
		updateView();
		dataChangedSinceLastSave = true;
	}
	
	@Override
	public void insertPointToPointSetFromFileInput(int x, int y) {
		pointSet.addPoint(new Point(x, y));
	}
	
	

	/**
	 * Deletes a point from the point set.
	 * @param mouseX the mouse X
	 * @param mouseY the mouse Y
	 */
	@Override
	public void deletePointFromModel(int mouseX, int mouseY) {
		IPoint closest =  getClosestPointToMouse(mouseX, mouseY, new ManhattanDistance());
		if(closest != null) {
			if(pointIsWithinMouseRadius(closest, mouseX, mouseY, new ManhattanDistance(), Settings.mouseRadius)) {
				if(commandIndex != commandList.size() - 1) {
					removeAllComandsAfterCommandIndex();
				}
				ICommand removePointCommand = new RemovePointCommand(closest, pointSet);
	            removePointCommand.execute();
	            addCommandToCommandList(removePointCommand);
				updateModel();
				updateView();
				dataChangedSinceLastSave = true;
			}
		}		
	}


	/**
	 * Initializes a point drag.
	 *
	 * @param mouseX - the x coordinate of the mouse which is the starting x coordinate of the 
	 * drag 
	 * @param mouseY - the y coordinate of the mouse which is the starting y coordinate of the 
	 * drag
	 */
	@Override
	public void initializePointDrag(int mouseX, int mouseY) {
		IPoint closest =  getClosestPointToMouse(mouseX, mouseY, new ManhattanDistance());
		if(closest != null) {
			if(pointIsWithinMouseRadius(closest, mouseX, mouseY, new ManhattanDistance(), Settings.mouseRadius)) {
				startMouseX = mouseX;
				startMouseY = mouseY;
				previousMouseX = mouseX;
				previousMouseY = mouseY;
				forDragSelected = closest;
			}
		}			
	}


	/**
	 * Drag point.
	 *
	 * @param mouseX - the x coordinate of the mouse
	 * @param mouseY - the y coordinate of the mouse
	 */
	@Override
	public void dragPoint(int mouseX, int mouseY) {
		if(forDragSelected != null) {
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
	 * Terminate point drag. It refers to the starting x coordinate and the
	 * starting y coordinate of the mouse, puts the command in the
	 * command list and executes the command.
	 *
	 * @param mouseX - the x coordinate of the mouse
	 * @param mouseY - the y coordinate of the mouse
	 */
	@Override
	public void terminatePointDrag(int mouseX, int mouseY) {
		dataChangedSinceLastSave = true;
		int dx = mouseX - startMouseX;
		int dy = mouseY - startMouseY;
		
		if(commandIndex != commandList.size() - 1) {
			removeAllComandsAfterCommandIndex();
		}
		ICommand dragPointCommand = new DragPointCommand(dx, dy, forDragSelected);
        addCommandToCommandList(dragPointCommand);
        forDragSelected = null;
        // a lot to do here..
		updateModel();
		updateView();
		
		dataChangedSinceLastSave = true;	
	}



	
	// for testing
	public Diameter getDiameter(){
		return this.diameter;
	}


	public double getDiameterLength() {
		if(diameter != null) {	
			IPoint point1 = this.diameter.getA();
			IPoint point2 = this.diameter.getB();
			double dx = point1.getX() - point2.getX();
			double dy = point1.getY() - point2.getY();
			return Math.sqrt( dx*dx + dy*dy );
		}
		return -1;	
	}
	
	public Quadrangle getBiggestQuadrangle() {
		return quadrangle;
	}
	
	
	/**
	 * Hull as array.
	 *
	 * @return the int[][]
	 */
	@Override
	public int[][] hullAsArray(){
		return hull.toArray();
	}


	@Override
	public boolean convexHullIsShown() {
	     return convexHullIsShown;
		
	}


	@Override
	public boolean diameterIsShown() {
		return diameterIsShown;
	}


	@Override
	public boolean quadrangelIsShown() {
		return quadrangleIsShown;
	}


	@Override
	public boolean triangelIsShown() {
		return triangleIsShown;
	}


	@Override
	public void setShowConvexHull(boolean b) {
		convexHullIsShown = b;
		
	}


	@Override
	public void setShowDiameter(boolean b) {
		diameterIsShown = b;
		
	}


	@Override
	public void setShowQuadrangle(boolean b) {
		quadrangleIsShown =  b;
		
	}


	@Override
	public void setShowTriangle(boolean b) {
		triangleIsShown = b;
		
	}

}
