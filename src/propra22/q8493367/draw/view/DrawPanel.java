package propra22.q8493367.draw.view;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import propra22.q8493367.animation.QuadrangleSequence;
import propra22.q8493367.animation.TangentPair;
import propra22.q8493367.contour.IDiameter;
import propra22.q8493367.contour.IHull;
import propra22.q8493367.contour.IPointSet;
import propra22.q8493367.contour.IQuadrangle;
import propra22.q8493367.contour.ContourType;
import propra22.q8493367.point.IPoint;
import propra22.q8493367.point.Point;
import propra22.q8493367.settings.Settings;



/**
 * This Class represents the draw panel. The points and all
 * the shapes like the convex hull, the diameter, the quadrangle,
 * the triangle and the animation are displayed on this draw panel.
 * Next to the functionality for the interaction with the user it 
 * also provides a panning and a zoom functionality.
 */
public class DrawPanel extends JPanel implements IDrawPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	// Model
	/** The point set. */
	private IPointSet pointSet;
	
	/** The hull. */
	private volatile IHull hull;
	
	/** The diameter. */
	private IDiameter diameter;
	
	/** The quadrangle. */
	private IQuadrangle quadrangle;
	
	/** The tangent pair. */
	private TangentPair tangentPair;
	
	/** The quadrangle sequence. */
	private QuadrangleSequence quadrangleSequence;
	
	
	//Listener
	/** The draw panel listener. */
	private IDrawPanelListener drawPanelListener;

	//Display
	/**  True, if the convexHull is shown. */
	private boolean convexHullIsShown = Settings.defaultConvexHullIsShown;
	
	/** True, if diameter is shown. */
	private boolean diameterIsShown = Settings.defaultDiameterIsShown;
	
	/** True, if the quadrangle is shown. */
	private boolean quadrangleIsShown = Settings.defaultQuadrangleIsShown;
	
	/** True, if the triangle is shown. */
	private boolean triangleIsShown = Settings.defaultTriangleIsShown;
	
	/** True, if the animation is running. */
	private boolean animationIsShown = Settings.defaultAnimationIsShown;
    
	
	//Zooming and Dragging
	/** The scale for the zoom. */
	private double scale = 1.0f;
	
	/** The scale of the draw panel. This
	 * scale is used to adapt the size of the 
	 * content of the draw panel, when the its size
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
	public DrawPanel(IPointSet pointSet, IHull hull, IDiameter diameter, IQuadrangle quadrangle,
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
				int translatedX = (int) Math.round(translateXFromViewToModel(e.getX()));
				int translatedY = (int) Math.round(translateYFromViewToModel(e.getY()));

				if (SwingUtilities.isLeftMouseButton(e) && !e.isAltDown() && !e.isControlDown()) {
					// insert point
					drawPanelListener.drawPanelEventOccured(new DrawPanelEvent(DrawPanelEventType.INSERT_POINT,
							e.getSource(), translatedX, translatedY, scale * panelScale));

				} else if (SwingUtilities.isRightMouseButton(e)) {
					// delete point
					drawPanelListener.drawPanelEventOccured(new DrawPanelEvent(DrawPanelEventType.DELETE_POINT,
							e.getSource(), translatedX, translatedY, scale * panelScale));

				} else if (SwingUtilities.isLeftMouseButton(e) && e.isAltDown()) {
					// point drag initialized
					drawPanelListener
							.drawPanelEventOccured(new DrawPanelEvent(DrawPanelEventType.DRAG_POINT_INITIALIZED,
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
					int translatedX = (int) Math.round(translateXFromViewToModel(e.getX()));
					int translatedY = (int) Math.round(translateYFromViewToModel(e.getY()));
					drawPanelListener.drawPanelEventOccured(new DrawPanelEvent(DrawPanelEventType.DRAG_POINT_ENDED,
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
				int translatedX = (int) Math.round(translateXFromViewToModel(e.getX()));
				int translatedY = (int) Math.round(translateYFromViewToModel(e.getY()));
				drawPanelListener.drawPanelEventOccured(new DrawPanelEvent(DrawPanelEventType.MOUSE_ENTERED, 
						e.getSource(), translatedX, translatedY, scale * panelScale));
			}
			
			public void mouseExited(MouseEvent e) {
				int translatedX = (int) Math.round(translateXFromViewToModel(e.getX()));
				int translatedY = (int) Math.round(translateYFromViewToModel(e.getY()));
				drawPanelListener.drawPanelEventOccured(new DrawPanelEvent(DrawPanelEventType.MOUSE_EXCITED, 
						e.getSource(), translatedX, translatedY, scale * panelScale));
			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e) && e.isAltDown()) {
					// point drag
					int translatedX = (int) Math.round(translateXFromViewToModel(e.getX()));
					int translatedY = (int) Math.round(translateYFromViewToModel(e.getY()));
					drawPanelListener.drawPanelEventOccured(new DrawPanelEvent(DrawPanelEventType.DRAG_POINT,
							e.getSource(), translatedX, translatedY, scale * panelScale));
				} else if (SwingUtilities.isLeftMouseButton(e) && e.isControlDown()) {
					// panel drag
					mouseOffsetX = ((double)e.getX() - initialDragX)/panelScale;
					mouseOffsetY = -((double)e.getY() - initialDragY)/panelScale;
					
					update();
				}
			}
			
			@Override
			public void mouseMoved(MouseEvent e) {
				
					int translatedX = (int) Math.round(translateXFromViewToModel(e.getX()));
					int translatedY = (int) Math.round(translateYFromViewToModel(e.getY()));
					drawPanelListener.drawPanelEventOccured(new DrawPanelEvent(DrawPanelEventType.MOUSE_MOVED, 
							e.getSource(), translatedX, translatedY, scale*panelScale));
				}
		});

		addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {

				if (e.isControlDown()) {
					// zoom
					double rot = e.getPreciseWheelRotation();
					double d = rot * scaleFactor;
					d = rot > 0 ? 1 / d : -d;
					
					double newOuterOffsetX = ((double)e.getX()/panelScale)*(1-d) + outerOffsetX*d;
					double newOuterOffsetY = (((double)getHeight() - 1d - (double)e.getY())/panelScale)*(1 - d) + outerOffsetY*d;
					
					outerOffsetX = newOuterOffsetX;
					outerOffsetY = newOuterOffsetY;
					scale = scale * d;
					
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
	public void center() {
		setOffsetsToZero();
		if (!pointSet.isEmpty()) {
			initializeScale();
			initializeOffsets();
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

	/**
	 * Initializes the scale value so that the point set loaded from a file
	 * is displayed centered on this draw panel.
	 */
	private void initializeScale() {
		int xRange = (pointSet.getMaxX() - pointSet.getMinX());
		int yRange = (pointSet.getMaxY() - pointSet.getMinY());
		if(xRange != 0 && yRange != 0) {
			 double xScale = ((double)getWidth() - 4 * (double)Settings.radius)/(double)xRange;
			 double yScale = (double) (getHeight() - 4 * Settings.radius)/(double)yRange;
			 scale=  Math.min(xScale, yScale);
		}
		// one radius as a margin
	    if (xRange == 0 && yRange != 0) {
	    	scale = (double) (getHeight() - 4 * Settings.radius)/(double)yRange;
	    }
	    if (xRange != 0 && yRange == 0) {
	    	scale = ((double)getWidth() - 4 * (double)Settings.radius)/(double)xRange;
	    }
	    if (xRange == 0 && yRange == 0) {
	    	scale = 1;
	    }
	}
    
	/**
	 * Initializes the offsets so that the point set loaded from a file 
	 * is displayed centered on this draw panel.
	 */
	private void initializeOffsets() {
		innerOffsetX = (double)getWidth()/(2*scale) - ((double)pointSet.getMinX() + (double)pointSet.getMaxX())/2;
		innerOffsetY = ((double)getHeight()/2 - 1)/scale - ((double)pointSet.getMinY() + (double)pointSet.getMaxY())/2;
	}

	
	@Override
	public void update() {
		repaint();
	}


	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		// set anti aliasing for a nicer presentation of the points and lines
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		drawAxes(g2);
		if (!pointSet.isEmpty()) {
			
			if (animationIsShown) {
				drawAnimatedQuadrangle(g2, Settings.animatedQuadrangleColor);
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
	 * Draws all the points of the point set on this
	 * draw panel.
	 *
	 * @param g2 the Graphics2D object
	 */
	private void drawPoints(Graphics2D g2) {

		for (int i = 0; i < pointSet.getNumberOfPoints(); i++) {
			IPoint p = pointSet.getPointAt(i);
			IPoint translatedPoint = translatePointFromModelToView(p);
			g2.fillOval(translatedPoint.getX() - Settings.radius, translatedPoint.getY() - Settings.radius,
					2 * Settings.radius, 2 * Settings.radius);
			if(p.isSelected()) {
				drawMarking(translatedPoint, g2, Settings.markingColor);
			}
		}
	}

	/**
	 * Draws the marking of the selected point.
	 *
	 * @param point the point
	 * @param g2 the g 2
	 * @param color the color
	 */
	private void drawMarking(IPoint point, Graphics2D g2, Color color) {
		g2.setColor(color);
		int x = point.getX() - Settings.radius - 3;
		int y = point.getY() - Settings.radius - 3;
		
		/* -1 because the height and the width of drawRect seems to 
		 * be the difference between highest pixel and lowest and not the number of pixels
		 */
		int length = 2*Settings.radius + 6 - 1; 
		g2.drawRect(x, y, length, length);
		g2.setColor(Color.BLACK);	
	}



	/**
	 * This method, for a given point with model
	 * coordinates, returns a new point with the 
	 * corresponding view coordinates.
	 *
	 * @param point the point whose coordinates are translated
	 * @return a new point with the translated coordinates
	 */
	private IPoint translatePointFromModelToView(IPoint point) {
		int x = (int) Math.round(translateXFromModelToView(point.getX()));
		int y = (int) Math.round(translateYFromModelToView(point.getY()));
		return new Point(x, y);
	}

	/**
	 * Returns a new point with model coordinates,
	 * corresponding to the given view coordinates.
	 *
	 * @param x the x coordinate to be translated
	 * @param y the y coordinate to be translated
	 * @return a new point with the translated coordinates
	 */
	private IPoint translatePointFromViewToModel(int x, int y) {
		int translatedX = (int) Math.round(translateXFromViewToModel(x));
		int translatedY = (int) Math.round(translateYFromViewToModel(y));
		return new Point(translatedX, translatedY);
	}
	
	@Override
	public IPoint getViewPointTranslatedToModelPoint(IPoint point) {
		return translatePointFromViewToModel(point.getX(), point.getY());
	}                                                                                        

	/**
	 * Translates an x coordinate from the model coordinate system to the view
	 * coordinate system.
	 *
	 * @param x the x coordinate to be translated
	 * @return the translated x coordinate
	 */
	private double translateXFromModelToView(int x) {
		return (((double) x + innerOffsetX) * scale + outerOffsetX + mouseOffsetX)*panelScale;
	}
	
	/**
	 * Translates an x coordinate from the view coordinate system to the model
	 * coordinate system
	 *
	 * @param x the x coordinate to be translated
	 * @return the translated x coordinate
	 */
	private double translateXFromViewToModel(int x) {
		return ((double) x / panelScale - outerOffsetX - mouseOffsetX)/scale - innerOffsetX;
	}

	/**
	 * Translates a y coordinate from the model coordinate system to the view
	 * coordinate system.
	 *
	 * @param y the y coordinate to be translated
	 * @return the translated y coordinate
	 */
	private double translateYFromModelToView(int y) {
		return  (double)getHeight() - 1 - (((double)y + innerOffsetY)*scale + outerOffsetY + mouseOffsetY)*panelScale;
	}

	

	/**
	 * Translates an y coordinate from the view coordinate system to the model
	 * coordinate system.
	 *
	 * @param y the y coordinate to be translated
	 * @return the translated y coordinate
	 */
	private double translateYFromViewToModel(int y) {
		return (((double)getHeight() - 1 - (double)y)/panelScale  - outerOffsetY - mouseOffsetY)/scale - innerOffsetY;
	}
     
	
     // TODO: With iterator
	
	/**
	 * Draws the convex hull.
	 *
	 * @param g2    the Graphics2D Object which is used for painting
	 * @param color the color for the hull
	 */
	private void drawHull(Graphics2D g2) {
		g2.setColor(Settings.convexHullColor);

		for (ContourType sectionType : ContourType.values()) {
			int sectionSize = hull.getSizeOfSection(sectionType);
			if (sectionSize > 1) {
				for (int i = 0; i < sectionSize - 1; i++) {

					IPoint first = hull.getPointFromSection(i, sectionType);
					IPoint translatedFirst = translatePointFromModelToView(first);
					IPoint second = hull.getPointFromSection(i + 1, sectionType);
					IPoint translatedSecond = translatePointFromModelToView(second);
					g2.drawLine(translatedFirst.getX(), translatedFirst.getY(), translatedSecond.getX(),
							translatedSecond.getY());
				}
			}
			sectionSize = hull.getSizeOfSection(ContourType.UPPERLEFT);
			IPoint lastLeft = hull.getPointFromSection(sectionSize - 1, ContourType.UPPERLEFT);
			IPoint translatedLastLeft = translatePointFromModelToView(lastLeft);

			sectionSize = hull.getSizeOfSection(ContourType.UPPERRIGHT);
			IPoint lastRight = hull.getPointFromSection(sectionSize - 1, ContourType.UPPERRIGHT);
			IPoint translatedLastRight = translatePointFromModelToView(lastRight);
			g2.drawLine(translatedLastLeft.getX(), translatedLastLeft.getY(), translatedLastRight.getX(),
					translatedLastRight.getY());

			sectionSize = hull.getSizeOfSection(ContourType.LOWERLEFT);
			lastLeft = hull.getPointFromSection(sectionSize - 1, ContourType.LOWERLEFT);
			translatedLastLeft = translatePointFromModelToView(lastLeft);
			sectionSize = hull.getSizeOfSection(ContourType.LOWERRIGHT);
			lastRight = hull.getPointFromSection(sectionSize - 1, ContourType.LOWERRIGHT);
			translatedLastRight = translatePointFromModelToView(lastRight);
			g2.drawLine(translatedLastLeft.getX(), translatedLastLeft.getY(), translatedLastRight.getX(),
					translatedLastRight.getY());

		}
		g2.setColor(Color.BLACK);
	}

	/**
	 * Draws the diameter.
	 *
	 * @param g2    the Graphics2D Object which is used for painting
	 * @param color the color for the diameter
	 */
	private void drawDiameter(Graphics2D g2) {
		if (diameter != null) {
			g2.setColor(Settings.diameterColor);
			IPoint a = diameter.getA();
			IPoint translatedA = translatePointFromModelToView(a);
			IPoint b = diameter.getB();
			IPoint translatedB = translatePointFromModelToView(b);
			g2.drawLine(translatedA.getX(), translatedA.getY(), translatedB.getX(), translatedB.getY());
			g2.setColor(Color.BLACK);
		}
	}

	/**
	 * Draws the quadrangle.
	 *
	 * @param g2    the Graphics2D Object which is used for painting
	 * @param color the color for the quadrangle
	 */
	private void drawQuadrangle(Graphics2D g2) {
		if (quadrangle != null) {
			g2.setColor(Settings.quadrangleColor);

			IPoint translatedA = translatePointFromModelToView(quadrangle.getA());
			IPoint translatedB = translatePointFromModelToView(quadrangle.getB());
			IPoint translatedC = translatePointFromModelToView(quadrangle.getC());
			IPoint translatedD = translatePointFromModelToView(quadrangle.getD());

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
	 * @param g2 the Graphics2D Object which is used for painting
	 */
	private void drawAxes(Graphics2D g2) {
		Graphics2D g2d = (Graphics2D) g2.create();
		g2d.setColor(Settings.axisColor);
		int x = (int) Math.round(translateXFromModelToView(0));
		int y = (int) Math.round(translateYFromModelToView(0));
		// ordinate
		g2d.drawLine(x, 0, x, getHeight() - 1);
		// abszissa
		g2d.drawLine(0, y, getWidth() - 1, y);
		g2d.setColor(Color.BLACK);	
	}

	/**
	 * Draws the current state of the tangent pair.
	 *
	 * @param g2 the Graphics2D Object which is used for painting
	 */
	private void drawTangentPair(Graphics2D g2) {
		// try and catch in case the tangent pair is not initialized yet
		try {
			 // get the tangents from the tangent pair
			 IPoint[] tangent1 = tangentPair.getTangent1(); 
			 IPoint[] tangent2 = tangentPair.getTangent2();
			 
			 extendTangent(tangent1);
			 extendTangent(tangent2);
			 
			 // draw first tangent
			 g2.drawLine(
					 (int)Math.round(translateXFromModelToView(tangent1[0].getX())),
					 (int)Math.round(translateYFromModelToView(tangent1[0].getY())), 
					 (int)Math.round(translateXFromModelToView(tangent1[2].getX())), 
					 (int)Math.round(translateYFromModelToView(tangent1[2].getY())));
			 
			 // draw second tangent
			 g2.drawLine(
					 (int)Math.round(translateXFromModelToView(tangent2[0].getX())),
					 (int)Math.round(translateYFromModelToView(tangent2[0].getY())), 
					 (int)Math.round(translateXFromModelToView(tangent2[2].getX())), 
					 (int)Math.round(translateYFromModelToView(tangent2[2].getY())));
			 
			 /* draw connection of the centers i.e. the connection between the points 
			 of the antipodal pair */
			 g2.drawLine(
					 (int)Math.round(translateXFromModelToView(tangent1[1].getX())),
					 (int)Math.round(translateYFromModelToView(tangent1[1].getY())), 
					 (int)Math.round(translateXFromModelToView(tangent2[1].getX())), 
					 (int)Math.round(translateYFromModelToView(tangent2[1].getY())));
			 
			 
		} catch (NullPointerException e) {
			e.getStackTrace();
		}	 
	}
	
	/**
	 * Draws the current quadrangle of the quadrangle sequence.
	 *
	 * @param g2 the g 2
	 * @param color the color
	 */
	private void drawAnimatedQuadrangle(Graphics2D g2, Color color) {
		if (quadrangleSequence != null) {
			g2.setColor(color);
            
			IQuadrangle quadrangle = quadrangleSequence.getQuadrangle();
			IPoint translatedA = translatePointFromModelToView(quadrangle.getA());
			IPoint translatedB = translatePointFromModelToView(quadrangle.getB());
			IPoint translatedC = translatePointFromModelToView(quadrangle.getC());
			IPoint translatedD = translatePointFromModelToView(quadrangle.getD());

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
	 * draw panel in pixels.
	 *
	 * @param tangent the tangent
	 */
	private void extendTangent(IPoint[] tangent) {
		
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
	 * Returns the length of diagonal of the 
	 * draw panel in pixels.
	 *
	 * @return the diagonal of the panel in pixels.
	 */
	private double panelDiagonal() {
		return Math.sqrt(getWidth() * getWidth() + getHeight() * getHeight());
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
	public void setShowAnimation(boolean b) {
		if(b == true) {
			//animationIsShown.update(convexHull);
		}
		
		animationIsShown = b;
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
