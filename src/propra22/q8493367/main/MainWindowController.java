package propra22.q8493367.main;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


import propra22.q8493367.command.CommandEvent;
import propra22.q8493367.command.CommandEventType;
import propra22.q8493367.draw.controller.DrawPanelController;
import propra22.q8493367.draw.controller.IDrawPanelController;
import propra22.q8493367.draw.model.IPointSet;
import propra22.q8493367.draw.model.PointSet;
import propra22.q8493367.file.FileEvent;
import propra22.q8493367.file.FileManager;
import propra22.q8493367.file.IFileManager;
import propra22.q8493367.file.IFileManagerObserver;
import propra22.q8493367.file.Parser;
import propra22.q8493367.point.RandomPointsEvent;
import propra22.q8493367.point.RandomPointsEventType;
import propra22.q8493367.settings.Settings;

public class MainWindowController implements IMainWindowListener, IFileManagerObserver {

	private IDrawPanelController drawPanelController;
	private IMainWindow view;
	private IFileManager fileManager;
	private IPointSet pointSet;
	
    public MainWindowController(IDrawPanelController drawPanelController, IPointSet pointSet, MainWindow mainWindow, IFileManager fileManager) {
    	this.drawPanelController = drawPanelController;
    	this.pointSet = pointSet;
    	this.view = mainWindow;
    	this.fileManager = fileManager;
    	
        //Parser parser = new Parser();
    	//this.fileManager = new FileManager((PointSet)pointSet, (DrawPanelController)drawPanelController, (Parser)parser);
    	
        view.setConvexHullIsShown(drawPanelController.convexHullIsShown());
        view.setDiameterIsShown(drawPanelController.diameterIsShown());
        view.setQuadrangleIsShown(drawPanelController.quadrangleIsShown());
        view.setTriangleIsShown(drawPanelController.triangleIsShown());
    }
	
    @Override
	public void FileEventOccured(FileEvent e) {
		fileManager.handleFileEvent(e);
	}

	@Override
	public void commandEventOccured(CommandEvent commandEvent) {
		CommandEventType type = commandEvent.getEventType();
		switch (type) {
			case UNDO: {
				drawPanelController.undoCommand();
				break;
			}
			case REDO: {
				drawPanelController.redoCommand();
				break;		
			}      
		}	
	}
	
	
	@Override
	public void editEventOccured() {
		view.setUndoEnabled(drawPanelController.undoIsEnabled());
		view.setRedoEnabled(drawPanelController.redoIsEnabled());	
	}

	@Override
	public void insertRandomPointsEventOccured(RandomPointsEvent randomPointsEvent) {
		RandomPointsEventType type = randomPointsEvent.getType();
		
		drawPanelController.insertRandomPoints(type.getNumber(), randomPointsEvent.getMinX(), randomPointsEvent.getMinY(), randomPointsEvent.getMaxX(), randomPointsEvent.getMaxY() );		
	}
	
	@Override
	public void viewEventOccured(ViewEvent viewEvent) {
		drawPanelController.setShowConvexHull(viewEvent.convexHullIsDisplayed());
		drawPanelController.setShowDiameter(viewEvent.DiameterIsDisplayed());
		drawPanelController.setShowQuadrangle(viewEvent.QuadrangleIsDisplayed());
		drawPanelController.setShowTriangle(viewEvent.TriangleIsDisplayed());
		drawPanelController.setShowAnimation(viewEvent.animationIsShown());
		
		//drawPanelController.updateModel();
		drawPanelController.updateView();
	}

	@Override
	public void showManualEventOccured() {
		File file = new File(Settings.defaultManualPath);
		try {
			Desktop.getDesktop().browse(file.toURI());
			
			
			//view.showManual(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	@Override
	public void update() {
		view.setAnimationIsShown(false);
	}	
}
