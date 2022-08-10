package propra22.q8493367.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import propra22.q8493367.controllers.IDrawPanel;
import propra22.q8493367.entities.Diameter;
import propra22.q8493367.entities.IHull;
import propra22.q8493367.entities.IHullIterator;
import propra22.q8493367.entities.Point;
import propra22.q8493367.entities.PointSet;
import propra22.q8493367.entities.Quadrangle;
import propra22.q8493367.entities.QuadrangleSequence;
import propra22.q8493367.entities.TangentPair;
import propra22.q8493367.util.DrawPanelEvent;
import propra22.q8493367.util.DrawPanelEventType;
import propra22.q8493367.util.IDrawPanelListener;


/**
 * This Class represents the draw panel. The points and all
 * the shapes like the convex hull, the diameter, the quadrangle,
 * the triangle and the animation are displayed on this JPanel.
 * Next to the functionality for the interaction with the user it 
 * also provides a panning and a zoom functionality.
 * 
 *
 * @see <a href="https://stackoverflow.com/questions/13155382/jscrollpane-zoom-relative-to-mouse-position">stackoverflow.com</a>
 * @see <a href="https://stackoverflow.com/questions/13155382/jscrollpane-zoom-relative-to-mouse-position">youtube.com</a>
 * @see <a href="https://medium.com/@benjamin.botto/zooming-at-the-mouse-coordinates-with-affine-transformations-86e7312fd50b">medium.com</a>
 * 
 */
public class DrawPanel extends JPanel implements IDrawPanel {

	/** The Constant serialVersionUID, used to check the version if 
	 * 	this object is serialized.
	 */
	private static final long serialVersionUID = 1L;
	
	// Model
	/** The point set. */
	private PointSet pointSet;
	
	/** The hull. */
	private volatile IHull hull;
	
	/** The diameter. */
	private Diameter diameter;
	
	/** The quadrangle. */
	private Quadrangle quadrangle;
	
	/** The tangent pair. */
	private TangentPair tangentPair;
	
	/** The quadrangle sequence. */
	private QuadrangleSequence quadrangleSequence;
	
	
	//Listener
	/** The draw panel listener. */
	private IDrawPanelListener drawPanelListener;

	//Display
	/**  True, if the convexHull is shown. False otherwise.*/
	private boolean convexHullIsShown = GUISettings.defaultConvexHullIsShown;
	
	/** True, if diameter is shown. False otherwise.*/
	private boolean diameterIsShown = GUISettings.defaultDiameterIsShown;
	
	/** True, if the quadrangle is shown. False otherwise.*/
	private boolean quadrangleIsShown = GUISettings.defaultQuadrangleIsShown;
	
	/** True, if the triangle is shown. False otherwise.*/
	private boolean triangleIsShown = GUISettings.defaultTriangleIsShown;
	
	/** True, if the animation is running. False otherwise.*/
	private boolean animationIsShown = GUISettings.defaultAnimationIsShown;
    
	
	//Zooming and Dragging
	/** The scale for the zoom. */
	private double scale = 1.0f;
	
	/** The scale of the draw panel. This
	 * scale is used to adapt the size of the 
	 * content of the draw panel, when its size
	 * is changed by the user.
	 */
	private double panelScale = 1d;
	
	/** The scale factor. This factor increases or decreases the 
	 * scale value depending on the number of mouse wheel rotations
	 */
	private final double scaleFactor = 1.08d;
	
	/** The x coordinate of the mouse when dragging starts. Used for
	 * the panning functionality.*/
	private double initialDragX = 0;
	
	/** The y coordinate of the mouse when dragging starts. Used for
	 * the panning functionality.*/
	private double initialDragY = 0;

	/**  The x coordinate of the mouse minus initialDragX. Used for
	 * the panning functionality.
	 * */
	private double mouseOffsetX = 0;
	
	/**  The y coordinate of the mouse minus initialDragY. Used for
	 * the panning functionality.
	 */
	private double mouseOffsetY = 0;
    
	/** The outerOffsetX which is used for zooming and dragging
	 * the panel.
	 */
	private double outerOffsetX = 0;
	
	/** The outerOffsetY which is used for zooming and dragging
	 * the panel.
	 */
	private double outerOffsetY = 0;

	/** The innerOffsetX is used to center a set of
	 * points which is loaded from a file
	 * on this draw panel.
	 */
	private double innerOffsetX = 0;
	
	/** The innerOffsetY is used to center a set of
	 * points which is loaded from a file
	 * on this draw panel.
	 */
	private double innerOffsetY = 0;
	
