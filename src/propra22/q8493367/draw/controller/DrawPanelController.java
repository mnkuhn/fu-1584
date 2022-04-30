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
import propra22.q8493367.draw.model.DrawPanelModel;
import propra22.q8493367.draw.model.IDrawPanelModel;
import propra22.q8493367.draw.view.DrawPanel;
import propra22.q8493367.draw.view.IDrawPanel;
import propra22.q8493367.draw.view.IDrawPanelListener;
import propra22.q8493367.file.Parser;
import propra22.q8493367.metric.IMetric;
import propra22.q8493367.metric.ManhattanDistance;
import propra22.q8493367.point.IPoint;
import propra22.q8493367.point.PointEvent;
import propra22.q8493367.settings.Settings;


public class DrawPanelController implements IDrawPanelListener, IDrawPanelController {
	
	private IDrawPanel view;
	private IDrawPanelModel model;
	
	private IPoint forDragSelected = null;
	
	private  int drawPanelReferenceWidth;
	private  int drawPanelReferenceHeight;
	
	private int previousMouseX;
	private int previousMouseY;
	
	private int startMouseX;
	private int startMouseY;
	
	private boolean pointDataHasChanged = false;
	
	private List<ICommand> commandList = new ArrayList<>();
	private int commandIndex =  -1;
	

	private ContourPolygonCalculator contourPolygonCalculator;
	private ConvexHullCalculator convexHullCalculator;
	
	private Parser parser = new Parser();
	
	public DrawPanelController(DrawPanel drawPanel, DrawPanelModel drawPanelModel) {
		this.view = drawPanel;
		this.model = drawPanelModel;
		contourPolygonCalculator = new ContourPolygonCalculator(drawPanelModel);
		convexHullCalculator = new ConvexHullCalculator(drawPanelModel);
		drawPanelReferenceWidth = drawPanel.getPreferredSize().width;
		drawPanelReferenceHeight = drawPanel.getPreferredSize().height;
		
	}
	
	
	//Points
	
	@Override
	public void pointInsertionEventOccured(PointEvent e) {
		
		if(commandIndex != commandList.size() - 1) {
			removeAllComandsAfterCommandIndex();
		}
		ICommand insertPointCommand = new InsertPointCommand(e.getX(), e.getY(), model);
		insertPointCommand.execute();
		addCommandToCommandList(insertPointCommand);
		
		updateModel();
		updateView();
		
		pointDataHasChanged = true;
	}
	
	//PointEvent ist nicht so eine gute Bezeichnung
	@Override
	public void pointDeletionEventOccured(PointEvent e) {
		IPoint closest =  getClosestPointToMouse(e.getX(), e.getY(), new ManhattanDistance());
		if(closest != null) {
			if(pointIsWithinMouseRadius(closest, e.getX(), e.getY(), new ManhattanDistance(), Settings.mouseRadius)) {

				if(commandIndex != commandList.size() - 1) {
					removeAllComandsAfterCommandIndex();
				}
				ICommand removePointCommand = new RemovePointCommand(closest, model);
	            removePointCommand.execute();
	            
	            addCommandToCommandList(removePointCommand);
	           
				updateModel();
				updateView();
				
				pointDataHasChanged = true;
			}
		}	
	}
	
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

	@Override
	public void dragEventOccured(PointEvent e) {
		if(forDragSelected != null) {
			int dx = e.getX() - previousMouseX;
			int dy = e.getY() - previousMouseY;
	
			forDragSelected.translate(dx, dy);
			model.lexSort();
			
			previousMouseX = e.getX();
			previousMouseY = e.getY();
			System.out.println(model.toString());
			contourPolygonCalculator.updateModel();
			convexHullCalculator.updateModel();
			updateView();		
		}
	}

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

	private boolean pointIsWithinMouseRadius(IPoint point, int mouseX, int mouseY, IMetric norm, int radius) {
		 return norm.distance(point.getX(), point.getY(), mouseX, mouseY) <= radius;
	}

	//returns null if there is no point on the drawPlane
	private IPoint getClosestPointToMouse(int mouseX, int mouseY, IMetric metric) {
		if(model.isEmpty()) {
			return null;
		}
		else {
			IPoint closest = model.getPointAt(0);
			for(int i = 1; i < model.getNumberOfPoints(); i++) {
				IPoint other = model.getPointAt(i);
				if(metric.distance(mouseX, mouseY, other.getX(), other.getY()) < metric.distance(mouseX, mouseY, closest.getX(), closest.getY()) ) {
					closest = other;
				}
			}
			return closest;
		}
	}
	
	
	public void updateView() {
		view.update();
	}

	public void updateModel() {
		model.lexSort(); 
		contourPolygonCalculator.updateModel();
		convexHullCalculator.updateModel();
	}

