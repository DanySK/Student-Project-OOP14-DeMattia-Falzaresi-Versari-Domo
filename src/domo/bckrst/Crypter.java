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
	 * @throws CrypterException  custom exception for encrypt procedure
	 * @throws InvalidKeyException 
	 * @throws IOException 
	 */
	void doEncryption() throws CrypterException, InvalidKeyException, IOException;
	/**
	 * Whit this class is possible to decrypt a previously encrypted file.
	 * @throws CrypterException  custom exception for decrypt procedure
	 * @throws InvalidKeyException 
	 * @throws IOException 
	 */
	void doDecryption() throws CrypterException, InvalidKeyException, IOException;
}
