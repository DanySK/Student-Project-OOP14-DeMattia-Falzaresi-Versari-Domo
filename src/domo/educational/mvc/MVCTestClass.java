package domo.educational.mvc;

/**
 * 
 * @author Stefano Falzaresi Stefano.Falzaresi2@studio.unibo.it
 * 
 * This is a generic class for MVC Gui tests
 * 
 * The MVC pattern is composed by some classes:
 * 
 * Model:
 * 	- Model Interface (a generic interface)
 *  - Model Impl (an implemantation class of the Model Interface)
 * 
 * View:
 *  - View Interface (a generic interface)
 *  - View Impl (a class that implement the View Interface)
 *  
 * Controller:
 *  - Abstract Observer Interface (a generic interface)
 *  -
 *
 */
public class MVCTestClass {

	public static void main(final String[] args) {
		
		final ViewInterface view = new ViewImpl("Domotic Application With MVC");
		new Controller(view);
	}

}