	@Override
	public void paintEventOccured(Graphics g) {
		if(!model.isEmpty()) {
			Graphics2D g2 = (Graphics2D)g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			drawPoints(g2);
			//drawContourPolygon(g);
			drawConvexHull(g2);
		}
	}
    
	
	
	//Drawing
	private void drawPoints(Graphics2D g2) {
		
		for(int i = 0; i < model.getNumberOfPoints(); i++) {
			 IPoint p = model.getPointAt(i);
			 g2.fillOval(p.getX() -  Settings.radius, p.getY() - Settings.radius , 2 * Settings.radius, 2 * Settings.radius);
		}	
	}
	
	
	
	private void drawConvexHull(Graphics2D g2) {
		g2.setColor(Settings.convexHullColor);
		
		  for(SectionType sectionType : SectionType.values()) { 
			  int sectionSize = model.getSizeOfSection(sectionType);
			  if(sectionSize > 1) { 
				  for(int i = 0; i < sectionSize - 1; i++) { 
					  IPoint first = model.getSectionPointAt(i, sectionType); 
					  IPoint second = model.getSectionPointAt(i + 1, sectionType);
					  g2.drawLine(first.getX(), first.getY(), second.getX(), second.getY()); 
				  }
			  }
			  sectionSize = model.getSizeOfSection(SectionType.LOWERLEFT);  
			  IPoint lastLeft = model.getSectionPointAt(sectionSize - 1, SectionType.LOWERLEFT);
			  sectionSize = model.getSizeOfSection(SectionType.LOWERRIGHT);
			  IPoint lastRight = model.getSectionPointAt(sectionSize - 1, SectionType.LOWERRIGHT);
			  g2.drawLine(lastLeft.getX(), lastLeft.getY(), lastRight.getX(), lastRight.getY());
			  
			  
			  sectionSize = model.getSizeOfSection(SectionType.UPPERLEFT);
			  lastLeft = model.getSectionPointAt(sectionSize - 1, SectionType.UPPERLEFT);
			  sectionSize = model.getSizeOfSection(SectionType.UPPERRIGHT);
			  lastRight = model.getSectionPointAt(sectionSize - 1, SectionType.UPPERRIGHT);
			  g2.drawLine(lastLeft.getX(), lastLeft.getY(), lastRight.getX(), lastRight.getY());
		    
			  }	  
		
		  g2.setColor(Color.BLACK);
	}
    
	
	
	
	
	
	@Override
	public void createNewDrawPanel() {
		model.clear();
		pointDataHasChanged = false;
		updateView();	
	}


	@Override
	public boolean drawPanelModelIsEmpty() {
		return model.isEmpty();
	}

	
	//Files
	@Override
	public void saveModel(String path) {
		
		if(pointDataHasChanged) {
				File file = new File(path);
				try {
					FileWriter fileWriter = new FileWriter(file);
					PrintWriter printWriter = new PrintWriter(fileWriter);
					for(int i = 0; i < model.getNumberOfPoints(); i++) {
						IPoint point = model.getPointAt(i);
						printWriter.println(point.toString());
					}
					printWriter.close();
					pointDataHasChanged = false;
				} catch (IOException e) {
					e.printStackTrace();
				}	
		}	
	}

	

	@Override
	public void loadPointsToModel(File file) {
		
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader reader = new BufferedReader(fileReader);
			
			model.clear();
			
			String line;
			while((line = reader.readLine()) != null) {
				IPoint point = parser.parseLine(line);
				if(point != null) {
					model.addPoint(point);
				}
			}
			pointDataHasChanged = false;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}


	@Override
	public boolean dataChangedSinceLastSave() {
		return pointDataHasChanged;
	}

	
	
	//Commands
	private void addCommandToCommandList(ICommand command) {
		commandList.add(command);
		commandIndex++;
	}
	
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
	
	@Override
	public boolean undoIsEnabled() {
		if(commandIndex >= 0 && commandIndex < commandList.size()) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean redoIsEnabled() {
		if(commandIndex >= - 1 && commandIndex < commandList.size() - 1) {
			return true;
		}
		return false;
	}
	
	private void removeAllComandsAfterCommandIndex() {
		for(int i = commandIndex + 1; i < commandList.size(); i++) {
			commandList.remove(i);
		}
	}


	@Override
	public void insertRandomPoints(int number) {
		if(commandIndex != commandList.size() - 1) {
			removeAllComandsAfterCommandIndex();
		}
		ICommand insertRandomPointsCommand = new InsertRandomPointsCommand(number, drawPanelReferenceWidth, drawPanelReferenceHeight, model);
		insertRandomPointsCommand.execute();
		addCommandToCommandList(insertRandomPointsCommand);
		
		updateModel();
		updateView();
		
		pointDataHasChanged = true;	
	}
}
