package domo.educational.mvc;

/**
 * 
 * @author Simone De Mattia - simopne.demattia@studio.unibo.it
 *
 */
public interface AbstractObserverInterface {

	
	/**
	 * 
	 * @param text -
	 */
	void newProject(String imagePath);
	
	void saveProject(String fileName);
	
	void openProject(String fileName);
	
	
}
