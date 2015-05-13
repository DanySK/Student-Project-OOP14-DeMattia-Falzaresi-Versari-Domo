package domo.bckrst;

import javax.crypto.spec.SecretKeySpec;
import javax.crypto.CipherInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import javax.crypto.Cipher;
import java.io.File;

/**
 * 
 * @author Stefano Falzaresi Stefano.Falzaresi2@studio.unibo.it
 *
 */
public class CrypterImpl implements Crypter {
	 
	private final byte[] key;
	private final Cipher crypt;
	private FileOutputStream fos;
	private FileInputStream fis;
	private CipherInputStream cis;
	private final SecretKeySpec secretKey;
	private final String decFileName;
	private final String enFileName;
	
	/**
	 * Constructor, two files are needed, the source file and the destination file.
	 * @param decFile	Decrypted File
	 * @param enFile	Encrypted File
	 * @throws Exception 
	 */
	public CrypterImpl(final String decFile, final String enFile) throws Exception {
		key = "0DeFaVe0".getBytes();
		secretKey = new SecretKeySpec(key, "DES");
		crypt = Cipher.getInstance("DES/ECB/PKCS5Padding");
		decFileName = decFile;
		enFileName = enFile;
	}
	
	/**
	 * This class do the encryption of the uncrypted temporary file.
	 * @throws Exception  
	 */
	public void doEncryption() throws Exception {
		crypt.init(Cipher.ENCRYPT_MODE, secretKey);
		fis = new FileInputStream(new File(decFileName));
		final File dataFile = new File(enFileName);
	    if (!dataFile.exists()) {
	        cis = new CipherInputStream(fis, crypt);  
	        try {
	            fos = new FileOutputStream(dataFile);  
	              final byte[] b = new byte[8];  
	              int i;
	              while ((i = cis.read(b)) != -1) {  
	                  fos.write(b, 0, i);  
	             }
	        } finally {
	            try {
	                if (fos != null) {
	                 fos.flush();  
	                 fos.close();  
	                }
	                 cis.close();  
	                 fis.close(); 
	            } catch (IOException e) {
	            	System.out.println("Error in the encrypting procedure: " + e);
	            }
	        }
	    }    
	}
	/**
	 * This class do the decryption from the encrypted file to the decrypted.
	 * 
	 * @throws Exception 
	 */
	public void doDecryption() throws Exception {
		crypt.init(Cipher.DECRYPT_MODE, secretKey);
		final File dataFile = new File(enFileName);
		final File newDataFile = new File(decFileName);
	    try {         
	       fis = new FileInputStream(dataFile);
	    } catch (Exception e) {  
	    	System.out.println("Error in the decrypting procedure: " + e);
	    }
	    if (dataFile.exists()) {
	        cis = new CipherInputStream(fis, crypt);  
	        try {
	            fos = new FileOutputStream(newDataFile);  
	              final byte[] b = new byte[8];  
	          int i;
	              while ((i = cis.read(b)) != -1) {  
	                  fos.write(b, 0, i);  
	             }                
	        } finally {
	            try {
	                if (fos != null) {
	                 fos.flush();  
	                 fos.close();
	                 }
	                 cis.close();  
	                 fis.close(); 
	            } catch (IOException e) {
	                System.out.println("Error: " + e);
	            }
	        }
	    }
	}
}
