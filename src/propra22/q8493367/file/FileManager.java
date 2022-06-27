package propra22.q8493367.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import propra22.q8493367.draw.controller.DrawPanelController;
import propra22.q8493367.draw.model.IPointSet;
import propra22.q8493367.draw.model.PointSet;
import propra22.q8493367.main.IMainWindow;
import propra22.q8493367.main.IMainWindowListener;
import propra22.q8493367.point.IPoint;
import propra22.q8493367.settings.Settings;

/**
 * The Class FileManager handles all tasks related to creating a new file,
 * opening a file, saving a file and saving a file before terminating the
 * program.
 */
public class FileManager extends JFileChooser implements IFileManager {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DrawPanelController drawPanelController;
	private IPointSet pointSet;
	private IParser parser;
	private IMainWindowListener mainWindowListener;

	private String suffix = ".points";
	private List<IFileManagerListener> listeners = new ArrayList<>();

	/** The draw panel controller. */
	/*
	 * IDrawPanelController drawPanelController;
	 */

	/**
	 * The parser for /* IParser parser = new Parser();
	 * 
	 * /** The main view.
	 */
	IMainWindow mainWindow = null;

	/** The file path. */
	private String filePath = null;

	private boolean handlingWasCancelled = false;

	/**
	 * Instantiates a new fileManager.
	 *
	 * @param the controller of the drawPanel
	 * @param the main window
	 */
	public FileManager(PointSet pointSet, DrawPanelController drawPanelController, Parser parser) {
		super(Settings.defaultFilePath);
		this.pointSet = pointSet;
		this.drawPanelController = drawPanelController;
		this.parser = parser;

		FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("points file", "points");
		setFileFilter(fileFilter);

	}

	public FileManager(IPointSet pointSet, IParser parser) {
		this.pointSet = pointSet;
		this.parser = parser;
	}

	/**
	 * Handles a FileEvent
	 * 
	 * @param e - the handled FileEvent
	 */
	@Override
	public void handleFileEvent(FileEvent e) {

		FileEventType type = e.getFileEventType();

		switch (type) {
		case NEW: {
			int dialogOption = JOptionPane.YES_OPTION;
			if (!pointSet.isEmpty() && pointSet.hasChanged()) {

				dialogOption = showSaveToFileOptionPane();
				if (dialogOption == JOptionPane.OK_OPTION) {
					if (filePath != null) {
						savePointSet(new File(filePath));
					} else {
						int fileChooserOption = showSaveDialog(null);
						if (fileChooserOption == JFileChooser.APPROVE_OPTION) {
							approveSelection();
						}
					}
				}
			}

			if (dialogOption != JOptionPane.CANCEL_OPTION) {
				drawPanelController.setShowAnimation(false);
				pointSet.clear();
				updateDrawPanelController();
				pointSet.setHasChanged(false);
				filePath = null;
				fileEventOccured();

			}

			break;
		}

		case OPEN: {
			int dialogOption = JOptionPane.YES_OPTION;
			if (!pointSet.isEmpty() && pointSet.hasChanged()) {
				dialogOption = showSaveToFileOptionPane();
				if (dialogOption == JOptionPane.OK_OPTION) {
					if (filePath != null) {
						savePointSet(new File(filePath));

						// filePath is null
					} else {
						int fileChooserOption = showSaveDialog(null);
						if (fileChooserOption == JFileChooser.APPROVE_OPTION) {
							approveSelection();
						}
					}
				}
			}

			if (dialogOption != JOptionPane.CANCEL_OPTION) {
				int fileChooserOption = showOpenDialog(null);
				if (fileChooserOption == JFileChooser.APPROVE_OPTION) {
					approveSelection();
					updateDrawPanelController();
					pointSet.setHasChanged(false);
				}
			}
			break;
		}

		case SAVE: {
			if (filePath != null) {
				savePointSet(new File(filePath));
			} else {
				int fileChooserOption = showSaveDialog(null);
				if (fileChooserOption == JFileChooser.APPROVE_OPTION) {
					approveSelection();
				}
			}
			break;
		}

		case SAVE_AS: {
			int fileChooserOption = showSaveDialog(null);
			if (fileChooserOption == JFileChooser.APPROVE_OPTION) {
				approveSelection();
			}
			break;
		}

		case EXIT: {
			int dialogOption = JOptionPane.YES_OPTION;
			if (pointSet.hasChanged()) {
				dialogOption = JOptionPane.showConfirmDialog(null, "Datei speichern?", "",
						JOptionPane.YES_NO_CANCEL_OPTION);
				if (dialogOption == JOptionPane.OK_OPTION) {
					if (filePath != null) {
						savePointSet(new File(filePath));
						System.exit(0);
					} else {
						int fileChooserOption = showSaveDialog(null);
						if (fileChooserOption == JFileChooser.APPROVE_OPTION) {
							approveSelection();
						}
					}
				}
			}
			if (dialogOption != JOptionPane.CANCEL_OPTION) {
				System.exit(0);
			}
			break;
		}
		}
	}

