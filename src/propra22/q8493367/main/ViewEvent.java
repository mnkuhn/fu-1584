package propra22.q8493367.main;

public class ViewEvent implements IViewEvent {
	
	
	private Object source;
	private boolean showConvexHull;
	private boolean showDiameter;
	private boolean showQuadrangle;
	private boolean showTriangle;

	public ViewEvent(Object source, boolean showConvexHull, boolean showDiameter, boolean showQuadrangle, boolean ShowTriangle) {
		this.source = source;
		this.showConvexHull = showConvexHull;
		this.showDiameter = showDiameter;
		this.showQuadrangle = showQuadrangle;
		this.showTriangle = ShowTriangle;
	}

	public Object getSource() {
		return source;
	}
    
	@Override
	public boolean convexHullIsDisplayed() {
		return showConvexHull;
	}
	@Override
	public boolean DiameterIsDisplayed() {
		return showDiameter;
	}
	@Override
	public boolean QuadrangleIsDisplayed() {
		return showQuadrangle;
	}

	@Override
	public boolean TriangleIsDisplayed() {
		return showTriangle;
	}
}
