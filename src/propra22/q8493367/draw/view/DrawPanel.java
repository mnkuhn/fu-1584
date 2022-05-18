package propra22.q8493367.draw.view;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


import propra22.q8493367.settings.Settings;



// TODO: Auto-generated Javadoc
/**
 * The Class DrawPanel extends JPanel. It shows all the graphical representations 
 * like points, the convex hull, triangles and rectangles.
 */
public class DrawPanel extends JPanel implements IDrawPanel {
	
	//The preferred width of the draw panel.
	private int preferredWidth = 750;
	
	
	// The preferred height of the draw panel.
	private int preferredHeight = 600;
	
	//The draw panel listener.
	private IDrawPanelListener drawPanelListener;

	/**
	 * Instantiates a new draw panel.
	 */
	public DrawPanel() {
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		preferredWidth = (int)(screenSize.width * Settings.panelToScreenWidhtRatio); 
		preferredHeight = (int)(screenSize.height * Settings.panelToScreenHeightRatio);
		
		addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(SwingUtilities.isLeftMouseButton(e)) {
					drawPanelListener.drawPanelEventOccured(new DrawPanelEvent(DrawPanelEventType.INSERT_POINT, e.getSource(), e.getX(), e.getY(), null));
				}
				else if(SwingUtilities.isRightMouseButton(e)) {
					drawPanelListener.drawPanelEventOccured(new DrawPanelEvent(DrawPanelEventType.DELETE_POINT, e.getSource(), e.getX(), e.getY(), null));
				}	
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				 if(SwingUtilities.isLeftMouseButton(e) && e.isControlDown()) {
					 drawPanelListener.drawPanelEventOccured(new DrawPanelEvent(DrawPanelEventType.DRAG_POINT_INITIALIZED, e.getSource(), e.getX(), e.getY(), null));
				 }
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				if(SwingUtilities.isLeftMouseButton(e) && e.isControlDown()) {
					drawPanelListener.drawPanelEventOccured(new DrawPanelEvent(DrawPanelEventType.DRAG_POINT_ENDED, e.getSource(), e.getX(), e.getY(), null));
				}
			}
		});
		

		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if(SwingUtilities.isLeftMouseButton(e) && e.isControlDown()) {
					drawPanelListener.drawPanelEventOccured(new DrawPanelEvent(DrawPanelEventType.DRAG_POINT, e.getSource(), e.getX(), e.getY(), null));
				}	
			}
		});
	}
		
	
	/**
	 * Sets the draw panel listener to the draw panel.
	 *
	 * @param drawPanelListener - the new draw panel listener
	 */
	@Override
	public void setDrawPanelListener(IDrawPanelListener drawPanelListener) {
    	this.drawPanelListener = drawPanelListener;
    }
	
	
    /**
     * Updates the draw panel
     */
    @Override
    public void update() {
    	repaint();
    }
	
    
    /**
     * Returns the preferred size of the draw panel
     *
     * @return the preferred size
     */
    @Override
    public Dimension getPreferredSize() {
    	return new Dimension(preferredWidth, preferredHeight);
    }
    
    /**
     * Returns the minimum size of the draw panel
     *
     * @return the minimum size
     */
    @Override
    public Dimension getMinimumSize() {
    	return new Dimension(preferredWidth, preferredHeight);
    }
    
    /**
     * The overwritten paintComponent method from JPanel
     *
     * @param g - the Graphics object on which the painting is done
     */
    @Override
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	drawPanelListener.drawPanelEventOccured(new DrawPanelEvent(DrawPanelEventType.PAINT, DrawPanel.this, -1, -1, g));
    }   
}