	private void updateDrawPanelController() {
		drawPanelController.updateModel();
		drawPanelController.initializeView();
	}

	// Saves the data of the model into a file
	private void saveFile(String path) {
		savePointSet(new File(path));
		filePath = path;
	}

	/**
	 * Loads the data of a file into the model.
	 */

	public void savePointSet(File file) {

		try {
			FileWriter fileWriter = new FileWriter(file);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			for (int i = 0; i < pointSet.getNumberOfPoints(); i++) {
				IPoint point = pointSet.getPointAt(i);
				printWriter.println(point.toString());
			}
			printWriter.close();
			pointSet.setHasChanged(false);
			handlingWasCancelled = false;
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void loadPointsToPointSet(File file) {

		try {
			long start = System.currentTimeMillis();
			FileReader fileReader = new FileReader(file);
			BufferedReader reader = new BufferedReader(fileReader);

			String line;
			pointSet.clear();

			while ((line = reader.readLine()) != null) {
				IPoint point = parser.parseLine(line);
				if (point != null) {
					pointSet.addPoint(point);
				}
			}
			reader.close();
			pointSet.setHasChanged(true);
			long end = System.currentTimeMillis();
			System.out.println("Punktmenge von Datei einlesen: " + (end - start) + " ms");
			// for FileReader(file)
		} catch (FileNotFoundException e) {
			// show dialog
			e.printStackTrace();
			// for readLine()
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	

	
	private int showSaveToFileOptionPane() {
		int choice = JOptionPane.showConfirmDialog(null, "In Datei speichern?", "", JOptionPane.YES_NO_CANCEL_OPTION);
		return choice;
	}

	@Override
	public void approveSelection() {
		File selectedFile = getSelectedFile();
		if (selectedFile.exists() && getDialogType() == SAVE_DIALOG) {
			int result = JOptionPane.showConfirmDialog(this,
					"Die Datei existiert bereits. Soll sie überschrieben werden?", "Existing file",
					JOptionPane.YES_NO_CANCEL_OPTION);
			switch (result) {
			case JOptionPane.YES_OPTION:
				savePointSet(selectedFile);
				return;
			case JOptionPane.CANCEL_OPTION:
				return;
			default:
				return;
			}
		} else if (selectedFile.exists() && getDialogType() == OPEN_DIALOG) {
			drawPanelController.setShowAnimation(false);
			loadPointsToPointSet(selectedFile);

			filePath = selectedFile.getAbsolutePath();
			fileEventOccured();
		}
		super.approveSelection();
	}
	
	@Override
	public void addListener(IFileManagerListener observer) {
		listeners.add(observer);
	}
	
	@Override
	public void removeListener(IFileManagerListener listener) {
		listeners.remove(listener);
	}
	
	@Override
	public void fileEventOccured() {
		for(IFileManagerListener listener : listeners) {
			listener.update();
		}
	}
	
}
