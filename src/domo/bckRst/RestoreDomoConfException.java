package domo.bckRst;

public class RestoreDomoConfException extends Exception{
	String err;
	public RestoreDomoConfException(String e) {
		this.err =e;
	}
	
	public String toString(){
		return "Error during restore procedure: "+err;
	}
}
