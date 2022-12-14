package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import controllers.IStatusBar;


/**
 * The Class StatusBar displays the current number of
 * points in the point set, the current position of the mouse pointer
 * over the draw panel and the coordinates of the selected point.
 */
public class StatusBar extends JPanel implements IStatusBar{
    
	
	/** The Constant serialVersionUID for version check in case of
	 * transmitting a serialized object.
	 */
	private static final long serialVersionUID = 1L;
	
	/**  The label which displays the number of points in the point set. */
	private JLabel numberLabel;
	
	/** The label which displays the mouse coordinates. */
	private JLabel mouseCoordinates;
	
	/** The label which displays the coordinates of the selected point. */
	private JLabel selectedPointCoordinates;
	
	/**  The height of the status bar in pixels. */
	private int height = 18;
	
	/**
	 * Instantiates a new status bar.
	 */
	public StatusBar() {
		setBorder(new BevelBorder(BevelBorder.LOWERED));
		setLayout(new BorderLayout());
		
		JPanel left = new JPanel();
		
		JLabel number = new JLabel("Anzahl Punkte:", SwingConstants.LEFT);
		number.setOpaque(true);
		numberLabel = new JLabel("", SwingConstants.LEFT);
		numberLabel.setPreferredSize(new Dimension(150, height));
		numberLabel.setOpaque(true);
		left.add(number);
		left.add(numberLabel);
		
		JLabel coordinates = new JLabel("Maus:", SwingConstants.LEFT);
		coordinates.setOpaque(true);
		mouseCoordinates = new JLabel("", SwingConstants.CENTER);
		mouseCoordinates.setPreferredSize(new Dimension(150, height));
		mouseCoordinates.setOpaque(true);
		
		JPanel right = new JPanel();
		
		right.add(coordinates);
		right.add(mouseCoordinates);
		
		JLabel selectedLabel = new JLabel("Gewählter Punkt:", SwingConstants.LEFT);
		selectedLabel.setOpaque(true);
		selectedPointCoordinates = new JLabel("", SwingConstants.CENTER);
		selectedPointCoordinates.setPreferredSize(new Dimension(150, height));
		selectedPointCoordinates.setOpaque(true);
		
		right.add(selectedLabel);
		right.add(selectedPointCoordinates);
		
		add(left, BorderLayout.WEST);
		add(right, BorderLayout.EAST);
	
	}
	
	@Override
	public void setNumberOfPoints(String number) {
		numberLabel.setText(number);
	}
	
	/**
	 * {@inheritDoc}
	 * The mouse pointer coordinates refer to the coordinate system
	 * of the model.
	 */
	@Override
	public void setMouseCoordinates(String x, String y) {
		String coordinates = "";
		if(x != "" && y != "") {
			coordinates = x + " | " + y;
		}
		
		mouseCoordinates.setText(coordinates);
	}
	
	/**
	 * {@inheritDoc}
	 * The x and y coordinates of the selected point
	 * refer to the coordinate system of the model.
	 */
	@Override
	public void setCoordindatesOfSelectedPoint(String x, String y) {
		String coordinates = "";
		if(x != "" && y != "") {
			coordinates = x + " | " + y;
		}
		
		selectedPointCoordinates.setText(coordinates);
	}
}
