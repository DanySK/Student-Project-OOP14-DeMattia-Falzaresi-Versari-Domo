package mvc;

/**
 * 
 * @author Stefano Falzaresi Stefano.Falzaresi2@studio.unibo.it
 * @author Simone De Mattia simone.demattia@studio.unibo.it
 * 
 * this is the observer class, this class implements the related interface
 * and is used by the view to send information to the controller
 *
 */
public abstract class AbstractObserver implements AbstractObserverInterface {

	/**
	 * New Project create a new flat with the image selection.
	 * @param imagePath the path of the selected image
	 */
	public abstract void newProject(String imagePath);
	/**
	 * Save project take the flat object and save everything on a txt file (only names and path are saved for this test).
	 * @param fileName the backup destination file
	 */
	public abstract void saveProject(String fileName);
	/**
	 * Open project accept a txt path file and restore flat and related image.
	 * @param fileName the file to restore
	 * @return a string with the image path
	 */
	public abstract String openProject(String fileName);

	
}
