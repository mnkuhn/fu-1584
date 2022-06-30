package propra22.q8493367.file;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileChooser extends JFileChooser {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileChooser(String filePath) {
		super(filePath);
		FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("points file", "points");
		setFileFilter(fileFilter);
		setAcceptAllFileFilterUsed(false);
	}

	@Override
	public void approveSelection() {
		File selectedFile = getSelectedFile();

		if (selectedFile.exists() && getDialogType() == SAVE_DIALOG) {
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
		} else {
			super.approveSelection();
			return;
		}
	}
}
