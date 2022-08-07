package propra22.q8493367.gui;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;



/**
 * The Class FileChooser extends JFileChooser. It
 * provides some extra functionality by which the user is asked
 * if he wants to overwrite an existing file. The user is also informed
 * if a file could not be found or if he wants to load a point set 
 * from a file whose name has not the correct ending.
 */
public class FileChooser extends JFileChooser {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new file chooser.
	 *
	 * @param folderPath the relative path of the folder
	 * which is to be opened by the file chooser.
	 */
	public FileChooser(String folderPath) {
		super(folderPath);
		FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("points file", "points");
		setFileFilter(fileFilter);
		setAcceptAllFileFilterUsed(false);
	}

	/**
	 * The overwritten method of the JFileChooser.
	 * While saving a file, the user is asked, if he wants to overwrite 
	 * an already existing file, if the file already exists. 
	 * While opening a file, the user is also informed, if a file
	 * could not be found or if he wants to open a file 
	 * whose name has not the correct ending.
	 */
	@Override
	public void approveSelection() {
		File selectedFile = getSelectedFile();
		if (getDialogType() == SAVE_DIALOG) {
			if (selectedFile.exists()) {
				int result = JOptionPane.showConfirmDialog(this,
						"Die Datei " + selectedFile.getName() + " existiert bereits. Soll sie überschrieben werden?",
						"Datei existiert", JOptionPane.YES_NO_OPTION);
				switch (result) {
				case JOptionPane.YES_OPTION:
					if (selectedFile.getName().endsWith(".points")) {
						super.approveSelection();
					}
					return;
				case JOptionPane.NO_OPTION:
					return;
				case JOptionPane.CLOSED_OPTION:
					return;
				}
			}
			super.approveSelection();
		}

		if (getDialogType() == OPEN_DIALOG) {
			if (!selectedFile.getAbsolutePath().endsWith(".points")) {
				JOptionPane.showMessageDialog(this, "Bitte wählen Sie nur Dateien mit der Endung 'points'.",
						"Falsches Dateiformat", JOptionPane.ERROR_MESSAGE);
			} else if (!selectedFile.exists()) {
				JOptionPane.showMessageDialog(this, "Die Datei konnte nicht gefunden werden.", "",
						JOptionPane.ERROR_MESSAGE);
			} else {
				super.approveSelection();
				return;
			}
		}
	}
}
