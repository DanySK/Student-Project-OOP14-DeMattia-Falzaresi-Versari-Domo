package domo.educational.mvc;

/**
 * 
 * @author Simone De Mattia - simopne.demattia@studio.unibo.it
 *
 */
public class Controller implements AbstractObserverInterface {

	private final ViewInterface view;
	private Flat myFlat;
	


	/**
	 * 
	 * @param tView -
	 * @param tModel -
	 */
	public Controller(final ViewInterface tView) {
		this.view = tView;
		view.setObserver(this);
	}
	/*
	@Override
	public void textChangedFromView(final String text) {
		model.setModelText(text);
		view.updateView(model.getModelText());
	}
*/
	@Override
	public void newProject(final String imagePath) {
		System.out.println("controller: NewProject ");
		this.myFlat = new Flat("Default Flat");
		this.myFlat.setImage(imagePath);
		
	}

	@Override
	public void saveProject(final String fileName) {
		System.out.println("controller: SaveProject");
		
	}

	@Override
	public void openProject(final String fileName) {
		System.out.println("controller: OpenProject");
		
	}


}
