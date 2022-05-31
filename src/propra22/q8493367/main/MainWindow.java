package propra22.q8493367.main;

import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JViewport;
import javax.swing.event.MenuEvent;
import javax.swing.filechooser.FileNameExtensionFilter;

import propra22.q8493367.command.CommandEvent;
import propra22.q8493367.command.CommandEventType;
import propra22.q8493367.draw.view.DrawPanel;
import propra22.q8493367.file.FileEvent;
import propra22.q8493367.file.FileEventType;
import propra22.q8493367.point.RandomPointsEvent;
import propra22.q8493367.point.RandomPointsEventType;
import propra22.q8493367.settings.Settings;



public class MainWindow extends JFrame implements IMainWindow {
	
	
	private IMainWindowListener mainWindowListener;
	private JMenuItem redoItem;
	private JMenuItem undoItem;
	private int fileChooserOption = -1;
	private JCheckBoxMenuItem convexHullItem;
	private JCheckBoxMenuItem diameterItem;
	private JCheckBoxMenuItem quadrangleItem;
	private JCheckBoxMenuItem triangleItem;
	
	private JViewport viewport;
	
	
	
	
	public MainWindow(DrawPanel drawPanel) {
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
		
		JScrollPane scrollPane = new JScrollPane(drawPanel);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		add(scrollPane);
		
		pack();
		setLocationRelativeTo(null);
		
		this.viewport = scrollPane.getViewport();
	}


	@Override
	public String showSaveFileChooser() {
		JFileChooser fileChooser = createFileChooser();
		fileChooserOption = fileChooser.showSaveDialog(this);
		File file = fileChooser.getSelectedFile();
		if(file != null) {
			String path = file.getAbsolutePath();
			return path;
		}
		return null;
	}

	

	@Override
	public File showOpenFileChooser() {
		JFileChooser fileChooser = createFileChooser();
		int choice = fileChooser.showOpenDialog(this);
		if(choice == 0) {
			File file = fileChooser.getSelectedFile();
			return file;
		}
		return null;	
	}
	
	private JFileChooser createFileChooser() {
		JFileChooser fileChooser = new JFileChooser(Settings.defaultFilePath);
		FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("points file", "points");
		fileChooser.setFileFilter(fileFilter);
		return fileChooser;
	}
	
	@Override
	public int showSaveToFileOptionPane() {
		int choice = JOptionPane.showConfirmDialog (null, "In Datei speichern?", 
				"", JOptionPane.YES_NO_CANCEL_OPTION);
		return choice;
	}
	
	
	@Override
	public void setUndoEnabled(boolean b) {
		undoItem.setEnabled(b);
	}
	
	@Override
	public void setRedoEnabled(boolean b) {
		redoItem.setEnabled(b);
	}
	

	
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
				double viewportPositionX = viewport.getViewPosition().getX();
				double viewportPositionY = viewport.getViewPosition().getY();
				mainWindowListener.insertRandomPointsEventOccured(new RandomPointsEvent(RandomPointsEventType.TEN, viewportPositionX, viewportPositionY, viewport.getSize()));
			}
        });
        
        JMenuItem fiftyPointsItem = new JMenuItem("50 neue Punkte");
        fiftyPointsItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				double viewportPositionX = viewport.getViewPosition().getX();
				double viewportPositionY = viewport.getViewPosition().getY();
				mainWindowListener.insertRandomPointsEventOccured(new RandomPointsEvent(RandomPointsEventType.FIFTY, viewportPositionX, viewportPositionY, viewport.getSize()));
			}
        });
       
        JMenuItem hundredPointsItem = new JMenuItem("100 neue Punkte");
        hundredPointsItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				double viewportPositionX = viewport.getViewPosition().getX();
				double viewportPositionY = viewport.getViewPosition().getY();
				mainWindowListener.insertRandomPointsEventOccured(new RandomPointsEvent(RandomPointsEventType.HUNDRED, viewportPositionX, viewportPositionY, viewport.getSize()));
				
			}	
        });
        
        JMenuItem fivehundredPointsItem = new JMenuItem("500 neue Punkte");
        fivehundredPointsItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				double viewportPositionX = viewport.getViewPosition().getX();
				double viewportPositionY = viewport.getViewPosition().getY();
				mainWindowListener.insertRandomPointsEventOccured(new RandomPointsEvent(RandomPointsEventType.FIVEHUNDRED, viewportPositionX, viewportPositionY, viewport.getSize()));
			}	
        });
        
        JMenuItem thousandPointsItem = new JMenuItem("1000 neue Punkte");
        thousandPointsItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				double viewportPositionX = viewport.getViewPosition().getX();
				double viewportPositionY = viewport.getViewPosition().getY();
				mainWindowListener.insertRandomPointsEventOccured(new RandomPointsEvent(RandomPointsEventType.THOUSAND, viewportPositionX, viewportPositionY, viewport.getSize()));
			}	
        });
       
        
		editMenu.add(tenPointsItem);
		editMenu.add(fiftyPointsItem);
		editMenu.add(hundredPointsItem);
		editMenu.add(fivehundredPointsItem);
		editMenu.add(thousandPointsItem);
		
		menuBar.add(editMenu);
	}
	
	private void createViewMenu(JMenuBar menuBar) {
		JMenu viewMenu = new JMenu("Ansicht");
		
		ActionListener viewActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindowListener.viewEventOccured(new ViewEvent(e.getSource(), convexHullItem.isSelected(), 
						diameterItem.isSelected(), quadrangleItem.isSelected(), triangleItem.isSelected()));	
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
		
		viewMenu.add(convexHullItem);
		viewMenu.add(diameterItem);
		viewMenu.add(quadrangleItem);
		viewMenu.add(triangleItem);
		
		menuBar.add(viewMenu);
	}
	
	
	
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
	public void setMainWindowListener(IMainWindowListener mainWindowListener) {
		this.mainWindowListener = mainWindowListener;
	}


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
	
	@Override
	public int getFileChooserOption() {
		return fileChooserOption;
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
}
