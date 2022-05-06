package propra22.q8493367.main;
import javax.swing.SwingUtilities;
import propra22.interfaces.IHullCalculator;
import propra22.q8493367.draw.controller.DrawPanelController;
import propra22.q8493367.draw.model.DrawPanelModel;
import propra22.q8493367.draw.view.DrawPanel;
import propra22.q8493367.test.HullCalculator;
import propra22.tester.Tester;


public class Main {
	
	private static void createAndShowGUI() {
		DrawPanel drawPanel = new DrawPanel();
		DrawPanelModel drawPanelModel = new DrawPanelModel();
		DrawPanelController drawPanelController = new DrawPanelController(drawPanelModel, drawPanel);
		drawPanel.setDrawPanelListener(drawPanelController);
		
		MainWindow mainWindow = new MainWindow(drawPanel);
		IMainWindowListener mainWindowListner = new MainWindowListener(drawPanelController, mainWindow);
		mainWindow.setMainWindowListener(mainWindowListner);
		mainWindow.setVisible(true);
	}
	
	public static  void main(String[] args) {
        if(args.length > 0  && "-t".equals(args[0])) {
        	IHullCalculator calculator = new HullCalculator();
        	Tester tester = new Tester(args, calculator);
        	System.out.println(tester.test());
        }
        else {
        	SwingUtilities.invokeLater(new Runnable() {
		      public void run() {
		        createAndShowGUI();
		      }
        	}); 	
        }
	}
}
