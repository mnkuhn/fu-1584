package controllers;

import gui.IMainWindowListener;
import gui.MainWindow;
import usecases.CommandEvent;
import usecases.CommandEventType;
import usecases.FileEvent;
import usecases.FileSettings;
import usecases.RandomPointsEvent;
import usecases.RandomPointsEventType;
import usecases.ViewEvent;


/**
 * The controller for the main window.
 * It provides methods that process events related to user input in the menu bar
 * of the main window.
 */
public class MainWindowController implements IMainWindowListener {

	/** The  controller for the draw panel*/
	private IDrawPanelController drawPanelController;
	
	/** The main window*/
	private IMainWindow view;
	
	/** The file manager which handles all file file events.*/
	private IFileManager fileManager;
	
    /**
     * Instantiates a new main window controller.
     *
     * @param drawPanelController the controller of the draw panel
     * @param mainWindow the main window
     * @param fileManager the file manager
     */
    public MainWindowController(IDrawPanelController drawPanelController, MainWindow mainWindow, IFileManager fileManager) {
    	this.drawPanelController = drawPanelController;
    	this.view = mainWindow;
    	this.fileManager = fileManager;
    	
    	// Set the default settings 
        view.setConvexHullIsShown(drawPanelController.getConvexHullIsshown());
        view.setDiameterIsShown(drawPanelController.getDiameterIsShown());
        view.setQuadrangleIsShown(drawPanelController.getQuadrangleIsShown());
        view.setAnimationIsShown(drawPanelController.getAnimationIsShown());
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
		
		if(viewEvent != null) {
			drawPanelController.setShowConvexHull(viewEvent.convexHullIsDisplayed());
			drawPanelController.setShowDiameter(viewEvent.DiameterIsDisplayed());
			drawPanelController.setShowQuadrangle(viewEvent.QuadrangleIsDisplayed());
			drawPanelController.setShowTriangle(viewEvent.TriangleIsDisplayed());
			drawPanelController.setShowAnimation(viewEvent.animationIsShown());
			
			drawPanelController.updateView();
		} else {
			view.setConvexHullIsShown(drawPanelController.getConvexHullIsshown());
			view.setDiameterIsShown(drawPanelController.getDiameterIsShown());
			view.setQuadrangleIsShown(drawPanelController.getQuadrangleIsShown());
			view.setTriangleIsShown(drawPanelController.getTriangleIsShown());
			view.setAnimationIsShown(drawPanelController.getAnimationIsShown());
		}	
	}

	
	@Override
	public void showManualEventOccured() {
		view.showManual(FileSettings.defaultManualPath);
	}

	@Override
	public void centerViewEventOccured() {
		drawPanelController.centerView();
		
	}

}
