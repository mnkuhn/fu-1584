package propra22.q8493367.draw.controller;

import java.io.File;

public interface IDrawPanelController {
	public boolean drawPanelModelIsEmpty();
	public void saveModel(String path);
	public void createNewDrawPanel();
	public void loadPointsToModel(File file);
	public boolean dataChangedSinceLastSave();
	public void undoCommand();
	public void redoCommand();
	public boolean undoIsEnabled();
	public boolean redoIsEnabled();

	public void insertRandomPoints(int number);
	
	public void updateModel();
	public void updateView();
	
}
