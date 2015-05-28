package mvc;

/**
 * 
 * @author Stefano Falzaresi Stefano.Falzaresi2@studio.unibo.it
 * 
 * this is the Controller part of the MVC pattern for this project.
 * the Controller gets information from the user input (the View), 
 * change data on the Model and update the View, in this case the model is
 * composed by some classes related to our Domotic Project
 *
 */
public class Controller implements AbstractObserverInterface {

	private final ViewInterface view;
	private Flat myFlat;

	/** 
	 * This is the constructor of the controller .
	 * @param tView the instance of the view to dialogue with
	 */
	public Controller(final ViewInterface tView) {
		this.view = tView;
		view.setObserver(this);
	}

	/**
	 * New project is setted when the user press new and is used to create a new flat and set an image on it.
	 * @param imagePath the image to use as flat image
	 */
	@Override
	public void newProject(final String imagePath) {
		this.myFlat = new Flat("Default Flat");
		this.myFlat.setImage(imagePath);
		
	}

	/**
	 * Save project create a backup instance and save flat name and image to a txt file.
	 * @param fileName the location of the backup file
	 */
	@Override
	public void saveProject(final String fileName) {
		final Backup myBackup = new Backup(myFlat);
		myBackup.doBackup(fileName);
	}

	/**
	 * Open project restore a flat and its image from a backup file, the image to use is passed to the view. 
	 * to repaint the background
	 */
	@Override
	public String openProject(final String fileName) {
		final Restore myRestore = new Restore();
		try {
			this.myFlat = myRestore.doRestore(fileName);
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println(e);
			return null;
		}
		return this.myFlat.getImage();
	}


}