	/** The reference width of the draw panel. The calculation of the 
	 * new panelScale value refers to this value when the window size 
	 * is changed.
	 */
	private double referenceWidth = 0;
	
	/** The reference height of the draw panel. The calculation of the 
	 * new panelScale value refers to this value when the window size 
	 * is changed.
	 */
	private double referenceHeight = 0 ;

	
	

	
	/**
	 * Instantiates a new draw panel.
	 *
	 * @param pointSet  the point set
	 * @param hull  the convex hull
	 * @param diameter  the diameter
	 * @param quadrangle  the biggest quadrangle
	 * @param tangentPair  the tangent pair needed by the animation
	 * @param quadrangleSequence the sequence of quadrangles needed by the animation
	 */
	public DrawPanel(PointSet pointSet, IHull hull, Diameter diameter, Quadrangle quadrangle,
			TangentPair tangentPair, QuadrangleSequence quadrangleSequence) {

		this.pointSet = pointSet;
		this.hull = hull;
		this.diameter = diameter;
		this.quadrangle = quadrangle;
		this.tangentPair = tangentPair;
		this.quadrangleSequence = quadrangleSequence;
		
		
        addMouseListener(new MouseAdapter() {


			@Override
			public void mousePressed(MouseEvent e) {
				int translatedX = (int) Math.round(translateXFromViewToModel(e.getX(), innerOffsetX, outerOffsetX, mouseOffsetX, 
						scale, panelScale));
				int translatedY = (int) Math.round(translateYFromViewToModel(e.getY(), innerOffsetY, outerOffsetY, mouseOffsetY,
						scale, panelScale));

				if (SwingUtilities.isLeftMouseButton(e) && !e.isAltDown() && !e.isControlDown()) {
					// insert point
					drawPanelListener.drawPanelEventOccurred(new DrawPanelEvent(DrawPanelEventType.INSERT_POINT,
							e.getSource(), translatedX, translatedY, scale * panelScale));

				} else if (SwingUtilities.isRightMouseButton(e)) {
					// delete point
					drawPanelListener.drawPanelEventOccurred(new DrawPanelEvent(DrawPanelEventType.DELETE_POINT,
							e.getSource(), translatedX, translatedY, scale * panelScale));

				} else if (SwingUtilities.isLeftMouseButton(e) && e.isAltDown()) {
					// point drag initialized
					drawPanelListener
							.drawPanelEventOccurred(new DrawPanelEvent(DrawPanelEventType.DRAG_POINT_INITIALIZED,
									e.getSource(), translatedX, translatedY, scale * panelScale));

				} else if (SwingUtilities.isLeftMouseButton(e) && e.isControlDown()) {
					// panel drag initialized
					initialDragX = e.getX();
					initialDragY = e.getY();
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e) && !e.isControlDown() && e.isAltDown()) {
					// point drag ended
					int translatedX = (int) Math.round(translateXFromViewToModel(e.getX(), innerOffsetX, outerOffsetX, mouseOffsetX, 
							scale, panelScale));
					int translatedY = (int) Math.round(translateYFromViewToModel(e.getY(), innerOffsetY, outerOffsetY, mouseOffsetY,
							scale, panelScale));
					drawPanelListener.drawPanelEventOccurred(new DrawPanelEvent(DrawPanelEventType.DRAG_POINT_ENDED,
							e.getSource(), translatedX, translatedY, scale * panelScale));

				} else if (SwingUtilities.isLeftMouseButton(e) && e.isControlDown() && !e.isAltDown()) {
					// panel drag ended
					outerOffsetX += mouseOffsetX;
					outerOffsetY += mouseOffsetY;
					mouseOffsetX = 0;
					mouseOffsetY = 0;
				}
			}
			
			
			@Override
			public void mouseEntered(MouseEvent e) {
				int translatedX = (int) Math.round(translateXFromViewToModel(e.getX(), innerOffsetX, outerOffsetX, mouseOffsetX, 
						scale, panelScale));
				int translatedY = (int) Math.round(translateYFromViewToModel(e.getY(), innerOffsetY, outerOffsetY, mouseOffsetY,
						scale, panelScale));
				drawPanelListener.drawPanelEventOccurred(new DrawPanelEvent(DrawPanelEventType.MOUSE_ENTERED, 
						e.getSource(), translatedX, translatedY, scale * panelScale));
			}
			
			public void mouseExited(MouseEvent e) {
				int translatedX = (int) Math.round(translateXFromViewToModel(e.getX(), innerOffsetX, outerOffsetX, mouseOffsetX, 
						scale, panelScale));
				int translatedY = (int) Math.round(translateYFromViewToModel(e.getY(), innerOffsetY, outerOffsetY, mouseOffsetY,
						scale, panelScale));
				drawPanelListener.drawPanelEventOccurred(new DrawPanelEvent(DrawPanelEventType.MOUSE_EXCITED, 
						e.getSource(), translatedX, translatedY, scale * panelScale));
			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e) && e.isAltDown()) {
					// point drag
					int translatedX = (int) Math.round(translateXFromViewToModel(e.getX(), innerOffsetX, outerOffsetX, mouseOffsetX, 
							scale, panelScale));
					int translatedY = (int) Math.round(translateYFromViewToModel(e.getY(), innerOffsetY, outerOffsetY, mouseOffsetY,
							scale, panelScale));
					drawPanelListener.drawPanelEventOccurred(new DrawPanelEvent(DrawPanelEventType.DRAG_POINT,
							e.getSource(), translatedX, translatedY, scale * panelScale));
				} else if (SwingUtilities.isLeftMouseButton(e) && e.isControlDown()) {
					// panel drag
					double tmpMouseOffsetX = ((double)e.getX() - initialDragX)/panelScale;
					double tmpMouseOffsetY = -((double)e.getY() - initialDragY)/panelScale;
					if(OffsetsAndScalesAreValid(innerOffsetX, innerOffsetY, 
							outerOffsetX, outerOffsetX, 
							tmpMouseOffsetX, tmpMouseOffsetY, 
							scale, panelScale)) {
						mouseOffsetX = tmpMouseOffsetX;
						mouseOffsetY = tmpMouseOffsetY;
					}
					update();
				}
			}
			
