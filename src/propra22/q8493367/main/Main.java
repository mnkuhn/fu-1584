package propra22.q8493367.main;
import javax.swing.SwingUtilities;
import propra22.interfaces.IHullCalculator;
import propra22.q8493367.draw.controller.DrawPanelController;
import propra22.q8493367.draw.model.PointSet;
import propra22.q8493367.draw.view.DrawPanel;
import propra22.q8493367.draw.view.DrawPanelListener;
import propra22.q8493367.draw.view.IDrawPanelListener;
import propra22.q8493367.test.HullCalculator;
import propra22.tester.Tester;


public class Main {
	
	private static void createAndShowGUI() {
		PointSet pointSet = new PointSet();
		
		DrawPanel drawPanel = new DrawPanel();
		
		DrawPanelController drawPanelController = new DrawPanelController(pointSet, drawPanel);
		IDrawPanelListener drawPanelListener = new DrawPanelListener(drawPanelController);
		drawPanel.setDrawPanelListener(drawPanelListener);
		
		MainWindow mainWindow = new MainWindow(drawPanel);
		IMainWindowListener mainWindowListner = new MainWindowListener(drawPanelController, pointSet, mainWindow);
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
