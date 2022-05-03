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
import propra22.q8493367.convex.ConvexHullCalculator;
import propra22.q8493367.draw.model.Hull;

import propra22.q8493367.draw.model.IHull;
import propra22.q8493367.draw.model.IDrawPanelModel;
import propra22.q8493367.draw.view.DrawPanel;
import propra22.q8493367.draw.view.IDrawPanelListener;
import propra22.q8493367.file.Parser;
import propra22.q8493367.metric.IMetric;
import propra22.q8493367.metric.ManhattanDistance;
import propra22.q8493367.point.IPoint;
import propra22.q8493367.point.PointEvent;
import propra22.q8493367.settings.Settings;



// TODO: Auto-generated Javadoc
/**
 * The controller of the draw panel. It also listens to all events on the draw panel.
 */
public class DrawPanelController implements IDrawPanelListener, IDrawPanelController {
	
	// model and view
	private DrawPanel view;
	private IDrawPanelModel drawPanelModel;
	
	private  int drawPanelReferenceWidth;
	private  int drawPanelReferenceHeight;
	
	// Dragging
	private IPoint forDragSelected = null;
	private int previousMouseX;
	private int previousMouseY;
	private int startMouseX;
	private int startMouseY;
	
	// List of the executed commands
	private List<ICommand> commandList = new ArrayList<>();
	private int commandIndex =  -1;
	
	private IHull hull = new Hull();
	private ContourPolygonCalculator contourPolygonCalculator;
	private ConvexHullCalculator convexHullCalculator;
	
	// Parsing a file
	private Parser parser = new Parser();
	
	// Saving to file
	private boolean pointDataHasChanged = false;
	
	/**
	 * Instantiates a new draw panel controller.
	 *
	 * @param drawPanelModell - the draw panel
	 * @param view - the model of the draw panel.
	 */
	public DrawPanelController(IDrawPanelModel drawPanelModel, DrawPanel drawPanel) {
		this.view = drawPanel;
		this.drawPanelModel = drawPanelModel;
		contourPolygonCalculator = new ContourPolygonCalculator(drawPanelModel, hull);
		convexHullCalculator = new ConvexHullCalculator(hull);
		drawPanelReferenceWidth = drawPanel.getPreferredSize().width;
		drawPanelReferenceHeight = drawPanel.getPreferredSize().height;
		
	}
	
	public DrawPanelController(IDrawPanelModel drawPanelModel) {
		this.drawPanelModel = drawPanelModel;
		this.view = view;
	}
	
	
	//Points
	
	/**
	 * This method is called when the user wants to insert a point 
	 * on the draw panel.
	 *
	 * @param e - the point event which contains information about the point.
	 */
	@Override
	public void pointInsertionEventOccured(PointEvent e) {
		
		if(commandIndex != commandList.size() - 1) {
			removeAllComandsAfterCommandIndex();
		}
		ICommand insertPointCommand = new InsertPointCommand(e.getX(), e.getY(), drawPanelModel);
		insertPointCommand.execute();
		addCommandToCommandList(insertPointCommand);
		
		updateModel();
		updateView();
		
		pointDataHasChanged = true;
	}
	
	/**
	 * This method is called when the user wants to delete a point
	 * from the draw panel.
	 *
	 * @param e - the point event which contains information about the point.
	 */
	//PointEvent ist nicht so eine gute Bezeichnung
	@Override
	public void pointDeletionEventOccured(PointEvent e) {
		IPoint closest =  getClosestPointToMouse(e.getX(), e.getY(), new ManhattanDistance());
		if(closest != null) {
			if(pointIsWithinMouseRadius(closest, e.getX(), e.getY(), new ManhattanDistance(), Settings.mouseRadius)) {

				if(commandIndex != commandList.size() - 1) {
					removeAllComandsAfterCommandIndex();
				}
				ICommand removePointCommand = new RemovePointCommand(closest, drawPanelModel);
	            removePointCommand.execute();
	            
	            addCommandToCommandList(removePointCommand);
	           
				updateModel();
				updateView();
				
				pointDataHasChanged = true;
			}
		}	
	}
	
	/**
	 * This method is called when the user starts to drag a point
	 * over the draw panel.
	 *
	 * @param e - point event which contains information about the point
	 */
	@Override
	public void dragInitializedEventOccured(PointEvent e) {
		
		IPoint closest =  getClosestPointToMouse(e.getX(), e.getY(), new ManhattanDistance());
	
		if(closest != null) {
			if(pointIsWithinMouseRadius(closest, e.getX(), e.getY(), new ManhattanDistance(), Settings.mouseRadius)) {
				
				startMouseX = e.getX();
				startMouseY = e.getY();
				previousMouseX = e.getX();
				previousMouseY = e.getY();
				forDragSelected = closest;	
			}
		}			
	}

	/**
	 * This method is called when the user is dragging a point over the 
	 * draw panel.
	 *
	 * @param e - point event which contains information about the point
	 */
	@Override
	public void dragEventOccured(PointEvent e) {
		if(forDragSelected != null) {
			int dx = e.getX() - previousMouseX;
			int dy = e.getY() - previousMouseY;
	
			forDragSelected.translate(dx, dy);
			drawPanelModel.lexSort();
			
			previousMouseX = e.getX();
			previousMouseY = e.getY();
			System.out.println(drawPanelModel.toString());
			contourPolygonCalculator.calculateContourPolygon();
			convexHullCalculator.calculateConvexHull();
			updateView();		
		}
	}

