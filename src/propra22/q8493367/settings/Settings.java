package propra22.q8493367.settings;

import java.awt.Color;

public class Settings {
	
	

	// Title
	public static String title = new String("Convex Hull Calculator - Manuel Kuhn - 8493367");	
	
	// Default file path
	public static String defaultFilePath = "../ProPra-SS22-Basis/data";
	
	// Default file path for the manual
	public static String defaultManualPath = "help/manual.html";
	
	//Colors
	public static Color convexHullColor = new Color(255, 0, 0, 255);             	// red
	public static Color diameterColor = new Color(0, 0, 255, 255);   				// blue
	public static Color quadrangleColor = new Color(0, 255, 0, 40);  				// green
	public static Color triangleColor = new Color(255, 255, 0, 255); 				// yellow
	public static final Color CoordinateSystemColor = new Color(112,128,144, 115); 	// slategray
	
	// Radius of a point
	public static int radius =  3;
	
	// Point to be selected has to be within the mouse radius
	public static int mouseRadius = 5;
	
	// Screen
	public static double panelToScreenWidhtRatio = 0.6;
	public static double panelToScreenHeightRatio = 0.6;
    
	// Display shapes
	public static boolean defaultConvexHullIsShown = true;
	public static boolean defaultDiameterIsShown = false;
	public static boolean defaultQuadrangleIsShown = false;
	public static boolean defaultTriangleIsShown = false;
	
}
