package domo.educational.mvc;

/**
 * 
 * @author Simone De Mattia - simopne.demattia@studio.unibo.it
 *
 */
public class Controller implements AbstractObserverInterface {

	private final ViewInterface view;
	private Flat myFlat;
	private Backup myBackup;
	private Restore myRestore;
	


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
		this.myBackup = new Backup(myFlat);
		myBackup.doBackup(fileName);
	}

	@Override
	public String openProject(final String fileName) {
		this.myRestore = new Restore();
		try{
			this.myFlat = myRestore.doRestore(fileName);
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
		return this.myFlat.getImage();
	}


}
