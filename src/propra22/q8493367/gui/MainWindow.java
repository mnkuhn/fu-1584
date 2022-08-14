package propra22.q8493367.gui;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.event.MenuEvent;

import propra22.q8493367.controllers.IDrawPanel;
import propra22.q8493367.controllers.IMainWindow;
import propra22.q8493367.entities.Point;
import propra22.q8493367.usecases.CommandEvent;
import propra22.q8493367.usecases.CommandEventType;
import propra22.q8493367.usecases.FileEvent;
import propra22.q8493367.usecases.FileEventType;
import propra22.q8493367.usecases.RandomPointsEvent;
import propra22.q8493367.usecases.RandomPointsEventType;
import propra22.q8493367.usecases.ViewEvent;

/**
 * The Class MainWindow represents the main window of the 
 * application. It surrounds the draw panel and the status bar
 * and provides a menu bar for interaction with the user.
 */
public class MainWindow extends JFrame implements IMainWindow {
	
	/**
	 * The Constant serialVersionUID which is used to check 
	 * if a serialized object has the right version.
	 */
	private static final long serialVersionUID = 1L;

	/** The main window listener. */
	private IMainWindowListener mainWindowListener;
	
	/** The redo menu item. By this item the user can 
	 *  redo an undone commands.
	 */
	private JMenuItem redoItem;
	
	/** The undo menu item. By this item the user can
	 * undo a command.
	 */
	private JMenuItem undoItem;
	
	/** The convex hull item. By this item the user
	 * can choose if the convex hull is to be shown.
	 */
	private JCheckBoxMenuItem convexHullItem;
	
	/** The diameter item. By this item the user
	 * can choose, if the diameter is to be shown.
	 */
	private JCheckBoxMenuItem diameterItem;
	
	/** The quadrangle item. By this item the user
	 * can choose, if the quadrangle is to be shown.
	 */
	private JCheckBoxMenuItem quadrangleItem;
	
	/** The triangle item. By this item the user
	 *  can choose, if the triangle is to be 
	 *  shown. 
	 */
	private JCheckBoxMenuItem triangleItem;
	
	/** The animation item. By this item, the user
	 * can choose, if the animation is to be shown.
	 */
	private JCheckBoxMenuItem animationItem;
	
	/** The draw panel. */
	private IDrawPanel drawPanel;
	
	
	

