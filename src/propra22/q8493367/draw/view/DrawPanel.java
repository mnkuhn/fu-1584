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
 * the triangle and the animation are displayed on the draw panel.
 * It also provides a panning and a zoom functionality.
 */
public class DrawPanel extends JPanel implements IDrawPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	// the model
	/** The point set. */
	private IPointSet pointSet;
	
	/** The hull. */
	private volatile IHull hull;
	
	/** The diameter. */
	private IDiameter diameter;
	
	/** The quadrangle. */
	private IQuadrangle quadrangle;
	
	private TangentPair tangentPair;
	
	//Listener
	/** The draw panel listener. */
	private IDrawPanelListener drawPanelListener;

	//Display
	/** True, if the convexHull is shown*/
	private boolean convexHullIsShown = Settings.defaultConvexHullIsShown;
	
	/** True, if diameter is shown. */
	private boolean diameterIsShown = Settings.defaultDiameterIsShown;
	
	/** True, if the quadrangle is shown. */
	private boolean quadrangleIsShown = Settings.defaultQuadrangleIsShown;
	
	/** True, if the triangle is shown. */
	private boolean triangleIsShown = Settings.defaultTriangleIsShown;
	
	/** True, if the animation is running. */
	private boolean animationIsShown = Settings.defaultAnimationIsShown;
    
	
	//Zoom and Dragging
	/** The scale for the zoom. */
	private double scale = 1.0f;
	
	/** The scale of the draw panel. This
	 * scale is used to adapt the size of the 
	 * content, when the size of the 
	 * draw panel is changed by the user.
	 */
	private double panelScale = 1d;
	
	/** The scale factor. */
	private final double scaleFactor = 1.08d;
	
	/** The x coordinate of the mouse when dragging starts. */
	private double initialDragX = 0;
	
	/** The y coordinate of the mouse when dragging starts. */
	private double initialDragY = 0;

	/** The x coordinate of the mouse minus initialDragX */
	private double mouseOffsetX = 0;
	
	/** The y coordinate of the mouse minus initialDragY */
	private double mouseOffsetY = 0;
    
	/** The outerOffsetX which is used for zooming and dragging
	 * the panel (after dragging the panel, the mouseOffsetX is
	 * added to the outerOffsetX and then set to 0). 
	 */
	private double outerOffsetX = 0;
	
	/** The outerOffsetY which is used for zooming and dragging
	 * the panel (after dragging the panel, the mouseOffsetY is
	 * added to the outerOffsetY and then set to 0). 
	 */
	private double outerOffsetY = 0;

	/** The innerOffsetX is used to center a set of
	 * points which is loaded from a file
	 * on the draw panel.
	 */
	private double innerOffsetX = 0;
	
	/** The innerOffsetY is used to center a set of
	 * points which is loaded from a file
	 * on the draw panel.
	 */
	private double innerOffsetY = 0;
	
	/** The original width is the reference width of the 
	 * draw panel. It us used to calculate the value of the 
	 * panelScale, when the user changes the size of
	 * the main window.
	 */
	private double referenceWidth = 0;
	
	/** The original height is the reference width of the 
	 * draw panel. It us used to calculate the value of the 
	 * panelScale, when the user changes the size of
	 * the main window.
	 */
	private double referenceHeight = 0 ;
	

	

	
	/**
	 * Instantiates a new draw panel.
	 *
	 * @param pointSet - the point set
	 * @param hull - the convex hull
	 * @param diameter - the diameter
	 * @param quadrangle - the biggest quadrangle
	 * @param tangentPair - the tangent pair for the animation
	 */
	public DrawPanel(IPointSet pointSet, IHull hull, IDiameter diameter, IQuadrangle quadrangle,
			TangentPair tangentPair) {

		this.pointSet = pointSet;
		this.hull = hull;
		this.diameter = diameter;
		this.quadrangle = quadrangle;
		this.tangentPair = tangentPair;

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
	
	

	/**
	 *  {@inheritDoc}
	 */
	@Override
	public void setDrawPanelListener(IDrawPanelListener drawPanelListener) {
		this.drawPanelListener = drawPanelListener;
	}

	/**
	 *  {@inheritDoc}
	 */
	@Override
	public void initialize() {
		//referenceWidth = getWidth();
		//referenceHeight = getHeight();
		//panelScale = 1;
		setOffsetsToZero();
		if (!pointSet.isEmpty()) {
			initializeScale();
			initializeOffsets();
		}
		repaint();
	}

	/**
	 * Sets the offsets to zero.
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
	 * Initializes scale so that the point set loaded from a file
	 * is displayed centered on the draw panel.
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
	 * is displayed centered on the draw panel.
	 */
	private void initializeOffsets() {
		innerOffsetX = (double)getWidth()/(2*scale) - ((double)pointSet.getMinX() + (double)pointSet.getMaxX())/2;
		innerOffsetY = ((double)getHeight()/2 - 1)/scale - ((double)pointSet.getMinY() + (double)pointSet.getMaxY())/2;
	}

	/**
	 *  {@inheritDoc}
	 */
	@Override
	public void update() {
		repaint();
	}


	/**
	 *  {@inheritDoc}
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		// set anti aliasing for a nicer presentation of the points and lines
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		drawCoordinateSystem(g2);
		if (!pointSet.isEmpty()) {
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
			if (animationIsShown) {
				drawTangentPair(g2);
			}
		}
	}

	/**
	 *  {@inheritDoc}
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
	 * Translate point from model to view.
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
	 * Translate point from view to model.
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
	
	/**
	 * {@inheritDoc}                         
	 */
	@Override
	public IPoint getViewPointTranslatedToModelPoint(IPoint point) {
		return translatePointFromViewToModel(point.getX(), point.getY());
	}                                                                                        

	/**
	 * Translates an x coordinate from the model to the view.
	 *
	 * @param x the x coordinate to be translated
	 * @return the translated x coordinate
	 */
	private double translateXFromModelToView(int x) {
		return (((double) x + innerOffsetX) * scale + outerOffsetX + mouseOffsetX)*panelScale;
	}
	
	/**
	 * Translates an x coordinate from the view to the model.
	 *
	 * @param x the x coordinate to be translated
	 * @return the translated y coordinate
	 */
	private double translateXFromViewToModel(int x) {
		return ((double) x / panelScale - outerOffsetX - mouseOffsetX)/scale - innerOffsetX;
	}

	/**
	 * Translate an y coordinate form the model to the view
	 *
	 * @param y the y coordinate to be translated
	 * @return the translated y coordinate
	 */
	private double translateYFromModelToView(int y) {
		return  (double)getHeight() - 1 - (((double)y + innerOffsetY)*scale + outerOffsetY + mouseOffsetY)*panelScale;
	}

	

	/**
	 * Translates an y coordinate from the view to the model
	 *
	 * @param y the y coordinate to be translated
	 * @return the translated y coordinate
	 */
	private double translateYFromViewToModel(int y) {
		return (((double)getHeight() - 1 - (double)y)/panelScale  - outerOffsetY - mouseOffsetY)/scale - innerOffsetY;
	}
     
	
	// TODO now with iterators!
	
	/**
	 * Draws the convex hull.
	 *
	 * @param g2    the Graphics2D Object which is used for painting
	 * @param color the color for the hull
	 */
	private void drawHull(Graphics2D g2, Color color) {
		g2.setColor(color);

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
			sectionSize = hull.getSizeOfSection(ContourType.NEWUPPERLEFT);
			IPoint lastLeft = hull.getPointFromSection(sectionSize - 1, ContourType.NEWUPPERLEFT);
			IPoint translatedLastLeft = translatePointFromModelToView(lastLeft);

			sectionSize = hull.getSizeOfSection(ContourType.NEWUPPERRIGHT);
			IPoint lastRight = hull.getPointFromSection(sectionSize - 1, ContourType.NEWUPPERRIGHT);
			IPoint translatedLastRight = translatePointFromModelToView(lastRight);
			g2.drawLine(translatedLastLeft.getX(), translatedLastLeft.getY(), translatedLastRight.getX(),
					translatedLastRight.getY());

			sectionSize = hull.getSizeOfSection(ContourType.NEWLOWERLEFT);
			lastLeft = hull.getPointFromSection(sectionSize - 1, ContourType.NEWLOWERLEFT);
			translatedLastLeft = translatePointFromModelToView(lastLeft);
			sectionSize = hull.getSizeOfSection(ContourType.NEWLOWERRIGHT);
			lastRight = hull.getPointFromSection(sectionSize - 1, ContourType.NEWLOWERRIGHT);
			translatedLastRight = translatePointFromModelToView(lastRight);
			g2.drawLine(translatedLastLeft.getX(), translatedLastLeft.getY(), translatedLastRight.getX(),
					translatedLastRight.getY());

		}
		g2.setColor(Color.BLACK);
	}

	/**
	 * Draw diameter.
	 *
	 * @param g2    the Graphics2D Object which is used for painting
	 * @param color the color for the diameter
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
	 * @param g2    the Graphics2D Object which is used for painting
	 * @param color the color for the quadrangle
	 */
	private void drawQuadrangle(Graphics2D g2, Color color) {
		if (quadrangle != null) {
			g2.setColor(color);

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
	 * Draw coordinate system.
	 *
	 * @param g2 the Graphics2D Object which is used for painting
	 */
	private void drawCoordinateSystem(Graphics2D g2) {
		Graphics2D g2d = (Graphics2D) g2.create();
		g2d.setColor(Settings.axisColor);
		int x = (int) Math.round(translateXFromModelToView(0));
		int y = (int) Math.round(translateYFromModelToView(0));
		// ordinate
		g2d.drawLine(x, 0, x, getHeight() - 1);
		// abszissa
		g2d.drawLine(0, y, getWidth() - 1, y);

		g2d.dispose();
	}

	/**
	 * Draw animation.
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
	 * Extends the tangent so that it is longer than twice the diameter of the 
	 * draw panel in pixels
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
	 * @return the double
	 */
	private double panelDiagonal() {
		return Math.sqrt(getWidth() * getWidth() + getHeight() * getHeight());
	}



	/**
	 *  {@inheritDoc}
	 */
	@Override
	public void setConvexHullIsShown(boolean convexHullIsShown) {
		this.convexHullIsShown = convexHullIsShown;
	}

	/**
	 *  {@inheritDoc}
	 */
	@Override
	public void setDiameterIsShown(boolean diameterIsShown) {
		this.diameterIsShown = diameterIsShown;
	}

	/**
	 *  {@inheritDoc}
	 */
	@Override
	public void setQuadrangleIsShown(boolean quadrangleIsShown) {
		this.quadrangleIsShown = quadrangleIsShown;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setTriangleIsShown(boolean b) {
		triangleIsShown = b;
	}

	/**
	 *  {@inheritDoc}
	 */
	@Override
	public boolean convexHullIsShown() {
		return convexHullIsShown;
	}

	/**
	 *  {@inheritDoc}
	 */
	@Override
	public boolean diameterIsShown() {
		return diameterIsShown;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean quadrangleIsShown() {
		return quadrangleIsShown;
	}

	

	/**
	  {@inheritDoc}
	 */
	@Override
	public void setShowAnimation(boolean b) {
		if(b == true) {
			//animationIsShown.update(convexHull);
		}
		
		animationIsShown = b;
	}

	/**
	 * Runs the animation.
	 */
	
	/*
	private void runAnimation() {
		animationIsShown = true;
		
		Thread animationThread = new Thread(new Runnable() {
             
			@Override
			public void run() {
				Thread.currentThread().setName("AnimationThread");
				System.out.println("Hello Animation Thread");
				boolean tangentPairInitialized = false;
				int tries = 0;
				int MAXTRIES = 10;
				while(animationIsShown && !tangentPairInitialized && tries < MAXTRIES) {
					try {
						tangentPair.initialize(hull);
						tangentPairInitialized = true;
					} catch (NullPointerException e) {
						e.printStackTrace();
						tries++;
						takeNap();
					} catch (ArithmeticException e) {
						e.printStackTrace();
						tries++;
						takeNap();
					}	
				}
				
				while(animationIsShown) {
					update();
					try {
						tangentPair.step();
						Thread.sleep(20);
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (NullPointerException e) {
						e.printStackTrace();
					} catch(ArithmeticException e) {
						e.printStackTrace();
					}
				}
				System.out.println("Animation Thread bye bye");		
			}

			private void takeNap() {
				try {
					Thread.sleep(30);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			
		});
		animationThread.start();	
	}
	
	*/

	/**
	 *  {@inheritDoc}
	 */
	@Override
	public boolean animationIsShown() {
		return animationIsShown;
	}

	/**
	 *  {@inheritDoc}
	 */
	@Override
	public boolean triangleIsShown() {
		return triangleIsShown;
	}
}
