package propra22.q8493367.draw.view;

import java.awt.Color;
import java.awt.Dimension;
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

import animation.ITangentPair;
import animation.TangentPair;
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
	private IPointSet pointSet;
	private IHull hull;
	private IDiameter diameter;
	private IQuadrangle quadrangle;
	private ITangentPair tangentPair;
	private Thread animationThread;

	// Draw panel listener.
	private IDrawPanelListener drawPanelListener;

	// Which shape is shown
	private boolean convexHullIsShown = Settings.defaultConvexHullIsShown;
	private boolean diameterIsShown = Settings.defaultDiameterIsShown;
	private boolean quadrangleIsShown = Settings.defaultQuadrangleIsShown;
	private boolean triangleIsShown = Settings.defaultTriangleIsShown;
	private boolean animationIsShown = Settings.defaultAnimationIsShown;

	// Zoom
	private double scale = 1.0;
	private final double scaleFactor = 1.08;
	private double zoomOffsetX = 0;
	private double zoomOffsetY = 0;

	// Drag
	private double initialDragX = 0;
	private double initialDragY = 0;

	private double mouseOffsetX = 0;
	private double mouseOffsetY = 0;

	private double dragOffsetX = 0;
	private double dragOffsetY = 0;

	// Offset (data)
	private double offsetX = 0;
	private double offsetY = 0;
	private double originalWidth;
	private double originalHeight;

	private double panelScale = 1;

	/**
	 * Instantiates a new draw panel.
	 * 
	 * @param tangentPair
	 */
	public DrawPanel(IPointSet pointSet, IHull hull, IDiameter diameter, IQuadrangle quadrangle,
			ITangentPair tangentPair) {

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

				System.out.println("drawPanel, point before translation: " + e.getX() + ", " + e.getY());
				System.out.println("drawPanel, point after translation: " + translatedX + ", " + translatedY);
				// translate back
				System.out.println("drawPanel, translated point translated back to view: "
						+ translateXFromModelToView(translatedX) + ", " + translateYFromModelToView(translatedY));

				System.out.println("drawPanel delete point, pointSet: " + pointSet.toString());

				System.out.println("scale: " + scale + " panelScale: " + panelScale);

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
					// point drag
					int translatedX = (int) Math.round(translateXFromViewToModel(e.getX()));
					int translatedY = (int) Math.round(translateYFromViewToModel(e.getY()));
					drawPanelListener.drawPanelEventOccured(new DrawPanelEvent(DrawPanelEventType.DRAG_POINT,
							e.getSource(), translatedX, translatedY, scale * panelScale));
				} else if (SwingUtilities.isLeftMouseButton(e) && e.isControlDown()) {
					// panel drag
					mouseOffsetX = e.getX() - initialDragX;
					mouseOffsetY = -(e.getY() - initialDragY);
					repaint();
				}
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
					/*
					 * System.out.println("Mouse Position  X:  " + e.getX() + " Mouse Position Y: "
					 * + e.getY()) ; System.out.println("scale: " + scale); System.out.println("d: "
					 * + d); System.out.println("zoomOffsetX: " + zoomOffsetX);
					 * System.out.println("zoomOffsetY: " + zoomOffsetY);
					 */
					double newzoomOffsetX = ((double) e.getX() / panelScale - zoomOffsetX - dragOffsetX - mouseOffsetX)
							* (1 - d) + zoomOffsetX;
					double newzoomOffsetY = (((double) getHeight() - 1d - (double) e.getY()) / panelScale - dragOffsetY
							- mouseOffsetY - zoomOffsetY) * (1 - d) + zoomOffsetY;

					zoomOffsetX = newzoomOffsetX;
					zoomOffsetY = newzoomOffsetY;

					scale = scale * d;
					updateTangetPairLength();
					repaint();
				}
			}

			
		});

		addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent e) {
				super.componentResized(e);
				if (originalWidth != 0 && originalHeight != 0) {
					panelScale = Math.min((double) getWidth() / (double) originalWidth,
							(double) getHeight() / (double) originalHeight);
				} else {
					originalWidth = getWidth();
					originalHeight = getHeight();
					panelScale = 1;
				}
				updateTangetPairLength();
				repaint();
			}
		});
	}
	
	private void updateTangetPairLength() {
		tangentPair.setLength((float) (Math.sqrt(getWidth() * getWidth() + getHeight() * getHeight())/ (scale * panelScale))  );
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
	public void initialize() {
		originalWidth = getWidth();
		originalHeight = getHeight();
		panelScale = 1;
		setOffsetsToZero();
		if (!pointSet.isEmpty()) {
			initializeScale();
			initializeOffsets();
		}
		repaint();
	}

	private void setOffsetsToZero() {
		zoomOffsetX = 0;
		zoomOffsetY = 0;
		initialDragX = 0;
		initialDragY = 0;
		mouseOffsetX = 0;
		mouseOffsetY = 0;
		dragOffsetX = 0;
		dragOffsetY = 0;
		offsetX = 0;
		offsetY = 0;
	}

	private void initializeScale() {
		double xScale = (double) (getWidth() - 3 * Settings.radius)
				/ (double) (pointSet.getMaxX() - pointSet.getMinX());
		double yScale = (double) (getHeight() - 3 * Settings.radius)
				/ (double) (pointSet.getMaxY() - pointSet.getMinY());
		scale = Math.min(xScale, yScale);
	}

	private void initializeOffsets() {
		offsetX = (int) (((double) getWidth() - (double) (pointSet.getMaxX() + pointSet.getMinX()) * scale)
				/ (2 * scale));
		offsetY = (int) (((double) getHeight() - (double) (pointSet.getMaxY() + pointSet.getMinY()) * scale)
				/ (2 * scale));
	}

	@Override
	public void update() {
		if (originalWidth == 0 || originalHeight == 0) {
			originalWidth = getWidth();
			originalHeight = getHeight();
		}
		repaint();
	}

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
			drawCoordinateSystem(g2);
			if (animationIsShown) {
				drawAnimation(g2);
			}
		}
	}

	/**
	 * Draws all points from the point set onto the draw panel.
	 *
	 * @param g2 - the Graphics2D Object which is used for painting
	 */
	private void drawPoints(Graphics2D g2) {

		for (int i = 0; i < pointSet.getNumberOfPoints(); i++) {
			IPoint p = pointSet.getPointAt(i);
			IPoint translatedPoint = translatePointFromModelToView(p);
			g2.fillOval(translatedPoint.getX() - Settings.radius, translatedPoint.getY() - Settings.radius,
					2 * Settings.radius, 2 * Settings.radius);
		}
	}

	private IPoint translatePointFromModelToView(IPoint point) {
		int x = (int) Math.round(translateXFromModelToView(point.getX()));
		int y = (int) Math.round(translateYFromModelToView(point.getY()));
		return new Point(x, y);
	}

	private IPoint translatePointFromViewToModel(int x, int y) {
		int translatedX = (int) Math.round(translateXFromViewToModel(x));
		int translatedY = (int) Math.round(translateYFromViewToModel(y));
		return new Point(translatedX, translatedY);
	}

	private double translateXFromModelToView(int x) {
		return (((double) x + offsetX) * scale + dragOffsetX + mouseOffsetX + zoomOffsetX) * panelScale;
	}

	private double translateYFromModelToView(int y) {
		return ((double) getHeight() - 1d
				- ((((double) y + offsetY)) * scale + dragOffsetY + mouseOffsetY + zoomOffsetY) * panelScale);
	}

	private double translateXFromViewToModel(int x) {
		return (((double) x / panelScale - dragOffsetX - mouseOffsetX - zoomOffsetX) / scale - offsetX);
	}

	private double translateYFromViewToModel(int y) {
		return ((((double) getHeight() - 1d - (double) y) / panelScale - dragOffsetY - mouseOffsetY - zoomOffsetY)
				/ scale - offsetY);
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
					g2.drawLine(translatedFirst.getX(), translatedFirst.getY(), translatedSecond.getX(),
							translatedSecond.getY());
				}
			}
			sectionSize = hull.getSizeOfSection(SectionType.LOWERLEFT);
			IPoint lastLeft = hull.getPointFromSection(sectionSize - 1, SectionType.LOWERLEFT);
			IPoint translatedLastLeft = translatePointFromModelToView(lastLeft);

			sectionSize = hull.getSizeOfSection(SectionType.LOWERRIGHT);
			IPoint lastRight = hull.getPointFromSection(sectionSize - 1, SectionType.LOWERRIGHT);
			IPoint translatedLastRight = translatePointFromModelToView(lastRight);
			g2.drawLine(translatedLastLeft.getX(), translatedLastLeft.getY(), translatedLastRight.getX(),
					translatedLastRight.getY());

			sectionSize = hull.getSizeOfSection(SectionType.UPPERLEFT);
			lastLeft = hull.getPointFromSection(sectionSize - 1, SectionType.UPPERLEFT);
			translatedLastLeft = translatePointFromModelToView(lastLeft);
			sectionSize = hull.getSizeOfSection(SectionType.UPPERRIGHT);
			lastRight = hull.getPointFromSection(sectionSize - 1, SectionType.UPPERRIGHT);
			translatedLastRight = translatePointFromModelToView(lastRight);
			g2.drawLine(translatedLastLeft.getX(), translatedLastLeft.getY(), translatedLastRight.getX(),
					translatedLastRight.getY());

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

	private void drawCoordinateSystem(Graphics2D g2) {
		Graphics2D g2d = (Graphics2D) g2.create();
		g2d.setColor(Settings.CoordinateSystemColor);
		int x = (int) Math.round(translateXFromModelToView(0));
		int y = (int) Math.round(translateYFromModelToView(0));
		// ordinate
		g2d.drawLine(x, 0, x, getHeight() - 1);
		// abszissa
		g2d.drawLine(0, y, getWidth() - 1, y);

		g2d.dispose();

	}

	private void drawAnimation(Graphics2D g2) {
		
		/*
		System.out.println("Show Animation");
		tangentPair.initialize(hull);

		System.out.println("hull");
		System.out.println(hull.toList());
		*/

		try {
			 IPoint[] tangent1 = tangentPair.getTangent1(); 
			 IPoint[] tangent2 = tangentPair.getTangent2(); 
			 System.out.println( "tangent1  " + (int) Math.round(tangent1[1].getX()) + "   " + (int)
			 Math.round(tangent1[1].getY()));
		
			 System.out.println( "tangent2  " + (int) Math.round(tangent2[1].getX()) +
			 "   " + (int) Math.round(tangent2[1].getY())); 
			 
			 /* int a1 =
			 * tangent1[0].getX(); int b1 = tangent1[0].getY(); int c1 = tangent1[2].getX();
			 * int d1 = tangent1[2].getY();
			 * 
			 * int a2 = tangent2[0].getX(); int b2 = tangent2[0].getY(); int c2 =
			 * tangent2[2].getX(); int d2 = tangent2[2].getY();
			 */
			
			 g2.drawLine(
					 (int) Math.round(translateXFromModelToView(tangent1[0].getX())),
					 (int) Math.round(translateYFromModelToView(tangent1[0].getY())), 
					 (int)Math.round(translateXFromModelToView(tangent1[2].getX())), 
					 (int)Math.round(translateYFromModelToView(tangent1[2].getY())));
			 
			 g2.drawLine(
					 (int) Math.round(translateXFromModelToView(tangent2[0].getX())),
					 (int) Math.round(translateYFromModelToView(tangent2[0].getY())), 
					 (int)Math.round(translateXFromModelToView(tangent2[2].getX())), 
					 (int)Math.round(translateYFromModelToView(tangent2[2].getY())));
		} catch (NullPointerException e) {
			e.getStackTrace();
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
	public IPoint getViewPointTranslatedToModelPoint(IPoint point) {
		return translatePointFromViewToModel(point.getX(), point.getY());
	}

	@Override
	public void setShowAnimation(boolean b) {
		if ((animationIsShown == false) && b) {
			animationIsShown = true;
			animate();
		} else if ((animationIsShown == true) && (b == false)) {
			
			animationIsShown = false;
		}
	}

	private void animate() {
		System.out.println("Thread hello");
		Thread animationThread = new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println("in run");
				float diagonal = (float)( Math.sqrt(getWidth() * getWidth() + getHeight() * getHeight() ) / (scale * panelScale));
				tangentPair.initialize(hull, diagonal);
				while(animationIsShown) {
					update();
					tangentPair.step();
					try {
						Thread.sleep(15);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println("Thread bye bye");
				
			}});
		animationThread.start();
		
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
