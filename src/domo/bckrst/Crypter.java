package domo.bckrst;

import java.io.IOException;
import java.security.InvalidKeyException;

	/**
	 * 
	 * @author Stefano Falzaresi Stefano.Falzaresi2@studio.unibo.it
	 *
	 * This is a useful class for manage encryption and decryption
	 */
public interface Crypter {

	/**
	 * With this class is possible encrypt the given encrypted file.
	 * @throws InvalidKeyException Encryption exception
	 * @throws IOException 
	 */
	void doEncryption() throws InvalidKeyException, IOException;
	/**
	 * Whit this class is possible to decrypt a previously encrypted file.
	 * @throws InvalidKeyException Encryption exception
	 * @throws IOException 
	 */
	void doDecryption() throws InvalidKeyException, IOException;
}
