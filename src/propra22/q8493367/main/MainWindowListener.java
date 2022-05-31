package propra22.q8493367.main;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import propra22.q8493367.command.CommandEvent;
import propra22.q8493367.command.CommandEventType;
import propra22.q8493367.draw.controller.IDrawPanelController;
import propra22.q8493367.file.FileEvent;
import propra22.q8493367.file.FileManager;
import propra22.q8493367.point.RandomPointsEvent;
import propra22.q8493367.point.RandomPointsEventType;
import propra22.q8493367.settings.Settings;

public class MainWindowListener implements IMainWindowListener {

	private IDrawPanelController drawPanelController;
	private IMainWindow view;
	private FileManager fileManager;
	
    public MainWindowListener(IDrawPanelController drawPanelController, MainWindow mainWindow) {
    	this.drawPanelController = drawPanelController;
    	this.view = mainWindow;
    	this.fileManager = new FileManager(drawPanelController, mainWindow);
    	
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
		System.out.println("Obere linke Ecke des viewports: " + randomPointsEvent.getUpperLeftCornerOfViewPortX() + "  " + randomPointsEvent.getUpperLeftCornerOfViewPortY());
		System.out.println("Grösse des viewports: " + randomPointsEvent.getViewportSize().getWidth() + "  " + randomPointsEvent.getViewportSize().getHeight());
		drawPanelController.insertRandomPoints(type.getNumber(), randomPointsEvent.getUpperLeftCornerOfViewPortX(), randomPointsEvent.getUpperLeftCornerOfViewPortY(), randomPointsEvent.getViewportSize() );		
	}
	
	@Override
	public void viewEventOccured(ViewEvent viewEvent) {
		drawPanelController.setShowConvexHull(viewEvent.convexHullIsDisplayed());
		drawPanelController.setShowDiameter(viewEvent.DiameterIsDisplayed());
		drawPanelController.setShowQuadrangle(viewEvent.QuadrangleIsDisplayed());
		drawPanelController.setShowTriangle(viewEvent.TriangleIsDisplayed());
		
		drawPanelController.updateModel();
		drawPanelController.updateView();
	}

	@Override
	public void showManualEventOccured() {
		File file = new File("C:\\Users\\mkuhn\\eclipse-workspace\\ProPra_22_Workspace\\CHGO_8493367_Kuhn_Manuel\\src\\propra22\\q8493367\\Manual\\manual.html");
		try {
			URL url = file.toURI().toURL();
			view.showManual(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}	
	}	
}
