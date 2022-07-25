package propra22.q8493367.status;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;



/**
 * The Class StatusBar.
 */
public class StatusBar extends JPanel implements IStatusBar{
    
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The label which displays the number of points in the point set */
	private JLabel numberLabel;
	
	private JLabel mouseCoordinates;
	
	private JLabel selectedPointCoordinates;
	
	/** The height of the status bar in pixels */
	private int height = 18;
	
	/**
	 * Instantiates a new status bar.
	 */
	public StatusBar() {
		setBorder(new BevelBorder(BevelBorder.LOWERED));
		setLayout(new BorderLayout());
		
		JPanel left = new JPanel();
		JLabel number = new JLabel("Anzahl Punkte:");
		number.setOpaque(true);
		numberLabel = new JLabel("", SwingConstants.LEFT);
		numberLabel.setPreferredSize(new Dimension(120, height));
		numberLabel.setOpaque(true);
		left.add(number);
		left.add(numberLabel);
		
		JLabel coordinates = new JLabel("Maus-Koordinaten:", SwingConstants.LEFT);
		coordinates.setOpaque(true);
		mouseCoordinates = new JLabel("", SwingConstants.LEFT);
		mouseCoordinates.setPreferredSize(new Dimension(120, height));
		mouseCoordinates.setOpaque(true);
		
		/*
		mouseCoordinatesY = new JLabel("", SwingConstants.LEFT);
		mouseCoordinatesY.setPreferredSize(new Dimension(60, height));
		mouseCoordinatesY.setOpaque(true);
		*/
		JPanel right = new JPanel();
		right.add(coordinates);
		right.add(mouseCoordinates);
		//add(mouseCoordinatesY);
		
		JLabel selectedLabel = new JLabel("Gewählter Punkt:", SwingConstants.LEFT);
		selectedLabel.setOpaque(true);
		selectedPointCoordinates = new JLabel("", SwingConstants.LEFT);
		selectedPointCoordinates.setPreferredSize(new Dimension(120, height));
		selectedPointCoordinates.setOpaque(true);
		
		
		/*
		selectedPointY = new JLabel("", SwingConstants.LEFT);
		selectedPointY.setPreferredSize(new Dimension(60, height));
		selectedPointY.setOpaque(true);
		*/
		right.add(selectedLabel);
		right.add(selectedPointCoordinates);
		//add(selectedPointY);	
		
		add(left, BorderLayout.WEST);
		add(right, BorderLayout.EAST);
	
	}
	
	/**
	 *{@inheritDoc}
	 */
	@Override
	public void setNumberOfPoints(String number) {
		numberLabel.setText(number);
	}
	
	@Override
	public void setMouseCoordinates(String x, String y) {
		String coordinates = x + " | " + y;
		
		mouseCoordinates.setText(coordinates);
		//mouseCoordinatesY.setText(y);
	}
	
	
	@Override
	public void setCoordindatesOfSelectedPoint(String x, String y) {
		
		String coordinates = "";
		if(x != "" && y != "") {
			coordinates = x + " | " + y;
		}
		
		selectedPointCoordinates.setText(coordinates);
		//selectedPointY.setText(y);
	}
}
