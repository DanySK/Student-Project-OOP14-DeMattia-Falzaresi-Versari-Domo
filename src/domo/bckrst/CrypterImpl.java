package domo.bckrst;

import javax.crypto.spec.SecretKeySpec;
import javax.crypto.CipherInputStream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.crypto.Cipher;

import java.io.File;
import java.security.InvalidKeyException;

/**
 * 
 * @author Stefano Falzaresi Stefano.Falzaresi2@studio.unibo.it
 *
 */
public class CrypterImpl implements Crypter {
	
	private Cipher crypt;
	private FileOutputStream fos;
	private FileInputStream fis;
	private CipherInputStream cis;
	private SecretKeySpec secretKey;
	private String decFileName;
	private String enFileName;
	
	/**
	 * Constructor, two files are needed, the source file and the destination file.
	 * @param decFile	Decrypted File
	 * @param enFile	Encrypted File
	 * @throws CrypterException 
	 */
	public CrypterImpl(final String decFile, final String enFile) throws CrypterException {

		try {
			secretKey = new SecretKeySpec("0DeFaVe0".getBytes(), "DES");
			crypt = Cipher.getInstance("DES/ECB/PKCS5Padding");
			decFileName = decFile;
			enFileName = enFile;	
		} catch (Exception e) {
			throw new CrypterException("Error while creating a CrypterImpl instance " + e);
		}
		
	}
	
	/**
	 * This class do the encryption of the uncrypted temporary file.
	 * @throws InvalidKeyException 
	 * @throws CrypterException  
	 * @throws IOException 
	 */
	public void doEncryption() throws CrypterException, InvalidKeyException, IOException {
		crypt.init(Cipher.ENCRYPT_MODE, secretKey);
		try {
			fis = new FileInputStream(new File(decFileName));
		} catch (FileNotFoundException e1) {
			throw new CrypterException("Decrypted file not found " + e1);
		}
		final File dataFile = new File(enFileName);
	    if (!dataFile.exists()) {
	        cis = new CipherInputStream(fis, crypt);  
	        try {
	            fos = new FileOutputStream(dataFile);  
	              final byte[] b = new byte[8];  
	              int i = cis.read(b);
	              while (i != -1) {  
	                  fos.write(b, 0, i);
	                  i = cis.read(b);
	             }
	        } finally {
                if (fos != null) {
                 fos.flush();  
                 fos.close();  
                }
                 cis.close();  
                 fis.close();
	        }
	    }    
	}
	/**
	 * This class do the decryption from the encrypted file to the decrypted.
	 * @throws InvalidKeyException 
	 * @throws IOException 
	 * @throws CrypterException  
	 */
	public void doDecryption() throws CrypterException, InvalidKeyException, IOException {
		crypt.init(Cipher.DECRYPT_MODE, secretKey);
		final File dataFile = new File(enFileName);
	    try {         
	       fis = new FileInputStream(dataFile);
	    } catch (Exception e) {  
	    	throw new CrypterException("Error in the decrypting procedure: " + e);
	    }
	    if (dataFile.exists()) {
	        cis = new CipherInputStream(fis, crypt);  
	        try {
	    		final File newDataFile = new File(decFileName);
	            fos = new FileOutputStream(newDataFile);  
	              final byte[] b = new byte[8];  
	          int i = cis.read(b);
	              while (i != -1) {  
	                  fos.write(b, 0, i);
	                  i = cis.read(b);
	             }                
	        } finally {
	                if (fos != null) {
	                 fos.flush();  
	                 fos.close();
	                 }
	                 cis.close();  
	                 fis.close();
	        }
	    }
	}
}
