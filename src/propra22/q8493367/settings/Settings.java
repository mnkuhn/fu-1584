package propra22.q8493367.settings;

import java.awt.Color;


/**
 * The Class Settings keeps the global settings variables
 */
public class Settings {
	
	

	/** The title of the application */
	public static final String title = new String("Convex Hull Calculator - Manuel Kuhn - 8493367");	
	
	/** The default file path */
	public static final String defaultFilePath = "../ProPra-SS22-Basis/data";
	
	
	/** The default manual path */
	public static final String defaultManualPath = "help/manual.html";
	
	
	//Colors
	/** The color used for the convex hull */
	public static final Color convexHullColor = new Color(255, 0, 0, 255);             	// red
	
	/** The color for the diameter */
	public static final Color diameterColor = new Color(0, 0, 255, 255);   				// blue
	
	/** The color used for the quadrangle */
	public static final Color quadrangleColor = new Color(0, 255, 0, 40);  				// green
	
	/** The color used for the triangle*/
	public static final Color triangleColor = new Color(255, 255, 0, 255); 				// yellow
	
	/** The color used for the x and y axis */
	public static final Color axisColor = new Color(112,128,144, 115); 		// slategray
	
	/** The color used for the marking of the selected point */
	public static final Color markingColor = new Color(67,70,75, 255);       // dark steel gray
	
	/** The radius of a point in pixels */
	public static final int radius =  3;
	
	/** The maximum distance a point needs to have from the mouse to be selected*/
	public static final int mouseRadius = 5;
	
	/** The panel to screen widht ratio.*/
	public static final double panelToScreenWidhtRatio = 0.6;
	
	/** The panel to screen height ratio. */
	public static final double panelToScreenHeightRatio = 0.6;
    
	
	// Display shapes
	/** The default setting for the display of the convex hull */
	public static final boolean defaultConvexHullIsShown = true;
	
	/** The default setting for the display of the diameter */
	public static final boolean defaultDiameterIsShown = false;
	
	/** The default setting for the display of the quadrangle  */
	public static final boolean defaultQuadrangleIsShown = false;
	
	/** The default setting for showing the triangle */
	public static final boolean defaultTriangleIsShown = false;
	
	/** The default setting for the display of the animation */
	public static final boolean defaultAnimationIsShown = false;

	
	
}
