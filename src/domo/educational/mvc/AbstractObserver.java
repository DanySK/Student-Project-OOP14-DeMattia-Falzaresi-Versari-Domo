package domo.educational.mvc;

/**
 * 
 * @author Simone De Mattia - simopne.demattia@studio.unibo.it
 *
 */
public abstract class AbstractObserver implements AbstractObserverInterface {

	/**
	 * 
	 */
	private ViewInterface view;
	
	public abstract void newProject(String imagePath);
	
	public abstract void saveProject(String fileName);
	
	public abstract void openProject(String fileName);
	
	/**
	 * 
	 * @param tView -
	 */
	public void setView(final ViewInterface tView) {
		this.view = tView;
	}
	
}
