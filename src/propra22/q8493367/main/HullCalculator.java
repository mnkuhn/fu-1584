package propra22.q8493367.main;

import java.io.File;
import java.io.IOException;

import propra22.interfaces.IHullCalculator;
import propra22.q8493367.controllers.DrawPanelController;
import propra22.q8493367.controllers.IDrawPanelController;
import propra22.q8493367.entities.Diameter;
import propra22.q8493367.entities.DiameterAndQuadrangleCalculator;
import propra22.q8493367.entities.DittmarTriangleCalculator;
import propra22.q8493367.entities.ArrayListHull;
import propra22.q8493367.entities.IHull;
import propra22.q8493367.entities.ITriangleCalculator;
import propra22.q8493367.entities.PointSet;
import propra22.q8493367.entities.Quadrangle;
import propra22.q8493367.entities.QuadrangleSequence;
import propra22.q8493367.entities.Triangle;
import propra22.q8493367.gui.FileManager;
import propra22.q8493367.usecases.FileSettings;
import propra22.q8493367.util.IParser;
import propra22.q8493367.util.ParserFactory;



/**
 * The Class HullCalculator implements IHullCalculator which
 * is the test interface for this application.
 */
public class HullCalculator implements IHullCalculator{
	
	/** The point set. */
	private PointSet pointSet;
	
	/** The contour polygon or the convex hull. */
	private IHull hull;
	
	/** The diameter. */
	private Diameter diameter;
	
	/** The biggest quadrangle. */
	private Quadrangle quadrangle;
	
	/** The biggest triangle. */
	private Triangle triangle;
	
	/** The draw panel controller. */
	private IDrawPanelController drawPanelController;
	
	/** The file manager. */
	private FileManager fileManager;
    
	/** The sequence of quadrangles as used by the animation. */
	private QuadrangleSequence quadrangleSequence;
	
	/**
	 * Instantiates a new hull calculator by creating the entities and 
	 * calculators. The draw panel controller has a specific role because
	 * it only coordinates the calculations. It does not handle any events
	 * from the draw panel since the draw panel is null internally.
	 */
	public HullCalculator() {
		this.pointSet = new PointSet();
		this.hull = new ArrayListHull();
		this.diameter = new Diameter();
		this.quadrangle = new Quadrangle();
		this.quadrangleSequence = new QuadrangleSequence();
		this.triangle = new Triangle();
	    
		DiameterAndQuadrangleCalculator diameterAndQuadrangleCalculator = new DiameterAndQuadrangleCalculator(hull);
		ITriangleCalculator triangleCalculator = new DittmarTriangleCalculator(hull);
		this.drawPanelController = new DrawPanelController(pointSet, hull, diameter, quadrangle, triangle, 
				quadrangleSequence, diameterAndQuadrangleCalculator, triangleCalculator);
		IParser parser = new ParserFactory().getParser(FileSettings.defaultParserName);
		this.fileManager = new FileManager(pointSet, drawPanelController, parser);
	}
	
	/**
	 * Adds a point to the point set.
	 * @param arg0 the x coordinate of the point
	 * @param arg1 the y coordinate of the point
	 */
	@Override
	public void addPoint(int arg0, int arg1) { 
		drawPanelController.insertPointToPointSetCheckedWithSorting(arg0, arg1);	
	}
    
	/**
	 * Adds points from an array of integers into the point set.
	 * @param arg0 n x 2 the array of the points, where arg0[m][0] is the 
	 * x coordinate of the m-th point and arg[m][1] is the y coordinate 
	 * of the m-th point.
	 */
	@Override
	public void addPointsFromArray(int[][] arg0) {
		for(int i = 0; i < arg0.length; i++) {
			drawPanelController.insertPointToPointSetCheckedWithSorting(arg0[i][0], arg0[i][1]);
		}
	}
    
	/**
	 * Reads points from a file and inserts them into the point
	 * set.
	 * @param arg0 the file path
	 */
	@Override
	public void addPointsFromFile(String arg0) throws IOException {
		fileManager.loadPointsToPointSet(new File(arg0));
		drawPanelController.updateModel();
	}
	
	/**
	 * Removes all points from the point set.
	 */
	@Override
	public void clearPoints() {
		drawPanelController.clearModel();
		
	}
    
	/**
	 * Returns the convex hull as an n x 2 integer array where
	 * the x coordinate of a point is the first element 
	 * and the y coordinate is the second element of the inner 
	 * array.
	 * @return the array with the coordinates of the  
	 * points of the convex hull.
	 */
	@Override
	public int[][] getConvexHull() {
		return drawPanelController.hullAsArray();
	}
	
	/**
	 * Returns the diameter as an 2 x 2 integer array where
	 * the x coordinate of a point is the first element 
	 * and the y coordinate is the second element of the inner 
	 * array.
	 * @return the array with the coordinates of the two points of 
	 * the diameter.
	 */

	@Override
	public int[][] getDiameter() {
		return diameter.asArray();
	}
	
	/**
	 * Returns the length of the diameter.
	 * @return the length of the diameter
	 */

	@Override
	public double getDiameterLength() {
		return drawPanelController.getDiameterLength();
	}
	
	/**
	 * Returns the email address of the
	 * participant.
	 * @return the email address of the participant
	 */

	@Override
	public String getEmail() {
		return "manuel.kuhn@studium.fernuni-hagen.de";
	}
    
	/**
	 * Returns the  matriculation number of the 
	 * participant.
	 * @return the matriculation number of the participant.
	 */
	@Override
	public String getMatrNr() {
		return "8493367";
	}
    
	/**
	 * Returns the full name of the 
	 * participant.
	 * @return the full name of the participant
	 */
	@Override
	public String getName() {
		return "Manuel Kuhn";
	}
    
	/**
	 * Returns the biggest quadrangle as an 4 x 2 integer 
	 * array where the x coordinate of a point is the first 
	 * element and the y coordinate is the second element 
	 * of the inner array.
	 * @return the array with the coordinates of the points 
	 * of the quadrangle
	 */
	@Override
	public int[][] getQuadrangle() {
		return quadrangle.asArray();
	}
	
	/**
	 * Returns the area of the biggest
	 * quadrangle.
	 */
	@Override
	public double getQuadrangleArea() {
		return quadrangle.area();
	}
    
	/**
	 * Returns the biggest triangle as an
	 * 3 x 2 integer array where the x 
	 * coordinate of a point is the first 
	 * element and the y coordinate is the 
	 * second element of the inner array.
	 * @return the array with the coordinates of
	 * the points of the biggest triangle.
	 */
	@Override
	public int[][] getTriangle() {
		return triangle.asArray();
	}
    
	/**
	 * Returns the area of the biggest
	 * triangle.
	 * @return the area of the biggest
	 * triangle.
	 */
	@Override
	public double getTriangleArea() {
		return 0.5d * (double)triangle.area();
	}
}
