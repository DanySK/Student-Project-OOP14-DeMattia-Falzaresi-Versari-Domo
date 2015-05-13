package domo.bckrst;

import javax.crypto.spec.SecretKeySpec;
import javax.crypto.CipherInputStream;
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
	 * @throws Exception 
	 */
	public CrypterImpl(final String decFile, final String enFile) {

		try {
			secretKey = new SecretKeySpec("0DeFaVe0".getBytes(), "DES");
			crypt = Cipher.getInstance("DES/ECB/PKCS5Padding");
			decFileName = decFile;
			enFileName = enFile;	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * This class do the encryption of the uncrypted temporary file.
	 * @throws InvalidKeyException 
	 * @throws IOException 
	 * @throws Exception  
	 */
	public void doEncryption() throws InvalidKeyException, IOException {
		crypt.init(Cipher.ENCRYPT_MODE, secretKey);
		fis = new FileInputStream(new File(decFileName));
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
	 * @throws InvalidKeyException 
	 * @throws IOException 
	 * 
	 * @throws Exception 
	 */
	public void doDecryption() throws InvalidKeyException, IOException {
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
	          int i = cis.read(b);
	              while (i != -1) {  
	                  fos.write(b, 0, i);
	                  i = cis.read(b);
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
