package propra22.q8493367.test;

import java.io.File;
import java.io.IOException;

import propra22.interfaces.IHullCalculator;
import propra22.q8493367.convex.DiameterCalculator;
import propra22.q8493367.draw.controller.DrawPanelController;
import propra22.q8493367.draw.controller.IDrawPanelController;
import propra22.q8493367.draw.model.PointSet;
import propra22.q8493367.draw.model.Quadrangle;
import propra22.q8493367.file.FileManager;
import propra22.q8493367.file.IParser;
import propra22.q8493367.file.Parser;
import propra22.q8493367.draw.model.Diameter;
import propra22.q8493367.draw.model.Hull;
import propra22.q8493367.draw.model.IDiameter;
import propra22.q8493367.draw.model.IHull;
import propra22.q8493367.draw.model.IPointSet;
import propra22.q8493367.draw.model.IQuadrangle;
import propra22.q8493367.point.IPoint;
import propra22.q8493367.point.Point;

public class HullCalculator implements IHullCalculator{
	
	IPointSet pointSet;
	IHull hull;
	IDiameter diameter;
	IQuadrangle quadrangle;
	
	DrawPanelController drawPanelController;
	FileManager fileManager;
	
	public HullCalculator() {
		pointSet = new PointSet();
		hull = new Hull();
		diameter = new Diameter();
		quadrangle = new Quadrangle();
		
		drawPanelController = new DrawPanelController(pointSet, hull, diameter, quadrangle);
		
		IParser parser = new Parser();
		fileManager = new FileManager(pointSet, parser);
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
