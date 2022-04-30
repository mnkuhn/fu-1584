package propra22.q8493367.draw.view;
import java.awt.Graphics;

import propra22.q8493367.point.PointEvent;

public interface IDrawPanelListener {
	public void pointInsertionEventOccured(PointEvent e);
	public void pointDeletionEventOccured(PointEvent e);
	public void paintEventOccured(Graphics g);
	public void dragInitializedEventOccured(PointEvent e);
	public void dragEventOccured(PointEvent e);
	public void dragEventEnded(PointEvent e);
}
