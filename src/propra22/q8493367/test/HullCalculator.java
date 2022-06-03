package propra22.q8493367.test;

import java.io.File;
import java.io.IOException;

import propra22.interfaces.IHullCalculator;
import propra22.q8493367.convex.DiameterAndQuadrangleCalculator;
import propra22.q8493367.draw.controller.DrawPanelController;
import propra22.q8493367.draw.controller.IDrawPanelController;
import propra22.q8493367.draw.model.PointSet;
import propra22.q8493367.draw.model.Quadrangle;
import propra22.q8493367.file.FileManager;
import propra22.q8493367.file.IParser;
import propra22.q8493367.file.Parser;
import propra22.q8493367.draw.model.Diameter;
import propra22.q8493367.draw.model.IDiameter;
import propra22.q8493367.draw.model.IPointSet;
import propra22.q8493367.draw.model.IQuadrangle;
import propra22.q8493367.point.IPoint;
import propra22.q8493367.point.Point;

public class HullCalculator implements IHullCalculator{
	
	IPointSet pointSet;
	DrawPanelController drawPanelController;
	FileManager fileManager;
	
	public HullCalculator() {
		pointSet = new PointSet();
		drawPanelController = new DrawPanelController(pointSet);
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
		IDiameter diameter= drawPanelController.getDiameter();
		int[][] diameterArr = new int[2][2];
		diameterArr[0][0] = diameter.getA().getX();
		diameterArr[0][1] = diameter.getA().getY();
		diameterArr[1][0] = diameter.getB().getX();
		diameterArr[1][1] = diameter.getB().getY();
		return diameterArr;
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
		IQuadrangle biggestQuadrangle = drawPanelController.getBiggestQuadrangle();
		int[][] biggestQuadrangleAsArray = new int[4][2];
		
		biggestQuadrangleAsArray[0][0] = biggestQuadrangle.getA().getX();
		biggestQuadrangleAsArray[0][1] = biggestQuadrangle.getA().getY();
		
		biggestQuadrangleAsArray[1][0] = biggestQuadrangle.getB().getX();
		biggestQuadrangleAsArray[1][1] = biggestQuadrangle.getB().getY();
		
		biggestQuadrangleAsArray[2][0] = biggestQuadrangle.getC().getX();
		biggestQuadrangleAsArray[2][1] = biggestQuadrangle.getC().getY();
		
		biggestQuadrangleAsArray[3][0] = biggestQuadrangle.getD().getX();
		biggestQuadrangleAsArray[3][1] = biggestQuadrangle.getD().getY();
		
		return biggestQuadrangleAsArray;
	}

	@Override
	public double getQuadrangleArea() {
		IQuadrangle biggestQuadrangle = drawPanelController.getBiggestQuadrangle();
		return biggestQuadrangle.area();
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
