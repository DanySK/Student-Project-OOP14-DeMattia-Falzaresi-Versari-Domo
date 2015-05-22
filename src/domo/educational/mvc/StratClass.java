package domo.educational.mvc;

/**
 * 
 * @author Simone De Mattia - simopne.demattia@studio.unibo.it
 *
 */
public class StratClass {

	/**
	 * 
	 * @param args -
	 */
	public static void main(final String[] args) {
		
		final ViewInterface view = new ViewImpl(" - MVC - ");
		final ModelInterface model = new ModelImpl();
		new Controller(view, model);
	}

}
