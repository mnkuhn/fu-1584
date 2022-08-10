package propra22.q8493367.main;

import javax.swing.SwingUtilities;

import propra22.interfaces.IHullCalculator;
import propra22.q8493367.controllers.DrawPanelController;
import propra22.q8493367.controllers.MainWindowController;
import propra22.q8493367.controllers.StatusBarController;
import propra22.q8493367.entities.ConventionalParser;
import propra22.q8493367.entities.Diameter;
import propra22.q8493367.entities.ArrayListHull;
import propra22.q8493367.entities.IHull;
import propra22.q8493367.entities.IParser;
import propra22.q8493367.entities.PointSet;
import propra22.q8493367.entities.Quadrangle;
import propra22.q8493367.entities.QuadrangleSequence;
import propra22.q8493367.entities.TangentPair;
import propra22.q8493367.gui.DrawPanel;
import propra22.q8493367.gui.GUISettings;
import propra22.q8493367.gui.MainWindow;
import propra22.q8493367.gui.StatusBar;
import propra22.q8493367.usecases.FileManager;
import propra22.q8493367.usecases.IFileManager;
import propra22.q8493367.util.DrawPanelListener;
import propra22.q8493367.util.IDrawPanelControllerObserver;
import propra22.q8493367.util.IDrawPanelListener;
import propra22.q8493367.util.IMainWindowListener;
import propra22.tester.Tester;


/**
 * The Class CHGO_8493367_Kuhn_Manuel contains the entry point of 
 * the program i.e. the public static void main(String[])
 * method. This function can also invoke the test 
 * environment if it takes the argument -t.
 */
public class CHGO_8493367_Kuhn_Manuel {
	
	
	/** The constant showConsoleOutput determines whether the duration for loading a file 
	 *  and the duration for calculating the contour polygon, the convex hull, the diameter
	 *  and the quadrangle are written to the console.
	 *  */
	public static final boolean showConsoleOutput = false;
	/**
	 * Creates the and shows the GUI.
	 */
	private static void createAndShowGUI() {
		
		//Model of the draw panel
		PointSet pointSet = new PointSet();
		IHull hull = new ArrayListHull();
		Diameter diameter = new Diameter();
		Quadrangle quadrangle = new Quadrangle();
		QuadrangleSequence quadrangleSequence = new QuadrangleSequence();
		TangentPair tangentPair = new TangentPair(quadrangleSequence);
		
		//Draw panel, draw panel listener and controller of the draw panel
		DrawPanel drawPanel = new DrawPanel(pointSet, hull, diameter, quadrangle, tangentPair, quadrangleSequence);
		DrawPanelController drawPanelController = new DrawPanelController(pointSet, hull, diameter, quadrangle, 
				tangentPair, quadrangleSequence, drawPanel);
		IDrawPanelListener drawPanelListener = new DrawPanelListener(drawPanelController);
		drawPanel.setDrawPanelListener(drawPanelListener);
		
		//Status bar and controller of the status bar
		StatusBar statusBar = new StatusBar();
		IDrawPanelControllerObserver statusBarController = new StatusBarController(statusBar);
		drawPanelController.addObserver(statusBarController);
		
		//File manager and parser
		IParser parser = new ConventionalParser();
		IFileManager fileManager = new FileManager(pointSet, drawPanelController, parser);
		
		//Main window
		MainWindow mainWindow = new MainWindow(drawPanel, statusBar);
		mainWindow.setTitle(GUISettings.title);
		IMainWindowListener mainWindowController = new MainWindowController(drawPanelController, mainWindow, fileManager);
		mainWindow.setMainWindowListener(mainWindowController);
		mainWindow.setVisible(true);
		
		
		
	}
	
	/**
	 * The entry point of the program.
	 *
	 * @param args the command line arguments
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
