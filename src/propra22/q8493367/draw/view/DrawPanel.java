package propra22.q8493367.draw.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JPanel;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;

import propra22.q8493367.contour.SectionType;
import propra22.q8493367.draw.model.IDiameter;
import propra22.q8493367.draw.model.IHull;
import propra22.q8493367.draw.model.IPointSet;
import propra22.q8493367.draw.model.IQuadrangle;
import propra22.q8493367.point.IPoint;
import propra22.q8493367.point.Point;
import propra22.q8493367.settings.Settings;

// TODO: Auto-generated Javadoc
/**
 * The Class DrawPanel extends JPanel. It shows all the graphical
 * representations like points, the convex hull, triangles and rectangles.
 */
public class DrawPanel extends JPanel implements IDrawPanel {

	// the model
	IPointSet pointSet;
	IHull hull;
	IDiameter diameter;
	IQuadrangle quadrangle;


	// Draw panel listener.
	private IDrawPanelListener drawPanelListener;
    
	// Which shape is shown
	private boolean convexHullIsShown = Settings.defaultConvexHullIsShown;
	private boolean diameterIsShown = Settings.defaultDiameterIsShown;
	private boolean quadrangleIsShown = Settings.defaultQuadrangleIsShown;
	private boolean triangleIsShown = Settings.defaultTriangleIsShown;
	
	// Points with x coordinate < 0 or y coordinate < 0
    int pointSetOffsetX = 0;
    int pointSetOffsetY = 0;
	
	// Zoom
	private double scale = 1.0;
	private final double scaleFactor = 1.08;
	private int zoomOffsetX = 0;
	private int zoomOffsetY = 0;
	
	// Drag
	private int initialDragX;
	private int initialDragY;
	
	private int mouseOffsetX;
	private int mouseOffsetY;
	
	private int dragOffsetX;
	private int dragOffsetY;
	
	// Offset (data)
	private int offsetX;
	private int offsetY;
	private int originalWidth;
	private int originalHeight;
	

