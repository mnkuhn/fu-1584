package propra22.q8493367.status;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

public class StatusBar extends JPanel implements IStatusBar{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel numberLabel;
	private int height = 18;
	
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
	
	@Override
	public void setNumberOfPoints(int number) {
		String numberString = String.valueOf(number);
		numberLabel.setText(numberString);
	}
    
	
}
