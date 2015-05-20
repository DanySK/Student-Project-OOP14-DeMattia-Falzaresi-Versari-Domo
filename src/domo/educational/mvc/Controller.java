package domo.educational.mvc;

/**
 * 
 * @author Simone De Mattia - simopne.demattia@studio.unibo.it
 *
 */
public class Controller implements AbstractObserverInterface {

	private final ViewInterface view;
	private ModelInterface model;
	


	/**
	 * 
	 * @param tView -
	 * @param tModel -
	 */
	public Controller(final ViewInterface tView, final ModelInterface tModel) {
		this.view = tView;
		this.model = tModel;
		view.setObserver(this);
	}
	
	@Override
	public void textChangedFromView(final String text) {
		model.setModelText(text);
		view.updateView(model.getModelText());
	}

	/**
	 * 
	 * @param tModel -
	 */
	public void setModel(final ModelInterface tModel) {
		this.model = tModel;
	}
	

}
