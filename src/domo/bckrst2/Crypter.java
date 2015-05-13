package domo.bckrst2;

	/**
	 * 
	 * @author Stefano Falzaresi Stefano.Falzaresi2@studio.unibo.it
	 *
	 * This is a useful class for manage encryption and decryption
	 */
public interface Crypter {

	/**
	 * With this class is possible encrypt the given encrypted file.
	 * @throws Exception Encryption exception
	 */
	void doEncryption() throws Exception;
	/**
	 * Whit this class is possible to decrypt a previously encrypted file.
	 * @throws Exception decryption exception
	 */
	void doDecryption() throws Exception;
}
