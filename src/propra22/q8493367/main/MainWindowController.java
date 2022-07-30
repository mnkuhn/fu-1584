package propra22.q8493367.main;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import propra22.q8493367.command.CommandEvent;
import propra22.q8493367.command.CommandEventType;
import propra22.q8493367.draw.controller.IDrawPanelController;
import propra22.q8493367.file.FileEvent;
import propra22.q8493367.file.IFileManager;
import propra22.q8493367.point.RandomPointsEvent;
import propra22.q8493367.point.RandomPointsEventType;
import propra22.q8493367.settings.Settings;


/**
 * The Class MainWindowController is the controller for the main window.
 */
public class MainWindowController implements IMainWindowListener {

	/** The  controller for the draw panel . */
	private IDrawPanelController drawPanelController;
	
	/** The main window. */
	private IMainWindow view;
	
	/** The file manager. */
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
        /* We do not set the default value for the animation because we think 
         * it should not be selected in the beginning.
         */
        
        //view.setTriangleIsShown(drawPanelController.triangleIsShown());
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
			view.setAnimationIsShown(drawPanelController.getAnimationIsShown());
		}
		
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
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	@Override
	public void centerViewEventOccured() {
		drawPanelController.centerView();
		
	}

}
