package propra22.q8493367.main;

import java.io.File;
import java.io.IOException;

import propra22.interfaces.IHullCalculator;
import propra22.q8493367.controllers.DrawPanelController;
import propra22.q8493367.entities.Diameter;
import propra22.q8493367.entities.ArrayListHull;
import propra22.q8493367.entities.IHull;
import propra22.q8493367.entities.PointSet;
import propra22.q8493367.entities.Quadrangle;
import propra22.q8493367.entities.QuadrangleSequence;
import propra22.q8493367.usecases.FileManager;
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
	
	/** The draw panel controller. */
	private DrawPanelController drawPanelController;
	
	/** The file manager. */
	private FileManager fileManager;
    
	/** The sequence of quadrangles as used by the animation. */
	private QuadrangleSequence quadrangleSequence;
	
	/**
	 * Instantiates a new hull calculator.
	 */
	public HullCalculator() {
		this.pointSet = new PointSet();
		this.hull = new ArrayListHull();
		this.diameter = new Diameter();
		this.quadrangle = new Quadrangle();
		this.quadrangleSequence = new QuadrangleSequence();
		this.drawPanelController = new DrawPanelController(pointSet, hull, diameter, quadrangle, 
				quadrangleSequence);
		IParser parser = new ParserFactory().getParser(FileSettings.defaultParserName);
		this.fileManager = new FileManager(pointSet, drawPanelController, parser);
	}
	
	/**
	 * Add a point to the point set.
	 * @param arg0 the x coordinate of the point
	 * @param arg1 the y coordinate of the point
	 */
	@Override
	public void addPoint(int arg0, int arg1) { 
		drawPanelController.insertPointToPointSetByFileInput(arg0, arg1);	
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
			drawPanelController.insertPointToPointSetByFileInput(arg0[i][0], arg0[i][1]);
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
	 */

	@Override
	public int[][] getDiameter() {
		return diameter.asArray();
	}
	
	/**
	 * Returns the length of the diameter.
	 */

	@Override
	public double getDiameterLength() {
		return drawPanelController.getDiameterLength();
	}
	
	/**
	 * Returns the Email address of the
	 * participant.
	 */

	@Override
	public String getEmail() {
		return "manuel.kuhn@studium.fernuni-hagen.de";
	}
    
	/**
	 * Returns the  matriculation number of the 
	 * participant.
	 */
	@Override
	public String getMatrNr() {
		return "8493367";
	}
    
	/**
	 * Returns the full name of the 
	 * participant.
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
	 */
	@Override
	public int[][] getTriangle() {
		// TODO Auto-generated method stub
		return null;
	}
    
	/**
	 * Returns the area of the biggest
	 * triangle.
	 */
	@Override
	public double getTriangleArea() {
		// TODO Auto-generated method stub
		return 0;
	}
}
