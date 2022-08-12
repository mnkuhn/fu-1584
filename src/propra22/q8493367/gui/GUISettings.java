package propra22.q8493367.gui;

import java.awt.Color;


/**
 * The Class GUISettings contains the title of the application, the colors for 
 * the draw panel, the default settings for the menus, the radius of the displayed
 * points, the radius of the mouse pointer and some information about the main window
 * size.
 */
public class GUISettings {
	
	/**  The title of the application. */
	public static final String title = new String("Convex Hull Calculator - Manuel Kuhn - 8493367");
	
	//Colors
	/**  The color used for the convex hull. */
	public static final Color convexHullColor = new Color(255, 0, 0, 255);             	// red
	
	/**  The color used for the diameter. */
	public static final Color diameterColor = new Color(0, 0, 255, 255);   				// blue
	
	/**  The color used for the quadrangle. */
	public static final Color quadrangleColor = new Color(0, 255, 0, 40);  				// light green
	
	/**  The color used for the quadrangle which is shown by the animation. */
	public static final Color animatedQuadrangleColor = new Color(0, 255, 0, 110);      // green
	
	/**  The color used for the triangle. */
	public static final Color triangleColor = new Color(55, 239, 213, 52); 				// light blue
	
	/**  The color used for the x and y axis. */
	public static final Color axisColor = new Color(112, 128, 144, 115); 				// slate gray
	
	/**  The color used for the marking of the selected point. */
	public static final Color markingColor = new Color(67, 70, 75, 255);      			// dark steel gray
	
	
	
	// Display shapes
	/**  Determines whether the convex hull is to be shown by default. */
	public static final boolean defaultConvexHullIsShown = true;
	
	/** Determines whether the diameter is to be shown by default. */
	public static final boolean defaultDiameterIsShown = true;
	
	/** Determines whether the biggest quadrangle is to be shown by default. */
	public static final boolean defaultQuadrangleIsShown = true;
	
	/** Determines whether the triangle is to be shown by default. */
	public static final boolean defaultTriangleIsShown = false;
	
	/** Determines whether the animation is to be shown by default. */
	public static final boolean defaultAnimationIsShown = false;
	
	
	
	// Distances and ratios
	/**  The radius of a point in the view. */
	public static final int radius = 3;

	/**
	 * The maximum distance in pixels in the view a point needs to have from the
	 * mouse pointr to be selected.
	 */
	public static final int mouseRadius = 16;

	/** The draw panel width to screen width ratio. */
	public static final double panelToScreenWidhtRatio = 0.6;

	/** The draw panel height to screen height ratio. */
	public static final double panelToScreenHeightRatio = 0.6;
	
	/** The minimum height of the main window.*/
	public static final int minimumHeight = 250;
	
	/** The minimum width of the main window.*/
	public static final int minimumWidth = 750;
	
	/** The margin for the center method of the draw panel
	 *  and the method which inserts a certain number of randomly 
	 *  generated points onto the draw panel.
	 *  Top, left, bottom and right have this one margin.
	 */
	public static final int margin = 2 * GUISettings.radius;
}