	/**
	 * Instantiates a new main window.
	 *
	 * @param drawPanel the draw panel
	 * @param statusBar the status bar
	 */
	public MainWindow(DrawPanel drawPanel, JPanel statusBar) {
		setLayout(new BorderLayout());
		
		this.drawPanel = drawPanel;
		
		JMenuBar menuBar = new JMenuBar();
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public  void windowClosing(WindowEvent e) {
				mainWindowListener.FileEventOccured(new FileEvent(FileEventType.EXIT));
			}
		});
		
		createFileMenu(menuBar);
		createEditMenu(menuBar);
		createViewMenu(menuBar);
		createHelpMenu(menuBar);
		
		setJMenuBar(menuBar);
	     
	
		add(drawPanel, BorderLayout.CENTER);
		
		add(statusBar,BorderLayout.SOUTH);
		pack();
	
		
		setMinimumSize(new Dimension(GUISettings.minimumWidth, GUISettings.minimumHeight));
		setLocationRelativeTo(null);
		
		
	}

	
	/**
	 * Creates the file menu.
	 *
	 * @param menuBar the menu bar
	 */
	private void createFileMenu(JMenuBar menuBar) {
		JMenu fileMenu = new JMenu("Datei");
		
		JMenuItem newFileItem = new JMenuItem("Neu");
		newFileItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindowListener.FileEventOccured(new FileEvent(FileEventType.NEW));
			}	
		});
		
		JMenuItem loadFileItem = new JMenuItem("Öffnen");
		loadFileItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindowListener.FileEventOccured(new FileEvent(FileEventType.OPEN));	
			}
		});
		JMenuItem saveFileItem = new JMenuItem("Speichern");
		saveFileItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindowListener.FileEventOccured(new FileEvent(FileEventType.SAVE));	
				
			}	
		});
		
		JMenuItem saveAsFileItem = new JMenuItem("Speichern unter");
		saveAsFileItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindowListener.FileEventOccured(new FileEvent(FileEventType.SAVE_AS));		
			}
		});
		
		JMenuItem exitItem = new JMenuItem("Beenden");
		exitItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindowListener.FileEventOccured(new FileEvent(FileEventType.EXIT));	
			}	
		});
		
		fileMenu.add(newFileItem);
		fileMenu.add(loadFileItem);
		fileMenu.add(saveFileItem);
		fileMenu.add(saveAsFileItem);
		fileMenu.add(exitItem);
		
		menuBar.add(fileMenu);
	}
	
	
	
	/**
	 * Creates the edit menu.
	 *
	 * @param menuBar the menu bar
	 */
	private void createEditMenu(JMenuBar menuBar) {
		JMenu editMenu = new JMenu("Bearbeiten");
		editMenu.addMenuListener(new MenuAdapter() {
			@Override
			public void menuSelected(MenuEvent e) {
				mainWindowListener.editEventOccured();
			}
		});
		
		undoItem = new JMenuItem("Rückgängig");
		redoItem = new JMenuItem("Wiederherstellen");
		
		undoItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindowListener.commandEventOccured(new CommandEvent(CommandEventType.UNDO));
			}
		});
		
		
		redoItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindowListener.commandEventOccured(new CommandEvent(CommandEventType.REDO));		
			}
		});
		
        undoItem.setEnabled(false);
        redoItem.setEnabled(false);
        
        editMenu.add(undoItem);
		editMenu.add(redoItem);
		
		JSeparator separator = new JSeparator();
		editMenu.add(separator);
        
        
        JMenuItem tenPointsItem = new JMenuItem("10 neue Punkte");
        tenPointsItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Point bottomLeft = drawPanel.translatePointFromViewToModel(new Point(GUISettings.margin, ((JPanel)drawPanel).getHeight() - 1 - GUISettings.margin));
				Point topRight = drawPanel.translatePointFromViewToModel(new Point(((JPanel)drawPanel).getWidth() - 1 - 2 * GUISettings.radius, 
						GUISettings.radius));
				mainWindowListener.insertRandomPointsEventOccured(new RandomPointsEvent(RandomPointsEventType.TEN, 
						bottomLeft.getX(), bottomLeft.getY(), topRight.getX(), topRight.getY()));
			}
        });
        
        JMenuItem fiftyPointsItem = new JMenuItem("50 neue Punkte");
        fiftyPointsItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Point bottomLeft = drawPanel.translatePointFromViewToModel(new Point(GUISettings.margin, ((JPanel)drawPanel).getHeight() - 1 - GUISettings.margin));
				Point topRight = drawPanel.translatePointFromViewToModel(new Point(((JPanel)drawPanel).getWidth() - 1 - 2 * GUISettings.radius, 
						GUISettings.radius));
				mainWindowListener.insertRandomPointsEventOccured(new RandomPointsEvent(RandomPointsEventType.FIFTY, 
						bottomLeft.getX(), bottomLeft.getY(), topRight.getX(), topRight.getY()));
			}
        });
       
        JMenuItem hundredPointsItem = new JMenuItem("100 neue Punkte");
        hundredPointsItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Point bottomLeft = drawPanel.translatePointFromViewToModel(new Point(GUISettings.margin, ((JPanel)drawPanel).getHeight() - 1 - GUISettings.margin));
				Point topRight = drawPanel.translatePointFromViewToModel(new Point(((JPanel)drawPanel).getWidth() - 1 - 2 * GUISettings.radius, 
						GUISettings.radius));
				mainWindowListener.insertRandomPointsEventOccured(new RandomPointsEvent(RandomPointsEventType.HUNDRED, 
						bottomLeft.getX(), bottomLeft.getY(), topRight.getX(), topRight.getY()));
				
			}	
        });
        
        JMenuItem fivehundredPointsItem = new JMenuItem("500 neue Punkte");
        fivehundredPointsItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Point bottomLeft = drawPanel.translatePointFromViewToModel(new Point(GUISettings.margin, ((JPanel)drawPanel).getHeight() - 1 - GUISettings.margin));
				Point topRight = drawPanel.translatePointFromViewToModel(new Point(((JPanel)drawPanel).getWidth() - 1 - 2 * GUISettings.radius, 
						GUISettings.radius));
				mainWindowListener.insertRandomPointsEventOccured(new RandomPointsEvent(RandomPointsEventType.FIVEHUNDRED, 
						bottomLeft.getX(), bottomLeft.getY(), topRight.getX(), topRight.getY()));
			}	
        });
        
        JMenuItem thousandPointsItem = new JMenuItem("1000 neue Punkte");
        thousandPointsItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Point bottomLeft = drawPanel.translatePointFromViewToModel(new Point(GUISettings.margin, ((JPanel)drawPanel).getHeight() - 1 - GUISettings.margin));
				Point topRight = drawPanel.translatePointFromViewToModel(new Point(((JPanel)drawPanel).getWidth() - 1 - 2 * GUISettings.radius, 
						GUISettings.radius));
				mainWindowListener.insertRandomPointsEventOccured(new RandomPointsEvent(RandomPointsEventType.THOUSAND, 
						bottomLeft.getX(), bottomLeft.getY(), topRight.getX(), topRight.getY()));
			}	
        });
       
        
		editMenu.add(tenPointsItem);
		editMenu.add(fiftyPointsItem);
		editMenu.add(hundredPointsItem);
		editMenu.add(fivehundredPointsItem);
		editMenu.add(thousandPointsItem);
		
		menuBar.add(editMenu);
	}
	
	/**
	 * Creates the view menu.
	 *
	 * @param menuBar the menu bar
	 */
	private void createViewMenu(JMenuBar menuBar) {
		JMenu viewMenu = new JMenu("Ansicht");
		viewMenu.addMenuListener(new MenuAdapter() {
			@Override
			public void menuSelected(MenuEvent e) {
				mainWindowListener.viewEventOccured(null);
			}
		});
		
		ActionListener viewActionListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindowListener.viewEventOccured(new ViewEvent(e.getSource(), 
						convexHullItem.isSelected(), 
						diameterItem.isSelected(), 
						quadrangleItem.isSelected(), 
						triangleItem.isSelected(), 
						animationItem.isSelected()));	
			}	
	    };
	    
		convexHullItem = new JCheckBoxMenuItem("Konvexe Hülle");
	    convexHullItem.addActionListener(viewActionListener);
	    
	   
		diameterItem = new JCheckBoxMenuItem("Durchmesser");
		diameterItem.addActionListener(viewActionListener);
		
	
		quadrangleItem = new JCheckBoxMenuItem("Grösstes Viereck");
		quadrangleItem.addActionListener(viewActionListener);
		
		
		triangleItem = new JCheckBoxMenuItem("Grösstes Dreieck");
		triangleItem.addActionListener(viewActionListener);
		
		animationItem = new JCheckBoxMenuItem("Animation");
		animationItem.addActionListener(viewActionListener);
		
		viewMenu.add(convexHullItem);
		viewMenu.add(diameterItem);
		viewMenu.add(quadrangleItem);
		viewMenu.add(triangleItem);
		viewMenu.add(animationItem);
		
		viewMenu.addSeparator();
		JMenuItem centerItem = new JMenuItem("Zentrieren");
		centerItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindowListener.centerViewEventOccured();
				
			}
		});
		viewMenu.add(centerItem);
		
		menuBar.add(viewMenu);
	}
	
	
	
	/**
	 * Creates the help menu.
	 *
	 * @param menuBar the menu bar
	 */
	private void createHelpMenu(JMenuBar menuBar) {
		JMenu helpMenu = new JMenu("Hilfe");
		JMenuItem manualItem = new JMenuItem("Anleitung");
		manualItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindowListener.showManualEventOccured();	
			}
		});
		
		helpMenu.add(manualItem);
		menuBar.add(helpMenu);
	}
	

	@Override
	public void setUndoEnabled(boolean b) {
		undoItem.setEnabled(b);
	}
	
	
	@Override
	public void setRedoEnabled(boolean b) {
		redoItem.setEnabled(b);
	}
	
	
	
	@Override
	public void setMainWindowListener(IMainWindowListener mainWindowListener) {
		this.mainWindowListener = mainWindowListener;
	}


	
	@Override
	public void showManual(String relativePath) {
		File file = new File(relativePath);
		try {
			Desktop.getDesktop().browse(file.toURI());
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	
	@Override
	public void setConvexHullIsShown(boolean b) {
		convexHullItem.setSelected(b);
		}
	
	
	@Override
	public void setDiameterIsShown(boolean b) {
		diameterItem.setSelected(b);
		}
	
	
	@Override
	public void setQuadrangleIsShown(boolean b) {
		quadrangleItem.setSelected(b);
		}
	
	
	
	@Override
	public void setTriangleIsShown(boolean b) {
		triangleItem.setSelected(b);
		}
    

	
	@Override
	public void setAnimationIsShown(boolean b) {
		animationItem.setSelected(b);	
	}
}
