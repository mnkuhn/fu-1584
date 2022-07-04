package propra22.q8493367.test;

import java.io.File;
import java.io.IOException;

import propra22.interfaces.IHullCalculator;
import propra22.q8493367.convex.IHull;
import propra22.q8493367.draw.controller.DrawPanelController;
import propra22.q8493367.draw.model.PointSet;
import propra22.q8493367.draw.model.Quadrangle;
import propra22.q8493367.file.FileManager;
import propra22.q8493367.file.IParser;
import propra22.q8493367.file.Parser;
import propra22.q8493367.draw.model.Diameter;
import propra22.q8493367.draw.model.Hull;
import propra22.q8493367.draw.model.IDiameter;
import propra22.q8493367.draw.model.IPointSet;
import propra22.q8493367.draw.model.IQuadrangle;



/**
 * The Class HullCalculator implements IHullCalculator which
 * is the interface put at our disposal to realize the tests.
 */
public class HullCalculator implements IHullCalculator{
	
	/** The point set. */
	IPointSet pointSet;
	
	/** The hull. */
	IHull hull;
	
	/** The diameter. */
	IDiameter diameter;
	
	/** The quadrangle. */
	IQuadrangle quadrangle;
	
	/** The draw panel controller. */
	DrawPanelController drawPanelController;
	
	/** The file manager. */
	FileManager fileManager;
	
	/**
	 * Instantiates a new hull calculator.
	 */
	public HullCalculator() {
		this.pointSet = new PointSet();
		this.hull = new Hull();
		this.diameter = new Diameter();
		this.quadrangle = new Quadrangle();
		this.drawPanelController = new DrawPanelController(pointSet, hull, diameter, quadrangle);
		this.fileManager = new FileManager(pointSet, drawPanelController, new Parser());
	}
	
	/**
	 * Adds a point with x coordinate arg0 and y coordinate arg1 to the point set.
	 *
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 */
	@Override
	public void addPoint(int arg0, int arg1) { 
		drawPanelController.insertPointToPointSetByFileInput(arg0, arg1);	
	}

	/**
	 * Adds  points from array to the point set.
	 *
	 * @param arg0 the arg 0
	 */
	@Override
	public void addPointsFromArray(int[][] arg0) {
		for(int i = 0; i < arg0.length; i++) {
			drawPanelController.insertPointToPointSetByFileInput(arg0[i][0], arg0[i][1]);
		}
	}

	/**
	 * Adds the points from file to the point set.
	 *
	 * @param arg0 the arg 0
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	public void addPointsFromFile(String arg0) throws IOException {
		fileManager.loadPointsToPointSet(new File(arg0));
		drawPanelController.updateModel();
	}

	/**
	 * Clear points.
	 */
	@Override
	public void clearPoints() {
		drawPanelController.clearModel();
		
	}

	/**
	 * Gets the convex hull.
	 *
	 * @return the convex hull
	 */
	@Override
	public int[][] getConvexHull() {
		return drawPanelController.hullAsArray();
	}

	/**
	 * Gets the diameter of the convex hull.
	 *
	 * @return the diameter
	 */
	@Override
	public int[][] getDiameter() {
		return diameter.asArray();
	}

	/**
	 * Gets the length of the diameter of the convex hull.
	 *
	 * @return the diameter length
	 */
	@Override
	public double getDiameterLength() {
		return drawPanelController.getDiameterLength();
	}

	/**
	 * Gets the email address of the student
	 *
	 * @return the email
	 */
	@Override
	public String getEmail() {
		return "manuel.kuhn@studium.fernuni-hagen.de";
	}

	/**
	 * Gets the matriculation number of the student.
	 *
	 * @return the matriculation number.
	 */
	@Override
	public String getMatrNr() {
		return "8493367";
	}

	/**
	 * Gets the name of the student.
	 *
	 * @return the name of the student.
	 */
	@Override
	public String getName() {
		return "Manuel Kuhn";
	}

	/**
	 * Gets the biggest quadrangel of the convex hull.
	 *
	 * @return the biggest quadrangle.
	 */
	@Override
	public int[][] getQuadrangle() {
		return quadrangle.asArray();
	}

	/**
	 * Gets the quadrangle area of the biggest quadrangle
	 *
	 * @return the quadrangle area of the biggest quadrangle
	 */
	@Override
	public double getQuadrangleArea() {
		return quadrangle.area();
	}

	/**
	 * Gets the biggest triangle of the convex hull.
	 *
	 * @return the biggest triangle of the convex hull
	 */
	@Override
	public int[][] getTriangle() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Gets the triangle area of the biggest triangle.
	 *
	 * @return the triangle area of the biggest triangle
	 */
	@Override
	public double getTriangleArea() {
		// TODO Auto-generated method stub
		return 0;
	}
}
