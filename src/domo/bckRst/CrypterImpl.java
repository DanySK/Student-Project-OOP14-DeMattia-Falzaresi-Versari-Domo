package domo.bckRst;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 
 * @author Stefano Falzaresi Stefano.Falzaresi2@studio.unibo.it
 *
 */
public class CrypterImpl implements Crypter {
	 
	private byte key[];
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
	public CrypterImpl(String decFile, String enFile) throws Exception {
		key = "0DeFaVe0".getBytes();
		secretKey = new SecretKeySpec(key, "DES");
		crypt = Cipher.getInstance("DES/ECB/PKCS5Padding");
		decFileName = decFile;
		enFileName = enFile;
	}
	public void doEncryption() throws Exception{
		crypt.init(Cipher.ENCRYPT_MODE, secretKey);
		fis = new FileInputStream(new File(System.getProperty("user.home") + System.getProperty("file.separator") + decFileName));
		File dataFile = new File(System.getProperty("user.home") + System.getProperty("file.separator") + enFileName);
	    if (!dataFile.exists()) {
	        cis = new CipherInputStream(fis, crypt);  
	        try {
	            fos = new FileOutputStream(dataFile);  
	              byte[] b = new byte[8];  
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
	            	System.out.println("Errore: " + e);
	            }
	        }
	    }              
	    
	}
	
	public void doDecryption() throws Exception {
			crypt.init(Cipher.DECRYPT_MODE, secretKey);
			File dataFile = new File(System.getProperty("user.home") + System.getProperty("file.separator") + enFileName);
			File newDataFile = new File(System.getProperty("user.home") + System.getProperty("file.separator") + decFileName);

		    try {         
		       fis = new FileInputStream(dataFile);
		    } catch (Exception e) {  
		        //Exception
		    }  

		    if (dataFile.exists()) {
		        cis = new CipherInputStream(fis, crypt);  
		        try {
		            fos = new FileOutputStream(newDataFile);  
		              byte[] b = new byte[8];  
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
		                System.out.println("Errore: " + e);
		            }
		        }
		    }
	}
}