	/**
	 * Instantiates a new draw panel.
	 */
	public DrawPanel(IPointSet pointSet, IHull hull, IDiameter diameter, IQuadrangle quadrangle) {

		this.pointSet = pointSet;
		this.hull = hull;
		this.diameter = diameter;
		this.quadrangle = quadrangle;

		addMouseListener(new MouseAdapter() {

			

			@Override
			public void mousePressed(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e) && e.isAltDown()) {
					drawPanelListener.drawPanelEventOccured(new DrawPanelEvent(
							DrawPanelEventType.DRAG_POINT_INITIALIZED, e.getSource(), e.getX(), e.getY(), null));
				} else if (SwingUtilities.isLeftMouseButton(e) && !e.isAltDown() && !e.isControlDown()) {
						drawPanelListener.drawPanelEventOccured(new DrawPanelEvent(DrawPanelEventType.INSERT_POINT,
								e.getSource(), e.getX(), e.getY(), null));
				} else if (SwingUtilities.isRightMouseButton(e)) {
						drawPanelListener.drawPanelEventOccured(new DrawPanelEvent(DrawPanelEventType.DELETE_POINT,
								e.getSource(), e.getX(), e.getY(), null));
				} else if(SwingUtilities.isLeftMouseButton(e) && e.isControlDown()) {
						// panel drag initialized
						initialDragX = e.getX();
						initialDragY = e.getY();
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e) && !e.isControlDown() && e.isAltDown()) {
					drawPanelListener.drawPanelEventOccured(new DrawPanelEvent(DrawPanelEventType.DRAG_POINT_ENDED,
							e.getSource(), e.getX(), e.getY(), null));
				
				} else if (SwingUtilities.isLeftMouseButton(e) && e.isControlDown() && !e.isAltDown()) {
					// panel drag ended
					dragOffsetX += mouseOffsetX;
					dragOffsetY += mouseOffsetY;
					mouseOffsetX = 0;
					mouseOffsetY = 0;
				}
			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e) && e.isAltDown()) {
					drawPanelListener.drawPanelEventOccured(
							new DrawPanelEvent(DrawPanelEventType.DRAG_POINT, e.getSource(), e.getX(), e.getY(), null));
				}
				else if(SwingUtilities.isLeftMouseButton(e) && e.isControlDown()) {
					// panel drag
					mouseOffsetX = e.getX() - initialDragX;
					mouseOffsetY = e.getY() - initialDragY;
					repaint();
				}	
			}
		});
		
		addMouseWheelListener(new MouseWheelListener() {
			
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				
				if(e.isControlDown()) {
					double rot = e.getPreciseWheelRotation();
					double d = rot * scaleFactor;
					d = rot > 0 ? 1/d : -d;
					System.out.println("Mouse Position  X:  " + e.getX() + " Mouse Position Y: " + e.getY()) ;
					System.out.println("scale: " + scale);
					System.out.println("d: " + d);
					System.out.println("zoomOffsetX: " + zoomOffsetX);
					System.out.println("zoomOffsetY: " + zoomOffsetY);
					double newzoomOffsetX =  (e.getX() - zoomOffsetX - dragOffsetX - mouseOffsetX)*(1 - d) + zoomOffsetX;
					double newzoomOffsetY =  (e.getY() - zoomOffsetY - dragOffsetY - mouseOffsetY)*(1 - d) + zoomOffsetY;
					zoomOffsetX = (int)newzoomOffsetX;
					zoomOffsetY = (int)newzoomOffsetY;
					
					scale = scale * d;
					repaint();	
				}	 
			}		
		});
		
		addComponentListener(new ComponentListener() {

			@Override
			public void componentResized(ComponentEvent e) {
				if(!pointSet.isEmpty()) {
					
					//repaint();
				}
			}

			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}});
		
	}
	
	/*
	private void updatePreferredSize(double rot) {
				double d = rot * scaleFactor;
				d = rot > 0 ? 1/d : -d;
				int newPreferredWidth = (int)(getWidth() * d);
				int newPreferredHeight = (int)(getHeight() * d);
				setPreferredSize(new Dimension(newPreferredWidth, newPreferredHeight));
			}
	
	*/

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
	public void initialize() {
		originalWidth = getWidth();
		originalHeight = getHeight();
		if(!pointSet.isEmpty()) {
			initializeScale();
			initializeOffsets();	
		}
		repaint();
	}
	
	private void initializeScale() {
		double xScale = (double)(getWidth() - 3 * Settings.radius)/(double)(pointSet.getMaxX() - pointSet.getMinX());
		double yScale = (double)(getHeight() - 3 * Settings.radius)/(double)(pointSet.getMaxY() - pointSet.getMinY());
		scale = Math.min(xScale, yScale);
	}
	
	private void initializeOffsets() {
		offsetX = (int)(((double)getWidth() - (double)(pointSet.getMaxX() + pointSet.getMinX())*scale) / (2 * scale) ); 
		offsetY = (int)(((double)getHeight() - (double)(pointSet.getMaxY() + pointSet.getMinY())*scale) / (2 * scale) ); 	
	}
	
	@Override
	public void update() {
		/*
		System.out.println("drawpanel in update");
		if(!pointSet.isEmpty()) {
			if(pointSet.getMinX() < 0) {pointSetOffsetX = - pointSet.getMinX();}
			if(pointSet.getMinY() < 0) {pointSetOffsetY = - pointSet.getMinY();}
		}
		*/
		//revalidate();
		repaint();
	}
	
	

	//@Override
	/*
	public void updateAfterFileEvent() {
		if(!pointSet.isEmpty()) {
			if(pointSet.getMinX() < 0) {xOffset = - pointSet.getMinX();}
			if(pointSet.getMinY() < 0) {yOffset = - pointSet.getMinY();}
			int preferredWidth = pointSet.getMaxX()  + xOffset + 1;
			int preferredHeight = pointSet.getMaxY()  + yOffset + 1;
			setPreferredSize(new Dimension(preferredWidth, preferredHeight));
		}
		update();
	}
	*/

	/**
	 * Returns the preferred size of the draw panel
	 *
	 * @return the preferred size
	 
	@Override
	public Dimension getPreferredSize() {
		
		return super.getPreferredSize();
	}
	
	*/

	/**
	 * Returns the minimum size of the draw panel
	 *
	 * @return the minimum size
	 */
	@Override
	public Dimension getMinimumSize() {
		return super.getPreferredSize();
	}

	/**
	 * The overwritten paintComponent method from JPanel
	 *
	 * @param g - the Graphics object on which the painting is done
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (!pointSet.isEmpty()) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			drawPoints(g2);
			if (convexHullIsShown) {
				drawHull(g2, Settings.convexHullColor);
			}
			if (diameterIsShown) {
				drawDiameter(g2, Settings.diameterColor);
			}
			if (quadrangleIsShown) {
				drawQuadrangle(g2, Settings.quadrangleColor);
			}
		}
	}

	/**
	 * Draws all points from the point set onto the draw panel.
	 *
	 * @param g2 - the Graphics2D Object which is used for painting
	 */
	private void drawPoints(Graphics2D g2) {
		System.out.println("scale: " + scale);
        System.out.println("offsetX: " + offsetX);
        System.out.println("offsetY: " + offsetY);
        System.out.println("minX: " + pointSet.getMinX());
        System.out.println("minY: " + pointSet.getMinY());
        System.out.println("maxX: " + pointSet.getMaxX());
        System.out.println("maxY: " + pointSet.getMaxY());
        System.out.println("width: " + getWidth());
        System.out.println("height: " + getHeight());
		for (int i = 0; i < pointSet.getNumberOfPoints(); i++) {
			
			IPoint p = pointSet.getPointAt(i);
			
			IPoint translatedPoint = translatePointFromModelToView(p);
			
			g2.fillOval(translatedPoint.getX() - Settings.radius, translatedPoint.getY() - Settings.radius, 2 * Settings.radius, 2 * Settings.radius);
		}
	}
	
	//eventuell runden
	private IPoint translatePointFromModelToView(IPoint point) {
		double panelScaleX = (double)getWidth()/(double)originalWidth;
		double panelScaleY = (double)getHeight()/(double)originalHeight;
		double panelScale = Math.min(panelScaleX, panelScaleY);
		System.out.println("drawPanel: " + panelScale);
		
		int x = (int) (translatePointXFromModelToView(point) * panelScale);
		int y = (int) (translatePointYFromModelToView(point) * panelScale);
		return new Point(x, y);
	}

	private int translatePointYFromModelToView(IPoint p) {
		int y = (int)((p.getY() + offsetY )*scale + dragOffsetY + mouseOffsetY + zoomOffsetY);
		return y;
	}

	private int translatePointXFromModelToView(IPoint p) {
		int x = (int)((p.getX() + offsetX )*scale + dragOffsetX + mouseOffsetX + zoomOffsetX);
		return x;
	}



	/**
	 * Draws the convex hull.
	 *
	 * @param g2    - the Graphics2D Object which is used for painting
	 * @param color - the color for the hull
	 */
	private void drawHull(Graphics2D g2, Color color) {
		g2.setColor(color);
		
		for (SectionType sectionType : SectionType.values()) {
			int sectionSize = hull.getSizeOfSection(sectionType);
			if (sectionSize > 1) {
				for (int i = 0; i < sectionSize - 1; i++) {

					IPoint first = hull.getPointFromSection(i, sectionType);
					IPoint translatedFirst = translatePointFromModelToView(first);
					IPoint second = hull.getPointFromSection(i + 1, sectionType);
					IPoint translatedSecond = translatePointFromModelToView(second);
					g2.drawLine(translatedFirst.getX(), translatedFirst.getY(), translatedSecond.getX(), translatedSecond.getY());
				}
			}
			sectionSize = hull.getSizeOfSection(SectionType.LOWERLEFT);
			IPoint lastLeft = hull.getPointFromSection(sectionSize - 1, SectionType.LOWERLEFT);
			IPoint translatedLastLeft = translatePointFromModelToView(lastLeft);
			
			sectionSize = hull.getSizeOfSection(SectionType.LOWERRIGHT);
			IPoint lastRight = hull.getPointFromSection(sectionSize - 1, SectionType.LOWERRIGHT);
			IPoint translatedLastRight = translatePointFromModelToView(lastRight);
			g2.drawLine(translatedLastLeft.getX(), translatedLastLeft.getY(), translatedLastRight.getX(), translatedLastRight.getY());

			sectionSize = hull.getSizeOfSection(SectionType.UPPERLEFT);
			lastLeft = hull.getPointFromSection(sectionSize - 1, SectionType.UPPERLEFT);
			translatedLastLeft = translatePointFromModelToView(lastLeft);
			sectionSize = hull.getSizeOfSection(SectionType.UPPERRIGHT);
			lastRight = hull.getPointFromSection(sectionSize - 1, SectionType.UPPERRIGHT);
			translatedLastRight = translatePointFromModelToView(lastRight);
			g2.drawLine(translatedLastLeft.getX(), translatedLastLeft.getY(), translatedLastRight.getX(), translatedLastRight.getY());

		}
		g2.setColor(Color.BLACK);
	}

	/**
	 * Draw diameter.
	 *
	 * @param g2    the g 2
	 * @param color the color
	 */
	private void drawDiameter(Graphics2D g2, Color color) {
		if (diameter != null) {
			g2.setColor(color);
			IPoint a = diameter.getA();
			IPoint translatedA = translatePointFromModelToView(a);
			IPoint b = diameter.getB();
			IPoint translatedB = translatePointFromModelToView(b);
			g2.drawLine(translatedA.getX(), translatedA.getY(), translatedB.getX(), translatedB.getY());
			g2.setColor(Color.BLACK);
		}
	}

	/**
	 * Draw quadrangle.
	 *
	 * @param g2    the g 2
	 * @param color the color
	 */
	private void drawQuadrangle(Graphics2D g2, Color color) {
		if (quadrangle != null) {
			g2.setColor(color);

			IPoint a = quadrangle.getA();
			IPoint translatedA = translatePointFromModelToView(a);
			IPoint b = quadrangle.getB();
			IPoint translatedB = translatePointFromModelToView(b);
			IPoint c = quadrangle.getC();
			IPoint translatedC = translatePointFromModelToView(c);
			IPoint d = quadrangle.getD();
			IPoint translatedD = translatePointFromModelToView(d);
            int[] xPoints = new int[]{translatedA.getX(), translatedB.getX(), translatedC.getX(), translatedD.getX()};
            int[] yPoints = new int[]{translatedA.getY(), translatedB.getY(), translatedC.getY(), translatedD.getY()};
			Polygon quadrangle = new Polygon(xPoints, yPoints, 4);
			
			g2.fillPolygon(quadrangle);

			g2.setColor(Color.BLACK);
		}
	}

	@Override
	public void setConvexHullIsShown(boolean convexHullIsShown) {
		this.convexHullIsShown = convexHullIsShown;
	}

	@Override
	public void setDiameterIsShown(boolean diameterIsShown) {
		this.diameterIsShown = diameterIsShown;
	}

	@Override
	public void setQuadrangleIsShown(boolean quadrangleIsShown) {
		this.quadrangleIsShown = quadrangleIsShown;
	}

	@Override
	public void setTriangleIsShown(boolean b) {
		triangleIsShown = b;
	}

	@Override
	public boolean convexHullIsShown() {
		return convexHullIsShown;
	}

	@Override
	public boolean diameterIsShown() {
		return diameterIsShown;
	}

	@Override
	public boolean quadrangleIsShown() {
		return quadrangleIsShown;
	}

	@Override
	public boolean triangleIsShown() {
		return triangleIsShown;
	}
}
