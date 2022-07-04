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
		
	}
	
	/**
	 *{@inheritDoc}
	 */
	@Override
	public void setNumberOfPoints(int number) {
		String numberString = String.valueOf(number);
		numberLabel.setText(numberString);
	}
}
