package propra22.q8493367.main;

import java.io.File;
import java.io.IOException;

import propra22.interfaces.IHullCalculator;
import propra22.q8493367.controllers.DrawPanelController;
import propra22.q8493367.entities.ConventionalParser;
import propra22.q8493367.entities.Diameter;
import propra22.q8493367.entities.Hull;
import propra22.q8493367.entities.IHull;
import propra22.q8493367.entities.PointSet;
import propra22.q8493367.entities.Quadrangle;
import propra22.q8493367.entities.QuadrangleSequence;
import propra22.q8493367.usecases.FileManager;



/**
 * The Class HullCalculator implements IHullCalculator which
 * is the test interface for this application.
 */
public class HullCalculator implements IHullCalculator{
	
	/** The point set. */
	private PointSet pointSet;
	
	/** The hull. */
	private IHull hull;
	
	/** The diameter. */
	private Diameter diameter;
	
	/** The quadrangle. */
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
		this.hull = new Hull();
		this.diameter = new Diameter();
		this.quadrangle = new Quadrangle();
		this.quadrangleSequence = new QuadrangleSequence();
		this.drawPanelController = new DrawPanelController(pointSet, hull, diameter, quadrangle, 
				quadrangleSequence);
		
		this.fileManager = new FileManager(pointSet, drawPanelController, null, new ConventionalParser());
	}
	
	
	@Override
	public void addPoint(int arg0, int arg1) { 
		drawPanelController.insertPointToPointSetByFileInput(arg0, arg1);	
	}

	@Override
	public void addPointsFromArray(int[][] arg0) {
		for(int i = 0; i < arg0.length; i++) {
			drawPanelController.insertPointToPointSetByFileInput(arg0[i][0], arg0[i][1]);
		}
	}

	@Override
	public void addPointsFromFile(String arg0) throws IOException {
		fileManager.loadPointsToPointSet(new File(arg0));
		drawPanelController.updateModel();
	}

	@Override
	public void clearPoints() {
		drawPanelController.clearModel();
		
	}

	@Override
	public int[][] getConvexHull() {
		return drawPanelController.hullAsArray();
	}

	@Override
	public int[][] getDiameter() {
		return diameter.asArray();
	}

	@Override
	public double getDiameterLength() {
		return drawPanelController.getDiameterLength();
	}

	@Override
	public String getEmail() {
		return "manuel.kuhn@studium.fernuni-hagen.de";
	}

	@Override
	public String getMatrNr() {
		return "8493367";
	}

	@Override
	public String getName() {
		return "Manuel Kuhn";
	}

	@Override
	public int[][] getQuadrangle() {
		return quadrangle.asArray();
	}
	
	@Override
	public double getQuadrangleArea() {
		return quadrangle.area();
	}

	@Override
	public int[][] getTriangle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getTriangleArea() {
		// TODO Auto-generated method stub
		return 0;
	}
}
