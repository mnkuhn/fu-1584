package propra22.q8493367.main;

import java.awt.BorderLayout;
import java.awt.Dimension;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JEditorPane;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.event.MenuEvent;


import propra22.q8493367.command.CommandEvent;
import propra22.q8493367.command.CommandEventType;
import propra22.q8493367.draw.view.DrawPanel;
import propra22.q8493367.draw.view.IDrawPanel;
import propra22.q8493367.file.FileEvent;
import propra22.q8493367.file.FileEventType;
import propra22.q8493367.point.IPoint;
import propra22.q8493367.point.Point;
import propra22.q8493367.point.RandomPointsEvent;
import propra22.q8493367.point.RandomPointsEventType;
import propra22.q8493367.settings.Settings;




/**
 * The Class MainWindow represents the main window of the 
 * application
 */
public class MainWindow extends JFrame implements IMainWindow {
	
	
	/** The main window listener. */
	private IMainWindowListener mainWindowListener;
	
	/** The redo menu item. By this item the user can 
	 * redo an undone command
	 */
	private JMenuItem redoItem;
	
	/** The undo menu item. By this item the user can
	 * undo a command
	 */
	private JMenuItem undoItem;
	
	/** The convex hull item. By this item the user
	 * can choose if the convex hull should be shown.
	 */
	private JCheckBoxMenuItem convexHullItem;
	
	/** The diameter item. By this item the user
	 * can choose, if the diameter should be shown.
	 */
	private JCheckBoxMenuItem diameterItem;
	
	/** The quadrangle item. By this item the user
	 * can choose, if the quadrangle should be shown
	 */
	private JCheckBoxMenuItem quadrangleItem;
	
	/** The triangle item. By this item the user
	 * can choose, if the triangle should be 
	 * shown. 
	 */
	private JCheckBoxMenuItem triangleItem;
	
	/** The animation item. By this item, the user
	 * can choose, if the animation should be shown
	 */
	private JCheckBoxMenuItem animationItem;
	
	/** The draw panel. */
	private IDrawPanel drawPanel;
	
	/** The status bar. */
	private JPanel statusBar;
	

