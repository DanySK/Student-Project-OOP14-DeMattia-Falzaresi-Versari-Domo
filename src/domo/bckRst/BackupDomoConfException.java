package domo.bckRst;

public class BackupDomoConfException extends Exception {
	String err;
	
	public BackupDomoConfException(String e) {
		this.err=e;
	}
	
	public String toString(){
		return "Backup Got an Error: " + err;
	}
}
