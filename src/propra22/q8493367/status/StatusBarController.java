package propra22.q8493367.status;

import propra22.q8493367.draw.controller.IDrawPanelControllerObserver;

public class StatusBarController implements IDrawPanelControllerObserver{
	
	IStatusBar statusBar;
	public StatusBarController(IStatusBar statusBar)  {
		this.statusBar = statusBar;
	}
	
	@Override
	public void update(int number) {
		statusBar.setNumberOfPoints(number);	
	}
}
