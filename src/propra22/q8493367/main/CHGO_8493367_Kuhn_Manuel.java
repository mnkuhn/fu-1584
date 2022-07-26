package propra22.q8493367.main;
import javax.swing.SwingUtilities;

import propra22.interfaces.IHullCalculator;
import propra22.q8493367.animation.QuadrangleSequence;
import propra22.q8493367.animation.TangentPair;
import propra22.q8493367.contour.IDiameter;
import propra22.q8493367.contour.IHull;
import propra22.q8493367.contour.IPointSet;
import propra22.q8493367.contour.IQuadrangle;
import propra22.q8493367.draw.controller.DrawPanelController;
import propra22.q8493367.draw.controller.IDrawPanelControllerObserver;
import propra22.q8493367.draw.model.PointSet;
import propra22.q8493367.draw.view.DrawPanel;
import propra22.q8493367.draw.view.DrawPanelListener;
import propra22.q8493367.draw.view.IDrawPanelListener;
import propra22.q8493367.file.FileManager;
import propra22.q8493367.file.IFileManager;
import propra22.q8493367.file.Parser;
import propra22.q8493367.status.StatusBar;
import propra22.q8493367.status.StatusBarController;
import propra22.q8493367.settings.Settings;
import propra22.q8493367.shape.Diameter;
import propra22.q8493367.shape.Hull;
import propra22.q8493367.shape.Quadrangle;
import propra22.q8493367.test.HullCalculator;
import propra22.tester.Tester;




/**
 * The Class CHGO_8493367_Kuhn_Manuel is the 
 * class which contains the entry point of 
 * the program i.e. the public static void main(String[])
 * method. This function can also invoke the test 
 * environment if it takes the argument -t.
 */
public class CHGO_8493367_Kuhn_Manuel {
	
	/**
	 * Creates the and show GUI.
	 */
	private static void createAndShowGUI() {
		
		//Model of the draw panel
		IPointSet pointSet = new PointSet();
		IHull hull = new Hull();
		IDiameter diameter = new Diameter();
		IQuadrangle quadrangle = new Quadrangle();
		
		QuadrangleSequence quadrangleSequence = new QuadrangleSequence();
		TangentPair tangentPair = new TangentPair(quadrangleSequence);
		
		//Draw panel and controller of the draw panel
		DrawPanel drawPanel = new DrawPanel(pointSet, hull, diameter, quadrangle, tangentPair, quadrangleSequence);
		DrawPanelController drawPanelController = new DrawPanelController(pointSet, hull, diameter, quadrangle, tangentPair, quadrangleSequence, drawPanel);
		IDrawPanelListener drawPanelListener = new DrawPanelListener(drawPanelController);
		drawPanel.setDrawPanelListener(drawPanelListener);
		
		//Status bar and controller of the status bar
		StatusBar statusBar = new StatusBar();
		IDrawPanelControllerObserver statusBarController = new StatusBarController(statusBar);
		drawPanelController.addObserver(statusBarController);
		
		//File manager and parser
		Parser parser = new Parser();
		IFileManager fileManager = new FileManager((PointSet)pointSet, (DrawPanelController)drawPanelController, (Parser)parser);
		
		//Main window
		MainWindow mainWindow = new MainWindow(drawPanel, statusBar);
		mainWindow.setTitle(Settings.title);
		IMainWindowListener mainWindowController = new MainWindowController(drawPanelController, pointSet, mainWindow, fileManager);
		mainWindow.setMainWindowListener(mainWindowController);
		mainWindow.setVisible(true);
		
		
		
	}
	
	/**
	 * The entry point of the program.
	 *
	 * @param args the arguments
	 */
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
