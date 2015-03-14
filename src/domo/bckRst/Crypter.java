package domo.bckRst;

	/**
	 * 
	 * @author Stefano Falzaresi Stefano.Falzaresi2@studio.unibo.it
	 *
	 * This is a useful class for manage encryption and decryption
	 */
public interface Crypter {

	/**
	 * With this class is possible encrypt the given encrypted file.
	 * @throws Exception
	 */
	void doEncryption() throws Exception;
	/**
	 * Whit this class is possible to decrypt a previously encrypted file.
	 * @throws Exception
	 */
	void doDecryption() throws Exception;
}
