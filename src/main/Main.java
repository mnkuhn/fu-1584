package main;

import javax.swing.SwingUtilities;

import controllers.DrawPanelController;
import controllers.IDrawPanelController;
import controllers.IDrawPanelControllerObserver;
import controllers.IFileManager;
import controllers.MainWindowController;
import controllers.StatusBarController;
import entities.ArrayListHull;
import entities.Diameter;
import entities.DiameterAndQuadrangleCalculator;
import entities.DittmarTriangleCalculator;
import entities.IHull;
import entities.ITriangleCalculator;
import entities.PointSet;
import entities.Quadrangle;
import entities.QuadrangleSequence;
import entities.TangentPair;
import entities.Triangle;
import gui.DrawPanel;
import gui.DrawPanelListener;
import gui.FileManager;
import gui.GUISettings;
import gui.IDrawPanelListener;
import gui.IMainWindowListener;
import gui.MainWindow;
import gui.StatusBar;
import usecases.FileSettings;
import util.IParser;
import util.ParserFactory;


/**
 * The Class CHGO_8493367_Kuhn_Manuel contains the entry point of 
 * the program i.e. the public static void main(String[])
 * method. This function can also invoke the test 
 * environment if it takes the argument -t.
 */
public class Main {
	
	
	/** The constant showConsoleOutput determines whether the duration for loading a file 
	 *  and the duration for calculating the contour polygon, the convex hull, the diameter
	 *  and the quadrangle are written to the console.
	 *  */
	public static final boolean showConsoleOutput = false;
	
	/**
	 * Creates the entities, GUI elements and their controllers.
	 */
	private static void createAndShowGUI() {
		
		//Model of the draw panel
		PointSet pointSet = new PointSet();
		IHull hull = new ArrayListHull();
		Diameter diameter = new Diameter();
		Quadrangle quadrangle = new Quadrangle();
		QuadrangleSequence quadrangleSequence = new QuadrangleSequence();
		Triangle triangle = new Triangle();
		TangentPair tangentPair = new TangentPair(quadrangleSequence);
		
		
		/* Calculator for diameter, quadrangle and quadrangle sequence
		Calculator for the triangle
		Draw panel, draw panel listener and controller for the draw panel */
		DiameterAndQuadrangleCalculator diameterAndQuadrangleCalculator = new DiameterAndQuadrangleCalculator(hull);
		ITriangleCalculator triangleCalculator = new DittmarTriangleCalculator(hull);
		DrawPanel drawPanel = new DrawPanel(pointSet, hull, diameter, quadrangle, triangle, tangentPair, quadrangleSequence);
		IDrawPanelController drawPanelController = new DrawPanelController(pointSet, hull, diameter, quadrangle, triangle, 
				tangentPair, quadrangleSequence, diameterAndQuadrangleCalculator, triangleCalculator,  drawPanel);
		IDrawPanelListener drawPanelListener = new DrawPanelListener(drawPanelController);
		drawPanel.setDrawPanelListener(drawPanelListener);
		
		//Status bar and controller of the status bar
		StatusBar statusBar = new StatusBar();
		IDrawPanelControllerObserver statusBarController = new StatusBarController(statusBar);
		drawPanelController.addObserver(statusBarController);
		
		//File manager and parser
		IParser parser = new ParserFactory().getParser(FileSettings.defaultParserName);
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
	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});

	}
}