	/**
	 * This method is called when the user has stopped dragging a 
	 * point over the draw panel.
	 *
	 * @param dragEvent the drag event
	 */
	@Override
	public void dragEventEnded(PointEvent dragEvent) {
		pointDataHasChanged = true;
		
		int dx = dragEvent.getX() - startMouseX;
		int dy = dragEvent.getY() - startMouseY;
		
		if(commandIndex != commandList.size() - 1) {
			removeAllComandsAfterCommandIndex();
		}
		
		ICommand dragPointCommand = new DragPointCommand(dx, dy, forDragSelected);
        addCommandToCommandList(dragPointCommand);
       
		updateModel();
		updateView();
		
		pointDataHasChanged = true;		
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
		if(drawPanelModel.isEmpty()) {
			return null;
		}
		else {
			IPoint closest = drawPanelModel.getPointAt(0);
			for(int i = 1; i < drawPanelModel.getNumberOfPoints(); i++) {
				IPoint other = drawPanelModel.getPointAt(i);
				if(metric.distance(mouseX, mouseY, other.getX(), other.getY()) < metric.distance(mouseX, mouseY, closest.getX(), closest.getY()) ) {
					closest = other;
				}
			}
			return closest;
		}
	}
	
	
	/**
	 * Updates the view i.e the draw panel.
	 */
	public void updateView() {
		view.update();
	}

	/**
	 * Update model i.e. the draw panel model.
	 */
	public void updateModel() {
		drawPanelModel.lexSort();
		contourPolygonCalculator.calculateContourPolygon();
		convexHullCalculator.calculateConvexHull();
		System.out.println(drawPanelModel.toString());
		System.out.println(hull.numberOfRows());
		hull.outArray();
	}

	/**
	 * This method is called, if the view needs to be updated
	 * 
	 * @param g - the graphics object of the draw panel.
	 */
	@Override
	public void paintEventOccured(Graphics g) {
		if(!drawPanelModel.isEmpty()) {
			Graphics2D g2 = (Graphics2D)g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			drawPoints(g2);
			drawHull(g2, Settings.convexHullColor);
		}
	}
    
	
	
	/**
	 * Draws all points which are part of the draw panel model.
	 *
	 * @param g2 - the graphics object of the draw panel which has
	 * been casted to Graphics2D.
	 */
	private void drawPoints(Graphics2D g2) {
		
		for(int i = 0; i < drawPanelModel.getNumberOfPoints(); i++) {
			 IPoint p = drawPanelModel.getPointAt(i);
			 g2.fillOval(p.getX() -  Settings.radius, p.getY() - Settings.radius , 2 * Settings.radius, 2 * Settings.radius);
		}	
	}
	
	
	
	/**
	 * Draws the convex hull.
	 *
	 * @param g2 the g 2
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
    
	
	/**
	 * Creates a new draw panel.
	 */
	@Override
	public void createNewDrawPanel() {
		clearModel();
		updateView();	
	}
	
	@Override
	public void clearModel() {
		drawPanelModel.clear();
		hull.clearAllSections();
		pointDataHasChanged = false;
	}


	/**
	 * Returns true, if there are no points registered in the draw panel model.
	 *
	 * @return true, if there are no points registered in the
	 * draw panel model.
	 */
	@Override
	public boolean drawPanelModelIsEmpty() {
		return drawPanelModel.isEmpty();
	}

	
	/**
	 * Saves all points which are registered in the draw panel
	 * model to disc.
	 *
	 * @param path the path
	 */
	
	@Override
	public void saveModel(String path) {
		
		if(pointDataHasChanged) {
			File file = new File(path);
			try {
				FileWriter fileWriter = new FileWriter(file);
				PrintWriter printWriter = new PrintWriter(fileWriter);
				for(int i = 0; i < drawPanelModel.getNumberOfPoints(); i++) {
					IPoint point = drawPanelModel.getPointAt(i);
					printWriter.println(point.toString());
				}
				printWriter.close();
				pointDataHasChanged = false;
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
			
			drawPanelModel.clear();
			
			String line;
			while((line = reader.readLine()) != null) {
				IPoint point = parser.parseLine(line);
				if(point != null) {
					drawPanelModel.addPoint(point);
				}
			}
			pointDataHasChanged = false;
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
	public boolean dataChangedSinceLastSave() {
		return pointDataHasChanged;
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
		ICommand insertRandomPointsCommand = new InsertRandomPointsCommand(number, drawPanelReferenceWidth, drawPanelReferenceHeight, drawPanelModel);
		insertRandomPointsCommand.execute();
		addCommandToCommandList(insertRandomPointsCommand);
		
		updateModel();
		updateView();
		
		pointDataHasChanged = true;	
	}


	@Override
	public void insertPoint(IPoint point) {
		drawPanelModel.addPoint(point);
		
	}
	
	@Override
	public int[][] hullAsArray(){
		return hull.toArray();
	}
}
