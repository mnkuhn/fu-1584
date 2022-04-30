package propra22.q8493367.draw.view;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import propra22.q8493367.point.PointEvent;
import propra22.q8493367.settings.Settings;



public class DrawPanel extends JPanel implements IDrawPanel {
	
	private int preferredWidth = 700;
	private int preferredHeight = 500;
	private IDrawPanelListener drawPanelListener;

	public DrawPanel() {
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		preferredWidth = (int)(screenSize.width * Settings.panelToScreenWidhtRatio); 
		preferredHeight = (int)(screenSize.height * Settings.panelToScreenHeightRatio);
		
		addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("DrawPanel mouseClicked");
				System.out.println(SwingUtilities.isEventDispatchThread());
				if(SwingUtilities.isLeftMouseButton(e)) {
					drawPanelListener.pointInsertionEventOccured(new PointEvent(e.getSource(), e.getX(), e.getY()));
				}
				else if(SwingUtilities.isRightMouseButton(e)) {
					drawPanelListener.pointDeletionEventOccured(new PointEvent(e.getSource(), e.getX(), e.getY()));
				}	
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				 if(SwingUtilities.isLeftMouseButton(e) && e.isControlDown()) {
					 drawPanelListener.dragInitializedEventOccured(new PointEvent(e.getSource(), e.getX(), e.getY()));
				 }
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				if(SwingUtilities.isLeftMouseButton(e) && e.isControlDown()) {
					drawPanelListener.dragEventEnded(new PointEvent(e.getSource(), e.getX(), e.getY()));
				}
			}
		});
		

		addMouseMotionListener(new MouseMotionAdapter() {
			
			@Override
			public void mouseDragged(MouseEvent e) {
				if(SwingUtilities.isLeftMouseButton(e) && e.isControlDown()) {
					drawPanelListener.dragEventOccured(new PointEvent(e.getSource(), e.getX(), e.getY()));
				}	
			}
		});
	}
		
	
	@Override
	public void setDrawPanelListener(IDrawPanelListener drawPanelListener) {
    	this.drawPanelListener = drawPanelListener;
    }
	
	
    @Override
    public void update() {
    	repaint();
    }
	
    
    @Override
    public Dimension getPreferredSize() {
    	return new Dimension(preferredWidth, preferredHeight);
    }
    
    @Override
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	drawPanelListener.paintEventOccured(g);
    }   
}
