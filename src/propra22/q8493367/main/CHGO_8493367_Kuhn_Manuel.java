package propra22.q8493367.main;
import javax.swing.SwingUtilities;

import propra22.interfaces.IHullCalculator;
import propra22.q8493367.animation.ITangentPair;
import propra22.q8493367.animation.TangentPair;
import propra22.q8493367.draw.controller.DrawPanelController;
import propra22.q8493367.draw.controller.IDrawPanelControllerObserver;
import propra22.q8493367.draw.model.Diameter;
import propra22.q8493367.draw.model.Hull;
import propra22.q8493367.draw.model.IDiameter;
import propra22.q8493367.draw.model.IHull;
import propra22.q8493367.draw.model.IPointSet;
import propra22.q8493367.draw.model.IQuadrangle;
import propra22.q8493367.draw.model.PointSet;
import propra22.q8493367.draw.model.Quadrangle;
import propra22.q8493367.draw.view.DrawPanel;
import propra22.q8493367.draw.view.DrawPanelListener;
import propra22.q8493367.draw.view.IDrawPanelListener;
import propra22.q8493367.file.FileManager;
import propra22.q8493367.file.IFileManager;
import propra22.q8493367.file.IFileManagerObserver;
import propra22.q8493367.file.Parser;
import propra22.q8493367.status.StatusBar;
import propra22.q8493367.status.StatusBarController;
import propra22.q8493367.settings.Settings;
import propra22.q8493367.test.HullCalculator;
import propra22.tester.Tester;



public class CHGO_8493367_Kuhn_Manuel {
	
	private static void createAndShowGUI() {
		
		//Model of the draw panel
		IPointSet pointSet = new PointSet();
		IHull hull = new Hull();
		IDiameter diameter = new Diameter();
		IQuadrangle quadrangle = new Quadrangle();
		ITangentPair tangentPair = new TangentPair();
		
		//Draw panel and controller of the draw panel
		DrawPanel drawPanel = new DrawPanel(pointSet, hull, diameter, quadrangle, tangentPair);
		DrawPanelController drawPanelController = new DrawPanelController(pointSet, hull, diameter, quadrangle, tangentPair, drawPanel);
		IDrawPanelListener drawPanelListener = new DrawPanelListener(drawPanelController);
		drawPanel.setDrawPanelListener(drawPanelListener);
		
		//Status bar and controller of the status bar
		StatusBar statusBar = new StatusBar();
		IDrawPanelControllerObserver statusBarController = new StatusBarController(statusBar);
		drawPanelController.addObserver(statusBarController);
		
		//File manager
		Parser parser = new Parser();
		IFileManager fileManager = new FileManager((PointSet)pointSet, (DrawPanelController)drawPanelController, (Parser)parser);
		
		//Main window
		MainWindow mainWindow = new MainWindow(drawPanel, statusBar);
		mainWindow.setTitle(Settings.title);
		IMainWindowListener mainWindowController = new MainWindowController(drawPanelController, pointSet, mainWindow, fileManager);
		fileManager.addObserver((IFileManagerObserver)mainWindowController);
		mainWindow.setMainWindowListener(mainWindowController);
		mainWindow.setVisible(true);
		
		
		
	}
	
	public static  void main(String[] args) {
        if(args.length > 0  && "-t".equals(args[0])) {
        	IHullCalculator calculator = new HullCalculator();
        	Tester tester = new Tester(args, calculator);
        	System.out.println(tester.test());
        }
        else {
        	SwingUtilities.invokeLater(new Runnable() {
		      public void run() {
		        createAndShowGUI();
		      }
        	}); 	
        }
	}
}
