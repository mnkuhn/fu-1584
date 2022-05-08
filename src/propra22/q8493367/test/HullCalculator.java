package propra22.q8493367.test;

import java.io.File;
import java.io.IOException;

import propra22.interfaces.IHullCalculator;
import propra22.q8493367.draw.controller.DrawPanelController;
import propra22.q8493367.draw.controller.IDrawPanelController;
import propra22.q8493367.draw.model.DrawPanelModel;
import propra22.q8493367.draw.model.IDrawPanelModel;


import propra22.q8493367.point.Point;

public class HullCalculator implements IHullCalculator{
	
	IDrawPanelModel model = new DrawPanelModel();
	IDrawPanelController drawPanelController;
	
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getDiameterLength() {
		// TODO Auto-generated method stub
		return 0;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getQuadrangleArea() {
		// TODO Auto-generated method stub
		return 0;
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
