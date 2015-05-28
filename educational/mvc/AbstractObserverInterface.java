package mvc;

/**
 * 
 * @author Stefano Falzaresi Stefano.Falzaresi2@studio.unibo.it
 * @author Simone De Mattia simone.demattia@studio.unibo.it
 *	This is the interface of the observer used to exchange information between View and Controller
 */
public interface AbstractObserverInterface {

	/**
	 * New Project create a new flat with the image selection.
	 * @param imagePath the path of the selected image
	 */
	void newProject(String imagePath);
	
	/**
	 * Save project take the flat object and save everything on a txt file (only names and path are saved for this test).
	 * @param fileName the backup destination file
	 */
	void saveProject(String fileName);
	
	/**
	 * Open project accept a txt path file and restore flat and related image.
	 * @param fileName the file to restore
	 * @return a string with the image path
	 */
	void openProject(String fileName);
	
	
}
