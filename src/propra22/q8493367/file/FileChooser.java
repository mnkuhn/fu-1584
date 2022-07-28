package propra22.q8493367.file;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;



/**
 * The Class FileChooser extends JFileChooser. It
 * provides some extra functionality by which the user is asked
 * if he wants to overwrite an existing file.
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
	 * A JOptionPane informs the user during a save operation, that 
	 * existing file will be overwritten, if the user chooses an existing
	 * file.
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
