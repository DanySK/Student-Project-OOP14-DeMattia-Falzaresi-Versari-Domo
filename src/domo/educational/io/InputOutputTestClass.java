package domo.educational.io;

public class InputOutputTestClass {

	/**
	 * 
	 * @author Stefano Falzaresi Stefano.Falzaresi2@studio.unibo.it
	 * 
	 * This is a generic class for Input Output tests
	 *
	 */
	public static void main(String[] args) {
		
		/* Flat Creation */
		
		final Flat myFlat = new Flat("My Flat");
		
		try {
			myFlat.addRoom(new Room("Living"));
			myFlat.addRoom(new Room("Kitchen"));
		} catch (CustomExceptions e) {
			e.printStackTrace();
		}
		
		/* Backup Procedure Start */
		final Backup myBackup = new Backup(myFlat);
		
		/* If the backup fail the program will be interrupt */
		if (myBackup.doBackup("myflat.txt")) {
			System.out.println("Backup Completed!");
		} else {
			System.out.println("Backup Got An Error!");
			System.exit(1);
		}
		
		/* Restore Procedure Start */
		final Restore myRestore = new Restore();
		try {
			final Flat myRestoredFlat = myRestore.doRestore("myflat.txt");
			System.out.println("My Restored Flat Name: " + myRestoredFlat.getName());
			
			for (final Room r : myRestoredFlat.getRooms()) {
				System.out.println("My Restored Room Name: " + r.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}

}
