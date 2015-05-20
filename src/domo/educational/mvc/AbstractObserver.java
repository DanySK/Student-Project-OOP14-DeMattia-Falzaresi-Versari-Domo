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
	
	/**
	 * @param text -
	 */
	public abstract void textChangedFromView(final String text);
	
	/**
	 * 
	 * @param tView -
	 */
	public void setView(final ViewInterface tView) {
		this.view = tView;
	}
	
}
