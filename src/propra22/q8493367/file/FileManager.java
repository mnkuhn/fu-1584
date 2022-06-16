package propra22.q8493367.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import propra22.q8493367.draw.model.IPointSet;
import propra22.q8493367.main.IMainWindow;
import propra22.q8493367.point.IPoint;

/**
 * The Class FileManager handles all tasks related to creating a new file,
 * opening a file, saving a file and saving a file before terminating the
 * program.
 */
public class FileManager implements IFileManager {
	IPointSet pointSet;
	IParser parser;

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

	/** Data has changed since last save to file */
	private boolean dataChangedSinceLastSave = false;

	private boolean handlingWasCancelled = false;

	/**
	 * Instantiates a new fileManager.
	 *
	 * @param the controller of the drawPanel
	 * @param the main window
	 */
	public FileManager(IPointSet pointSet, IMainWindow view, IParser parser) {
		this.pointSet = pointSet;
		this.mainWindow = view;
		this.parser = parser;
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
			if (!pointSet.isEmpty() && pointSet.hasChanged()) {

				int dialogOption = mainWindow.showSaveToFileOptionPane();
				if (dialogOption == JOptionPane.OK_OPTION) {
					if (filePath != null) {
						savePointSet(filePath);
						handlingWasCancelled = false;
					} else {
						String newFilePath = mainWindow.showSaveFileChooser();
						if (mainWindow.getFileChooserOption() == JFileChooser.APPROVE_OPTION) {
							if (newFilePath != null) {
								savePointSet(newFilePath);
								pointSet.clear();
								filePath = null;
							}
							handlingWasCancelled = false;
						} else {
							handlingWasCancelled = true;
						}
					}
				} else if (dialogOption == JOptionPane.NO_OPTION) {
					pointSet.clear();
					filePath = null;
					handlingWasCancelled = false;
				} else {
					handlingWasCancelled = true;
				}
			} else {
				pointSet.clear();
				filePath = null;
				handlingWasCancelled = false;
			}
			break;
		}

		case OPEN: {

			if (!pointSet.isEmpty() && pointSet.hasChanged()) {
				int dialogOption = mainWindow.showSaveToFileOptionPane();
				if (dialogOption == JOptionPane.OK_OPTION) {
					if (filePath != null) {
						savePointSet(filePath);
						openFile();
					} else {
						String newFilePath = mainWindow.showSaveFileChooser();
						if (mainWindow.getFileChooserOption() == JFileChooser.APPROVE_OPTION) {
							if (newFilePath != null) {
								savePointSet(newFilePath);
								openFile();
							}
						}
					}
				}
				if (dialogOption == JOptionPane.NO_OPTION) {
					openFile();
				}
			} else {
				openFile();
			}
			break;
		}

		case SAVE: {
			if (pointSet.hasChanged()) {
				if (filePath != null) {
					savePointSet(filePath);
					handlingWasCancelled = false;
				} else {
					String path = mainWindow.showSaveFileChooser();
					int option = mainWindow.getFileChooserOption();
					if (option == JFileChooser.APPROVE_OPTION) {
						if (path != null) {
							saveFile(path);
							handlingWasCancelled = false;
						}

					} else if (option == JFileChooser.CANCEL_OPTION) {
						handlingWasCancelled = true;
					}
				}
			}
			break;
		}

		case SAVE_AS: {
			String path = mainWindow.showSaveFileChooser();
			int option = mainWindow.getFileChooserOption();
			if (option == JFileChooser.APPROVE_OPTION) {
				if (path != null) {
					saveFile(path);
				}
			} else if (option == JFileChooser.CANCEL_OPTION) {
				handlingWasCancelled = true;
			}
			break;
		}

		case EXIT: {

			if (pointSet.hasChanged()) {
				int dialogOption = JOptionPane.showConfirmDialog(null, "Datei speichern?", "",
						JOptionPane.YES_NO_CANCEL_OPTION);
				if (dialogOption == JOptionPane.OK_OPTION) {
					if (filePath != null) {
						savePointSet(filePath);
						System.exit(0);
					} else {
						String path = mainWindow.showSaveFileChooser();
						int option = mainWindow.getFileChooserOption();
						if (option == JFileChooser.APPROVE_OPTION) {
							if (path != null) {
								savePointSet(path);
								System.exit(0);
							}
							handlingWasCancelled = false;
						} else if (option == JFileChooser.CANCEL_OPTION) {
							handlingWasCancelled = true;
						}
					}
				}
				else if (dialogOption == JOptionPane.NO_OPTION) {
					System.exit(0);
				}
				else if (dialogOption == JOptionPane.CANCEL_OPTION) {
					handlingWasCancelled = true;
				}
			} else {
				System.exit(0);
			}
			break;
		}
		}

	}

	// Saves the data of the model into a file
	private void saveFile(String path) {
		savePointSet(path);
		filePath = path;
	}

	/**
	 * Loads the data of a file into the model.
	 */
	private void openFile() {
		File file = mainWindow.showOpenFileChooser();
		if (file != null) {
			loadPointsToPointSet(file);
			filePath = file.getAbsolutePath();
			handlingWasCancelled = false;
		} else {
			handlingWasCancelled = true;
		}
	}

	public void savePointSet(String path) {

		if (pointSet.hasChanged()) {
			File file = new File(path);
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
	}

	public void loadPointsToPointSet(File file) {

		try {
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
			// for FileReader(file)
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			// for readLine()
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean handlingWasCancelled() {
		return handlingWasCancelled;
	}
}