			@Override
			public void mouseMoved(MouseEvent e) {
				
					int translatedX = (int) Math.round(translateXFromViewToModel(e.getX(), innerOffsetX, outerOffsetX, mouseOffsetX, 
							scale, panelScale));
					int translatedY = (int) Math.round(translateYFromViewToModel(e.getY(), innerOffsetY, outerOffsetY, mouseOffsetY,
							scale, panelScale));
					drawPanelListener.drawPanelEventOccurred(new DrawPanelEvent(DrawPanelEventType.MOUSE_MOVED, 
							e.getSource(), translatedX, translatedY, scale*panelScale));
				}
		});

		addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {

				if (e.isControlDown()) {
					// Zoom
					double rot = e.getPreciseWheelRotation();
					double d = rot * scaleFactor;
					d = rot > 0 ? 1 / d : -d;
					
					double tmpOuterOffsetX = ((double)e.getX()/panelScale)*(1-d) + outerOffsetX*d;
					double tmpOuterOffsetY = (((double)getHeight() - 1d - (double)e.getY())/panelScale)*(1 - d) + outerOffsetY*d;
					
					double tmpScale = scale * d;
					if(OffsetsAndScalesAreValid(innerOffsetX, innerOffsetY, 
							tmpOuterOffsetX, tmpOuterOffsetY, 
							mouseOffsetX, mouseOffsetY, 
							tmpScale, panelScale)) {
						outerOffsetX = tmpOuterOffsetX;
						outerOffsetY = tmpOuterOffsetY;
						scale = tmpScale;
					}
					else {
						// TODO Message on status bar
					}
					update();
				}
			}	
		});
		
		
	

		addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent e) {
				super.componentResized(e);
			               
				if(referenceWidth == 0 && referenceHeight == 0) {
					referenceWidth = ((JPanel)e.getSource()).getWidth();
					referenceHeight = ((JPanel)e.getSource()).getHeight();
				}
				else {
					panelScale = Math.min((double) getWidth() / (double) referenceWidth,
							(double) getHeight() / (double) referenceHeight);
				}
				update();
			}
			
			@Override
			public void componentShown(ComponentEvent e) {
				super.componentShown(e);
				referenceWidth = ((JPanel)e.getSource()).getWidth();
				referenceHeight = ((JPanel)e.getSource()).getHeight();
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


	/**
	 * Draws the points of the point set, the convex hull, the diameter,
	 * the biggest quadrangle and the current state of the animation.
	 *
	 * @param g the Graphics Object used for drawing.
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		// set anti aliasing for a nicer presentation of the points and lines
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		drawAxes(g2);
		if (!pointSet.isEmpty()) {
			
			if (animationIsShown) {
				drawAnimatedQuadrangle(g2);
				drawTangentPair(g2);
			}
			
			drawPoints(g2);
			if (convexHullIsShown) {
				drawHull(g2);
			}
			if (diameterIsShown) {
				drawDiameter(g2);
			}
			if (quadrangleIsShown) {
				drawQuadrangle(g2);
			}
		}
	}

	
	/**
	 * Draws all the points of the point set.
	 * @param g2 the Graphics2D Object used for drawing
	 */
	private void drawPoints(Graphics2D g2) {

		for (int i = 0; i < pointSet.getNumberOfPoints(); i++) {
			Point p = pointSet.getPointAt(i);
			Point translatedPoint = translatePointFromModelToView(p);
			g2.fillOval(translatedPoint.getX() - GUISettings.radius, translatedPoint.getY() - GUISettings.radius,
					2 * GUISettings.radius, 2 * GUISettings.radius);
			if(p.isSelected()) {
				drawMarking(translatedPoint, g2);
			}
		}
	}

	/**
	 * Draws the marking of the selected point.
	 *
	 * @param point the point which is to be marked.
	 * @param g2 the Graphics2D Object used for drawing.
	 */
	private void drawMarking(Point point, Graphics2D g2) {
		g2.setColor(GUISettings.markingColor);
		int x = point.getX() - GUISettings.radius - 3;
		int y = point.getY() - GUISettings.radius - 3;
		
		/* -1 because the height and the width of drawRect seems to 
		 * be the difference between highest pixel and lowest and not the number of pixels
		 */
		int length = 2*GUISettings.radius + 6 - 1; 
		g2.drawRect(x, y, length, length);
		g2.setColor(Color.BLACK);	
	}



	
	/**
	 * Draws the convex hull.
	 *
	 * @param g2  the Graphics2D Object used for drawing
	 */
	private void drawHull(Graphics2D g2) {
		g2.setColor(GUISettings.convexHullColor);

		IHullIterator it = hull.leftIterator();
		Point leftMostPoint = it.getPoint();
		do {
			Point point1 = translatePointFromModelToView(it.getPoint());
			Point point2 = translatePointFromModelToView(it.getNextPoint());
			g2.drawLine(point1.getX(), point1.getY(), point2.getX(), point2.getY());
			it.next();
		}
		while(it.getPoint() != leftMostPoint);
		
		g2.setColor(Color.BLACK);
	}

	/**
	 * Draws the diameter.
	 *
	 * @param g2   the Graphics2D Object used for painting
	 */
	private void drawDiameter(Graphics2D g2) {
		if (diameter != null) {
			g2.setColor(GUISettings.diameterColor);
			Point a = diameter.getA();
			Point translatedA = translatePointFromModelToView(a);
			Point b = diameter.getB();
			Point translatedB = translatePointFromModelToView(b);
			g2.drawLine(translatedA.getX(), translatedA.getY(), translatedB.getX(), translatedB.getY());
			g2.setColor(Color.BLACK);
		}
	}

	/**
	 * Draws the biggest quadrangle.
	 *
	 * @param g2 the Graphics2D Object used for drawing.
	 */
	private void drawQuadrangle(Graphics2D g2) {
		if (quadrangle != null) {
			g2.setColor(GUISettings.quadrangleColor);

			Point translatedA = translatePointFromModelToView(quadrangle.getA());
			Point translatedB = translatePointFromModelToView(quadrangle.getB());
			Point translatedC = translatePointFromModelToView(quadrangle.getC());
			Point translatedD = translatePointFromModelToView(quadrangle.getD());

			int[] xPoints = new int[] { translatedA.getX(), translatedB.getX(), translatedC.getX(),
					translatedD.getX() };
			int[] yPoints = new int[] { translatedA.getY(), translatedB.getY(), translatedC.getY(),
					translatedD.getY() };

			Polygon quadrangle = new Polygon(xPoints, yPoints, 4);
			g2.fillPolygon(quadrangle);

			g2.setColor(Color.BLACK);
		}
	}

	/**
	 * Draws the x and the y axis.
	 *
	 * @param g2 the Graphics2D Object which used for drawing.
	 */
	private void drawAxes(Graphics2D g2) {
		Graphics2D g2d = (Graphics2D) g2.create();
		g2d.setColor(GUISettings.axisColor);
		int x = (int) Math.round(translateXFromModelToView(0, innerOffsetX, outerOffsetX,  mouseOffsetX, 
				 scale, panelScale));
		int y = (int) Math.round(translateYFromModelToView(0, innerOffsetY, outerOffsetY, mouseOffsetY,
				scale, panelScale));
		// ordinate
		g2d.drawLine(x, 0, x, getHeight() - 1);
		// abszissa
		g2d.drawLine(0, y, getWidth() - 1, y);
		g2d.setColor(Color.BLACK);	
	}

	/**
	 * Draws the current state of the tangent pair.
	 *
	 * @param g2 the Graphics2D Object used for drawing
	 */
	private void drawTangentPair(Graphics2D g2) {
		// try and catch in case the tangent pair is not initialized yet
		try {
			 // get the tangents from the tangent pair
			 Point[] tangent1 = tangentPair.getTangent1(); 
			 Point[] tangent2 = tangentPair.getTangent2();
			 
			 extendTangent(tangent1);
			 extendTangent(tangent2);
			 
			 // draw first tangent
			 g2.drawLine(
					 (int)Math.round(translateXFromModelToView(tangent1[0].getX(), innerOffsetX, outerOffsetX,  mouseOffsetX, 
							 scale, panelScale)),
					 (int)Math.round(translateYFromModelToView(tangent1[0].getY(), innerOffsetY, outerOffsetY, mouseOffsetY,
								scale, panelScale)), 
					 (int)Math.round(translateXFromModelToView(tangent1[2].getX(), innerOffsetX, outerOffsetX,  mouseOffsetX, 
							 scale, panelScale)), 
					 (int)Math.round(translateYFromModelToView(tangent1[2].getY(), innerOffsetY, outerOffsetY, mouseOffsetY,
								scale, panelScale)));
			 
			 // draw second tangent
			 g2.drawLine(
					 (int)Math.round(translateXFromModelToView(tangent2[0].getX(), innerOffsetX, outerOffsetX,  mouseOffsetX, 
							 scale, panelScale)),
					 (int)Math.round(translateYFromModelToView(tangent2[0].getY(), innerOffsetY, outerOffsetY, mouseOffsetY,
								scale, panelScale)), 
					 (int)Math.round(translateXFromModelToView(tangent2[2].getX(), innerOffsetX, outerOffsetX,  mouseOffsetX, 
							 scale, panelScale)), 
					 (int)Math.round(translateYFromModelToView(tangent2[2].getY(), innerOffsetY, outerOffsetY, mouseOffsetY,
								scale, panelScale)));
			 
			 /* draw connection of the centers i.e. the connection between the points 
			 of the antipodal pair */
			 g2.drawLine(
					 (int)Math.round(translateXFromModelToView(tangent1[1].getX(), innerOffsetX, outerOffsetX,  mouseOffsetX, 
							 scale, panelScale)),
					 (int)Math.round(translateYFromModelToView(tangent1[1].getY(), innerOffsetY, outerOffsetY, mouseOffsetY,
								scale, panelScale)), 
					 (int)Math.round(translateXFromModelToView(tangent2[1].getX(), innerOffsetX, outerOffsetX,  mouseOffsetX, 
							 scale, panelScale)), 
					 (int)Math.round(translateYFromModelToView(tangent2[1].getY(), innerOffsetY, outerOffsetY, mouseOffsetY,
								scale, panelScale)));
			 
			 
		} catch (NullPointerException e) {
			e.getStackTrace();
		}	 
	}
	
	/**
	 * Draws the current quadrangle of the quadrangle sequence.
	 *
	 * @param g2 the Graphics2D Object used for drawing
	 */
	private void drawAnimatedQuadrangle(Graphics2D g2) {
		if (quadrangleSequence != null) {
			g2.setColor(GUISettings.animatedQuadrangleColor);
            
			Quadrangle quadrangle = quadrangleSequence.getQuadrangle();
			Point translatedA = translatePointFromModelToView(quadrangle.getA());
			Point translatedB = translatePointFromModelToView(quadrangle.getB());
			Point translatedC = translatePointFromModelToView(quadrangle.getC());
			Point translatedD = translatePointFromModelToView(quadrangle.getD());

			int[] xPoints = new int[] { translatedA.getX(), translatedB.getX(), translatedC.getX(),
					translatedD.getX() };
			int[] yPoints = new int[] { translatedA.getY(), translatedB.getY(), translatedC.getY(),
					translatedD.getY() };

			Polygon quadrangleAsPolygon = new Polygon(xPoints, yPoints, 4);
			g2.fillPolygon(quadrangleAsPolygon);

			g2.setColor(Color.BLACK);
		}
	}

	/**
	 * Extends the tangent so that it is longer than twice the diagonal of the 
	 * draw panel.
	 *
	 * @param tangent the tangent
	 */
	private void extendTangent(Point[] tangent) {
		
		double length =  Math.sqrt(Point.qaudraticDistance(tangent[0], tangent[2]))*scale*panelScale;
		double stretch = 2*panelDiagonal()/length;
		
		int dx = (int)Math.round ((tangent[0].getX() - tangent[1].getX())*stretch);
		int dy = (int)Math.round ((tangent[0].getY() - tangent[1].getY())*stretch);
		tangent[0].translate(dx, dy);
		
		dx = (int)Math.round((tangent[2].getX() - tangent[1].getX())*stretch);
		dy = (int)Math.round((tangent[2].getY() - tangent[1].getY())*stretch);
		tangent[2].translate(dx, dy);	
	}


	/**
	 * Returns the length of the diagonal of the 
	 * draw panel.
	 *
	 * @return the diagonal of the panel in pixels.
	 */
	private double panelDiagonal() {
		return Math.sqrt(getWidth() * getWidth() + getHeight() * getHeight());
	}


	
	
	
	
	@Override
	public Dimension getPreferredSize() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int preferredWidth = (int) (screenSize.width * GUISettings.panelToScreenWidhtRatio);
		int preferredHeight = (int) (screenSize.height * GUISettings.panelToScreenHeightRatio);
		return new Dimension(preferredWidth, preferredHeight);
	}	
	
	
    /**
     * {@inheritDoc}
     * If the centering may cause overflow in the model or in the view
     * the centering is prevented.
     */
	@Override
	public void center() {
		setOffsetsToZero();
		scale = 1;
		if (!pointSet.isEmpty()) {
			double tmpScale = 1;
			
			// find the scale
			double xRange = (((double)pointSet.getMaxX() - (double)pointSet.getMinX()) * panelScale);
			double yRange = (((double)pointSet.getMaxY() - (double)pointSet.getMinY()) * panelScale);
			if(xRange != 0 && yRange != 0) {
				 double xScale = ((double)getWidth() - 2 * (double)GUISettings.margin)/(double)xRange;
				 double yScale = ((double)getHeight() - 2 * (double)GUISettings.margin)/(double)yRange;
				 tmpScale=  Math.min(xScale, yScale);
			}
			else if (xRange == 0 && yRange != 0) {
		    	tmpScale = ((double)getHeight() - 2 * (double)GUISettings.margin)/(double)yRange;
		    }
			else if (xRange != 0 && yRange == 0) {
		    	tmpScale = ((double)getWidth() - 2 * (double)GUISettings.margin)/(double)xRange;
		    }
			else if (xRange == 0 && yRange == 0) {
		    	tmpScale = 1;
		    }
		    
		    // find the inner offsets
		    double tmpInnerOffsetX = (double)getWidth()/( 2 * tmpScale * panelScale) - ((double)pointSet.getMinX() + (double)pointSet.getMaxX())/2;
			double tmpInnerOffsetY = (double)getHeight()/(2 * tmpScale * panelScale) - ((double)pointSet.getMinY() + (double)pointSet.getMaxY())/2;
			
			// Check scale and inner offsets
			if(OffsetsAndScalesAreValid(tmpInnerOffsetX, tmpInnerOffsetY, outerOffsetX, outerOffsetY, mouseOffsetX, mouseOffsetY, tmpScale, panelScale)) {
				scale = tmpScale;
				innerOffsetX = tmpInnerOffsetX;
				innerOffsetY = tmpInnerOffsetY;
			}
			else {
				// TODO Display information in the status bar
			}
			
		}
		
		repaint();
	}

	/**
	 * Sets all offsets to zero.
	 */
	private void setOffsetsToZero() {
		
		initialDragX = 0;
		initialDragY = 0;
		mouseOffsetX = 0;
		mouseOffsetY = 0;
		outerOffsetX = 0;
		outerOffsetY = 0;
		innerOffsetX = 0;
		innerOffsetY = 0;
	}
	
	
	
	//Zooming and panning
	/**
	 * Returns a new point with view coordinates,
	 * corresponding to the point with the given model coordinates.
	 *
	 * @param point the point whose coordinates are translated
	 * @return a new point with the translated coordinates
	 * 
	 */
	private Point translatePointFromModelToView(Point point) {
		int x = (int) Math.round(translateXFromModelToView(point.getX(), innerOffsetX, outerOffsetX, mouseOffsetX, 
				scale, panelScale));
		int y = (int) Math.round(translateYFromModelToView(point.getY(), innerOffsetY, outerOffsetY, mouseOffsetY,
				scale, panelScale));
		return new Point(x, y);
	}

	/**
	 * Returns a new point with model coordinates,
	 * corresponding to the point with the given view coordinates.
	 *
	 * @param x the x coordinate to be translated
	 * @param y the y coordinate to be translated
	 * @return a new point with the translated coordinates
	 */
	private Point translatePointFromViewToModel(int x, int y) {
		int translatedX = (int) Math.round(translateXFromViewToModel(x, innerOffsetX, outerOffsetX, mouseOffsetX, 
				scale, panelScale));
		int translatedY = (int) Math.round(translateYFromViewToModel(y, innerOffsetY, outerOffsetY, mouseOffsetY,
				scale, panelScale));
		return new Point(translatedX, translatedY);
	}
	
	
	@Override
	public Point translatePointFromViewToModel(Point point) {
		return translatePointFromViewToModel(point.getX(), point.getY());
	}                                                                                        

	
	/**
	 * Translates an x coordinate from the model coordinate system to the view
	 * coordinate system.
	 *
	 * @param x the x coordinate from the coordinate system in the model.
	 * @param innerOffsetX the inner offset of this projection. {@link DrawPanel#innerOffsetX}
	 * @param outerOffsetX the outer offset of this projection. {@link DrawPanel#outerOffsetX}
	 * @param mouseOffsetX the mouse offset of this projection. {@link DrawPanel#mouseOffsetX }
	 * @param scale the scale value of this projection. {@link DrawPanel#scale}
	 * @param panelScale the panel scale value of this projection. {@link DrawPanel#panelScale}
	 * @return the x coordinate from the coordinate system in the view.
	 */
	private double translateXFromModelToView(int x, double innerOffsetX, double outerOffsetX, double mouseOffsetX, 
			double scale, double panelScale) {
		return (((double) x + innerOffsetX) * scale + outerOffsetX + mouseOffsetX)*panelScale;
	}
	
	/**
	 * Translates an x coordinate from the view coordinate system to the model
	 * coordinate system.
	 *
	 * @param x the x coordinate from the coordinate system in the view.
	 * @param innerOffsetX the inner offset value of this projection. {@link DrawPanel#innerOffsetX}
	 * @param outerOffsetX the outer offset value of this projection. {@link DrawPanel#outerOffsetX}
	 * @param mouseOffsetX the mouse offset value of this projection. {@link DrawPanel#mouseOffsetX}
	 * @param scale the scale value of this projection. {@link DrawPanel#scale}
	 * @param panelScale the panel scale value of this projection. {@link DrawPanel#panelScale}
	 * @return the x coordinate from the coordinate system of the model.
	 */
	private double translateXFromViewToModel(int x, double innerOffsetX, double outerOffsetX, double mouseOffsetX, 
			double scale, double panelScale) {
		return ((double) x / panelScale - outerOffsetX - mouseOffsetX)/scale - innerOffsetX;
	}

	/**
	 * Translates a y coordinate from the model coordinate system to the view
	 * coordinate system.
	 *
	 * @param y the y coordinate from the coordinate system of the model.
	 * @param innerOffsetY the inner offset value of this projection. {@link DrawPanel#innerOffsetY}
	 * @param outerOffsetY the outer offset value of this projection. {@link DrawPanel#outerOffsetY}
	 * @param mouseOffsetY the mouse offset value of this projection. {@link DrawPanel#mouseOffsetY}
	 * @param scale the scale value of this projection. {@link DrawPanel#scale}
	 * @param panelScale the panel scale value of this projection. {@link DrawPanel#panelScale}
	 * @return the y coordinate from the coordinate system of the view.
	 */
	private double translateYFromModelToView(int y, double innerOffsetY, double outerOffsetY, double mouseOffsetY, 
			double scale, double panelScale) {
		return  (double)getHeight() - 1 - (((double)y + innerOffsetY)*scale + outerOffsetY + mouseOffsetY)*panelScale;
	}

	

	/**
	 * Translates a y coordinate from the view coordinate system to the model
	 * coordinate system.
	 *
	 * @param y the y coordinate from the view coordinate system.
	 * @param innerOffsetY the inner offset value of this projection. {@link DrawPanel#innerOffsetY}
	 * @param outerOffsetY the outer offset value of this projection. {@link DrawPanel#outerOffsetY}
	 * @param mouseOffsetY the mouse offset value of this projection. {@link DrawPanel#mouseOffsetY}
	 * @param scale the scale value of  this projection. {@link DrawPanel#scale}
	 * @param panelScale the panel scale value of this projection. {@link DrawPanel#panelScale}
	 * @return the y coordinate from the model coordinate system.
	 */
	private double translateYFromViewToModel(int y, double innerOffsetY, double outerOffsetY, double mouseOffsetY, 
			double scale, double panelScale) {
		return (((double)getHeight() - 1 - (double)y)/panelScale  - outerOffsetY - mouseOffsetY)/scale - innerOffsetY;
	}
	
	
	
	/**
	 * This method checks, if the temporary offset and scale
	 * values do not cause overflow in the model and in the view.
	 * If so, the temporary values are assigned to the respective
	 * variables. If an overflow would occur no assignment takes place
	 * and the zooming or the panning action is ignored.
	 * All the parameters are playing a role in the projection methods:
	 * {@link #translateXFromModelToView(int, double, double, double, double, double)}
	 * {@link #translateYFromViewToModel(int, double, double, double, double, double)}
	 * {@link #translateXFromViewToModel(int, double, double, double, double, double)}
	 * {@link #translateYFromViewToModel(int, double, double, double, double, double)}
	 *
	 * @param innerOffsetX the innerOffsetX.
	 * @param innerOffsetY the innerOffsetY.
	 * @param outerOffsetX the outerOffsetX, this might be a temporary value. 
	 * @param outerOffsetY the outerOffsetY, this might be a temporary value. 
	 * @param mouseOffsetX the mouseOffsetX, this might be a temporary value. 
	 * @param mouseOffsetY the mouseOffsetY, this might be a temporary value. 
	 * @param scale the scale, this might be a temporary value. 
	 * @param panelScale the panel scale
	 * @return true, if the these parameters do not cause overflow in the model
	 * and in the view and if the minimum distances are guaranteed. False otherwise.
	 */
	private boolean OffsetsAndScalesAreValid(
			double innerOffsetX, double innerOffsetY,
			double outerOffsetX, double outerOffsetY,
			double mouseOffsetX, double mouseOffsetY, 
			double scale, double panelScale) {
		
		//Check for overflow in view
		double smallestViewX = translateXFromModelToView(pointSet.getMinX(), 
				innerOffsetX, outerOffsetX, mouseOffsetX, scale, panelScale);
		double smallestViewY = translateYFromModelToView(pointSet.getMinY(), 
				innerOffsetY, outerOffsetY, mouseOffsetY, scale, panelScale);
		double biggestViewX = translateXFromModelToView(pointSet.getMaxX(), 
				innerOffsetX, outerOffsetX, mouseOffsetX, scale, panelScale);
		double biggestViewY = translateYFromModelToView(pointSet.getMaxY(), 
				innerOffsetY, outerOffsetY, mouseOffsetY, scale, panelScale);
		
		
		//Check overflow in model.
		double smallestModelX = translateXFromViewToModel(0, innerOffsetX, 
				outerOffsetX, mouseOffsetX, scale, panelScale);
		double smallestModelY = translateYFromViewToModel(getHeight(), innerOffsetY,
				outerOffsetY, mouseOffsetY, scale, panelScale);
		double biggestModelX = translateXFromViewToModel(getWidth(), innerOffsetX, 
				outerOffsetX, mouseOffsetX, scale, panelScale);
		double biggestModelY = translateYFromViewToModel(0, innerOffsetY,
				outerOffsetY, mouseOffsetY, scale, panelScale);
		
		//Check for minimal range in model.
		double xModelRange = biggestModelX - smallestModelX;
		double yModelRange = biggestModelY - smallestModelY;
		
		
		
		if(     smallestViewX >= Integer.MIN_VALUE 	&&
				smallestViewY >= Integer.MIN_VALUE 	&&
				biggestViewX <= Integer.MAX_VALUE 	&&
				biggestViewY <= Integer.MAX_VALUE 	&&
				smallestModelX >= Integer.MIN_VALUE	&&
				smallestModelY >= Integer.MIN_VALUE &&
				biggestModelX <= Integer.MAX_VALUE 	&&
				biggestModelY <= Integer.MAX_VALUE 	&&
				xModelRange >= 10 					&&
				yModelRange >= 10
				) {
		
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public void setConvexHullIsShown(boolean b) {
		this.convexHullIsShown = b;
	}

	
	@Override
	public void setDiameterIsShown(boolean b) {
		this.diameterIsShown = b;
	}

	
	@Override
	public void setQuadrangleIsShown(boolean b) {
		this.quadrangleIsShown = b;
	}

	
	@Override
	public void setTriangleIsShown(boolean b) {
		triangleIsShown = b;
	}
	
	@Override
	public void setShowAnimation(boolean b) {
		animationIsShown = b;
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
	public boolean animationIsShown() {
		return animationIsShown;
	}


	@Override
	public boolean triangleIsShown() {
		return triangleIsShown;
	}
}
