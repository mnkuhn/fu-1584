package propra22.q8493367.status;

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
	
	private JLabel mouseCoordinatesX;
	private JLabel mouseCoordinatesY;
	
	private JLabel pointWithinRangeX;
	private JLabel pointWithinRangeY;
	
	/** The height of the status bar in pixels */
	private int height = 18;
	
	/**
	 * Instantiates a new status bar.
	 */
	public StatusBar() {
		setBorder(new BevelBorder(BevelBorder.LOWERED));
		setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JLabel number = new JLabel("Anzahl Punkte:");
		number.setOpaque(true);
		numberLabel = new JLabel("", SwingConstants.LEFT);
		numberLabel.setPreferredSize(new Dimension(120, height));
		numberLabel.setOpaque(true);
		add(number);
		add(numberLabel);
		
		JLabel coordinates = new JLabel("Koordinaten:");
		coordinates.setOpaque(true);
		mouseCoordinatesX = new JLabel("", SwingConstants.LEFT);
		mouseCoordinatesX.setPreferredSize(new Dimension(60, height));
		mouseCoordinatesX.setOpaque(true);
		mouseCoordinatesY = new JLabel("", SwingConstants.LEFT);
		mouseCoordinatesY.setPreferredSize(new Dimension(60, height));
		mouseCoordinatesY.setOpaque(true);
		add(coordinates);
		add(mouseCoordinatesX);
		add(mouseCoordinatesY);
		
		JLabel selectedLabel = new JLabel("Gewählter Punkt:");
		selectedLabel.setOpaque(true);
		pointWithinRangeX = new JLabel("", SwingConstants.LEFT);
		pointWithinRangeX.setPreferredSize(new Dimension(60, height));
		pointWithinRangeX.setOpaque(true);
		pointWithinRangeY = new JLabel("", SwingConstants.LEFT);
		pointWithinRangeY.setPreferredSize(new Dimension(60, height));
		pointWithinRangeY.setOpaque(true);
		add(selectedLabel);
		add(pointWithinRangeX);
		add(pointWithinRangeY);	
	
	}
	
	/**
	 *{@inheritDoc}
	 */
	@Override
	public void setNumberOfPoints(String number) {
		numberLabel.setText(number);
	}
	
	@Override
	public void setCoordinates(String x, String y) {
		mouseCoordinatesX.setText(x);
		mouseCoordinatesY.setText(y);
	}
	
	
	@Override
	public void setSelected(String x, String y) {
		pointWithinRangeX.setText(x);
		pointWithinRangeY.setText(y);
	}
}
