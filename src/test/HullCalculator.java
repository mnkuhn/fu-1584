package test;

import java.io.IOException;

import propra22.interfaces.IHullCalculator;
import propra22.q8493367.draw.controller.DrawPanelController;
import propra22.q8493367.draw.controller.IDrawPanelController;
import propra22.q8493367.draw.model.DrawPanelModel;
import propra22.q8493367.draw.model.IDrawPanelModel;
import propra22.q8493367.draw.view.DrawPanel;


import propra22.q8493367.point.Point;

public class HullCalculator implements IHullCalculator{
	
	IDrawPanelModel model = new DrawPanelModel();
	DrawPanel view = new DrawPanel();
	IDrawPanelController drawPanelController;
	
	public HullCalculator() {
		IDrawPanelModel model = new DrawPanelModel();
		DrawPanel view = new DrawPanel();
		drawPanelController = new DrawPanelController(model, view);
	}
	
	@Override
	public void addPoint(int arg0, int arg1) { 
		drawPanelController.insertPoint(new Point(arg0, arg1));	
	}

	@Override
	public void addPointsFromArray(int[][] arg0) {
		for(int i = 0; i < arg0.length; i++) {
			drawPanelController.insertPoint(new Point(arg0[i][0], arg0[i][1]));
		}
	}

	@Override
	public void addPointsFromFile(String arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearPoints() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int[][] getConvexHull() {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMatrNr() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
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
