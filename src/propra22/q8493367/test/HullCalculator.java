package propra22.q8493367.test;

import java.io.File;
import java.io.IOException;

import propra22.interfaces.IHullCalculator;
import propra22.q8493367.convex.BiggestRectangleCalculator;
import propra22.q8493367.convex.Quadrangle;
import propra22.q8493367.draw.controller.DrawPanelController;
import propra22.q8493367.draw.controller.IDrawPanelController;
import propra22.q8493367.draw.model.DrawPanelModel;
import propra22.q8493367.draw.model.IDrawPanelModel;
import propra22.q8493367.point.IPoint;
import propra22.q8493367.point.Point;

public class HullCalculator implements IHullCalculator{
	
	IDrawPanelModel model = new DrawPanelModel();
	DrawPanelController drawPanelController;
	
	public HullCalculator() {
		IDrawPanelModel model = new DrawPanelModel();
		drawPanelController = new DrawPanelController(model);
	}
	
	@Override
	public void addPoint(int arg0, int arg1) { 
		drawPanelController.insertPointToModel(new Point(arg0, arg1));	
	}

	@Override
	public void addPointsFromArray(int[][] arg0) {
		for(int i = 0; i < arg0.length; i++) {
			drawPanelController.insertPointToModel(new Point(arg0[i][0], arg0[i][1]));
		}
	}

	@Override
	public void addPointsFromFile(String arg0) throws IOException {
		drawPanelController.loadPointsToModel(new File(arg0));	
	}

	@Override
	public void clearPoints() {
		drawPanelController.clearModel();
		
	}

	@Override
	public int[][] getConvexHull() {
		drawPanelController.updateModel();
		return drawPanelController.hullAsArray();
	}

	@Override
	public int[][] getDiameter() {
		drawPanelController.updateModel();
		int[][] diameter = new int[2][2];
		IPoint[] diameterPointArray = drawPanelController.getDiameter();
		diameter[0][0] = diameterPointArray[0].getX();
		diameter[0][1] = diameterPointArray[0].getY();
		diameter[1][0] = diameterPointArray[1].getX();
		diameter[1][1] = diameterPointArray[1].getY();
		return diameter;
	}

	@Override
	public double getDiameterLength() {
		drawPanelController.updateModel();
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
		drawPanelController.updateModel();
		Quadrangle biggestQuadrangle = drawPanelController.getBiggestQuadrangle();
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
		drawPanelController.updateModel();
		Quadrangle biggestQuadrangle = drawPanelController.getBiggestQuadrangle();
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