	/**
	 * Instantiates a new main window.
	 *
	 * @param drawPanel the draw panel
	 * @param statusBar the status bar
	 */
	public MainWindow(DrawPanel drawPanel, JPanel statusBar) {
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int preferredWidth = (int) (screenSize.width * Settings.panelToScreenWidhtRatio);
		int preferredHeight = (int) (screenSize.height * Settings.panelToScreenHeightRatio);
		setSize(new Dimension(preferredWidth, preferredHeight));
		setLayout(new BorderLayout());
		
		this.drawPanel = drawPanel;
		this.statusBar = statusBar;
		
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
		add(statusBar, BorderLayout.SOUTH);
		
		setLocationRelativeTo(null);
	}

   
	


	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setUndoEnabled(boolean b) {
		undoItem.setEnabled(b);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setRedoEnabled(boolean b) {
		redoItem.setEnabled(b);
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
				IPoint bottomLeft = drawPanel.getViewPointTranslatedToModelPoint(new Point(Settings.radius, ((JPanel)drawPanel).getHeight() - 1 - 2 * Settings.radius));
				IPoint topRight = drawPanel.getViewPointTranslatedToModelPoint(new Point(((JPanel)drawPanel).getWidth() - 1 - 2 * Settings.radius, 
						Settings.radius));
				mainWindowListener.insertRandomPointsEventOccured(new RandomPointsEvent(RandomPointsEventType.TEN, 
						bottomLeft.getX(), bottomLeft.getY(), topRight.getX(), topRight.getY()));
			}
        });
        
        JMenuItem fiftyPointsItem = new JMenuItem("50 neue Punkte");
        fiftyPointsItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				IPoint bottomLeft = drawPanel.getViewPointTranslatedToModelPoint(new Point(Settings.radius, ((JPanel)drawPanel).getHeight() - 1 - 2 * Settings.radius));
				IPoint topRight = drawPanel.getViewPointTranslatedToModelPoint(new Point(((JPanel)drawPanel).getWidth() - 1 - 2 * Settings.radius, 
						Settings.radius));
				mainWindowListener.insertRandomPointsEventOccured(new RandomPointsEvent(RandomPointsEventType.FIFTY, 
						bottomLeft.getX(), bottomLeft.getY(), topRight.getX(), topRight.getY()));
			}
        });
       
        JMenuItem hundredPointsItem = new JMenuItem("100 neue Punkte");
        hundredPointsItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				IPoint bottomLeft = drawPanel.getViewPointTranslatedToModelPoint(new Point(Settings.radius, ((JPanel)drawPanel).getHeight() - 1 - 2 * Settings.radius));
				IPoint topRight = drawPanel.getViewPointTranslatedToModelPoint(new Point(((JPanel)drawPanel).getWidth() - 1 - 2 * Settings.radius, 
						Settings.radius));
				mainWindowListener.insertRandomPointsEventOccured(new RandomPointsEvent(RandomPointsEventType.HUNDRED, 
						bottomLeft.getX(), bottomLeft.getY(), topRight.getX(), topRight.getY()));
				
			}	
        });
        
        JMenuItem fivehundredPointsItem = new JMenuItem("500 neue Punkte");
        fivehundredPointsItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				IPoint bottomLeft = drawPanel.getViewPointTranslatedToModelPoint(new Point(Settings.radius, ((JPanel)drawPanel).getHeight() - 1 - 2 * Settings.radius));
				IPoint topRight = drawPanel.getViewPointTranslatedToModelPoint(new Point(((JPanel)drawPanel).getWidth() - 1 - 2 * Settings.radius, 
						Settings.radius));
				mainWindowListener.insertRandomPointsEventOccured(new RandomPointsEvent(RandomPointsEventType.FIVEHUNDRED, 
						bottomLeft.getX(), bottomLeft.getY(), topRight.getX(), topRight.getY()));
			}	
        });
        
        JMenuItem thousandPointsItem = new JMenuItem("1000 neue Punkte");
        thousandPointsItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				IPoint bottomLeft = drawPanel.getViewPointTranslatedToModelPoint(new Point(Settings.radius, ((JPanel)drawPanel).getHeight() - 1 - 2 * Settings.radius));
				IPoint topRight = drawPanel.getViewPointTranslatedToModelPoint(new Point(((JPanel)drawPanel).getWidth() - 1 - 2 * Settings.radius, 
						Settings.radius));
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
	    convexHullItem.setSelected(Settings.defaultConvexHullIsShown);
	    convexHullItem.addActionListener(viewActionListener);
	   
		diameterItem = new JCheckBoxMenuItem("Durchmesser");
		diameterItem.setSelected(Settings.defaultDiameterIsShown);
		diameterItem.addActionListener(viewActionListener);
		
		quadrangleItem = new JCheckBoxMenuItem("Grösstes Rechteck");
		quadrangleItem.setSelected(Settings.defaultQuadrangleIsShown);
		quadrangleItem.addActionListener(viewActionListener);
		
		triangleItem = new JCheckBoxMenuItem("Grösstes Dreieck");
		triangleItem.setSelected(Settings.defaultTriangleIsShown);
		triangleItem.addActionListener(viewActionListener);
		
		animationItem = new JCheckBoxMenuItem("Animation");
		animationItem.setSelected(false);
		animationItem.addActionListener(viewActionListener);
		
		viewMenu.add(convexHullItem);
		viewMenu.add(diameterItem);
		viewMenu.add(quadrangleItem);
		viewMenu.add(triangleItem);
		
		viewMenu.addSeparator();
		
		viewMenu.add(animationItem);
		
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
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setMainWindowListener(IMainWindowListener mainWindowListener) {
		this.mainWindowListener = mainWindowListener;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void showManual(URL url) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(this);
		frame.setSize(400, 600);
		try {
			JEditorPane editorPane = new JEditorPane(url);
			editorPane.setEditable(false);
			JScrollPane scrollPane = new JScrollPane(editorPane);
			scrollPane.setPreferredSize(new Dimension(420, 600));
			scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			frame.add(scrollPane);
			frame.setVisible(true);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setConvexHullIsShown(boolean b) {
		convexHullItem.setSelected(b);
		}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setDiameterIsShown(boolean b) {
		diameterItem.setSelected(b);
		}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setQuadrangleIsShown(boolean b) {
		quadrangleItem.setSelected(b);
		}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setTriangleIsShown(boolean b) {
		triangleItem.setSelected(b);
		}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setAnimationIsShown(boolean b) {
		animationItem.setSelected(false);	
	}
}
