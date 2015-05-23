package domo.educational.mvc;

/**
 * 
 * @author Stefano Falzaresi Stefano.Falzaresi2@studio.unibo.it
 * @author Simone De Mattia simone.demattia@studio.unibo.it
 * 
 * This is a generic class for MVC Gui tests
 * 
 * The MVC pattern is composed by some classes:
 * 
 * Model:
 * 	- Flat related classes (Flat,Room,Sensor,Sensor Typology,Exceptions)
 * 	- Backup and restore classes
 * 
 * View:
 *  - View Interface (a generic interface)
 *  - View Impl (a class that implement the View Interface)
 *  
 * Controller:
 *  - Controller
 *  
 * In this case we decided to use to more classes as the Java MVC standard require:
 * 	- Abstract Observer Interface
 * 	- Abstract Observer
 * this two classes are used for send information from the View to the controller
 *
 */
public class MVCTestClass {

	public static void main(final String[] args) {
		
		final ViewInterface view = new ViewImpl("Domotic Application With MVC");
		new Controller(view);
	}

}
