package propra22.q8493367.main;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;



import propra22.q8493367.command.CommandEvent;
import propra22.q8493367.command.CommandEventType;
import propra22.q8493367.draw.controller.IDrawPanelController;
import propra22.q8493367.draw.model.IPointSet;
import propra22.q8493367.file.FileEvent;
import propra22.q8493367.file.IFileManager;
import propra22.q8493367.file.IFileManagerObserver;
import propra22.q8493367.point.RandomPointsEvent;
import propra22.q8493367.point.RandomPointsEventType;
import propra22.q8493367.settings.Settings;


/**
 * The Class MainWindowController is the controller for the main window.
 */
public class MainWindowController implements IMainWindowListener, IFileManagerObserver {

	/** The  controller for the draw panel . */
	private IDrawPanelController drawPanelController;
	
	/** The main window. */
	private IMainWindow view;
	
	/** The file manager. */
	private IFileManager fileManager;
	
	/** The point set. */
	private IPointSet pointSet;
	
    /**
     * Instantiates a new main window controller.
     *
     * @param drawPanelController the controller of the draw panel
     * @param pointSet the point set
     * @param mainWindow the main window
     * @param fileManager the file manager
     */
    public MainWindowController(IDrawPanelController drawPanelController, IPointSet pointSet, MainWindow mainWindow, IFileManager fileManager) {
    	this.drawPanelController = drawPanelController;
    	this.pointSet = pointSet;
    	this.view = mainWindow;
    	this.fileManager = fileManager;
    	
        view.setConvexHullIsShown(drawPanelController.convexHullIsShown());
        view.setDiameterIsShown(drawPanelController.diameterIsShown());
        view.setQuadrangleIsShown(drawPanelController.quadrangleIsShown());
        view.setTriangleIsShown(drawPanelController.triangleIsShown());
    }
	
    /**
     * Handles a file event.
     *
     * @param e the file event
     */
    @Override
	public void FileEventOccured(FileEvent e) {
		fileManager.handleFileEvent(e);
	}

	/**
	 * Handles a command event. Used for handling undo and redo.
	 *
	 * @param commandEvent the command event
	 */
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
	
	
	/**
	 * This method is invoked, when the user opens the 
	 * edit menu. It checks, if there are commands,
	 * which can be undone, or if there are commands
	 * which can be redone.
	 */
	@Override
	public void editEventOccured() {
		view.setUndoEnabled(drawPanelController.undoIsEnabled());
		view.setRedoEnabled(drawPanelController.redoIsEnabled());	
	}

	/**
	 * Handles a event which occurs, when the user wants to 
	 * insert a certain number of randomly generated points.
	 * 
	 * @param randomPointsEvent the random points event
	 */
	@Override
	public void insertRandomPointsEventOccured(RandomPointsEvent randomPointsEvent) {
		RandomPointsEventType type = randomPointsEvent.getType();
		
		drawPanelController.insertRandomPoints(type.getNumber(), randomPointsEvent.getMinX(), randomPointsEvent.getMinY(), randomPointsEvent.getMaxX(), randomPointsEvent.getMaxY() );		
	}
	
	/**
	 * Handles a view event. This event occurs
	 * when the user selects or deselects a checkbox
	 * for the convex hull, the diameter, the quadrangle,
	 * the triangle or the animation.
	 *
	 * @param viewEvent the view event
	 */
	@Override
	public void viewEventOccured(ViewEvent viewEvent) {
		drawPanelController.setShowConvexHull(viewEvent.convexHullIsDisplayed());
		drawPanelController.setShowDiameter(viewEvent.DiameterIsDisplayed());
		drawPanelController.setShowQuadrangle(viewEvent.QuadrangleIsDisplayed());
		drawPanelController.setShowTriangle(viewEvent.TriangleIsDisplayed());
		drawPanelController.setShowAnimation(viewEvent.animationIsShown());
		
		drawPanelController.updateView();
	}

	/**
	 * Show manual event occured. This method is invoked
	 * when the user chooses to display the manual.
	 */
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

	/**
	 * This class is a observer for the filemanager.
	 * This method is called, when the observer is notified
	 * by the file manager.
	 */
	@Override
	public void update() {
		view.setAnimationIsShown(false);
	}	
}
