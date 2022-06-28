package propra22.q8493367.file;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileChooser extends JFileChooser {
	
	
	public FileChooser(String filePath) {
		super(filePath);
		FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("points file", "points");
		setFileFilter(fileFilter);
	}
	
	@Override
	public  void approveSelection() {
		File selecetedFile = getSelectedFile();
		if(selecetedFile.exists() && getDialogType() == SAVE_DIALOG) {
			int result = JOptionPane.showConfirmDialog(this,
					"Die Datei " + selecetedFile.getName() +  
					" existiert bereits. Soll sie überschrieben werden?", "",
					JOptionPane.YES_NO_OPTION);
			switch (result) {
			case JOptionPane.YES_OPTION:
				super.approveSelection();
				return;	
			case JOptionPane.NO_OPTION:
                return;
            case JOptionPane.CLOSED_OPTION:
                return;
			}
		}
		else {
			super.approveSelection();
		}
	
	}
}
