package domo.bckRst;
import java.io.File;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;

import domo.general.Flat;

	/**
	 * 
	 * @author Stefano Falzaresi Stefano.Falzaresi2@studio.unibo.it
	 * 
	 */
public class BackupImpl implements Backup {
	private final String fileName;
	
	/**
	 * Constructor.
	 * @param file File Name
	 * @throws Exception 
	 */
	public BackupImpl(String file) {
		this.fileName = file;
		
	}
	/**
	 *  BackupNow method allow to start an automated backup of the application.
	 *  
	 *  @return True if no error occurred False if something wrong is happened
	 */
	public boolean backupNow(Flat flatB) {
		try {
			// Creation of the document builder
			//
			DocumentBuilderFactory docBuildFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuild = docBuildFactory.newDocumentBuilder();
			Document document = docBuild.newDocument();
			
			//Creation of a new root element and add it to the document
			Element rootEle = document.createElement("domo");
			document.appendChild(rootEle);
			
			//Creation of the flat element
			Element flat = document.createElement("flat");
			rootEle.appendChild(flat);
			
			//Set Attribute for flat
			Attr flatAttr = document.createAttribute("id");
			flatAttr.setValue("1");
			flat.setAttributeNode(flatAttr);
			// shorten way
			// flat.setAttribute("id", "1");
			
			//add attribute "name" to Flat
			Element flatName = document.createElement("name");
			flatName.appendChild(document.createTextNode(flatB.getName()));
			flat.appendChild(flatName);
			
			TransformerFactory transFact = TransformerFactory.newInstance();
			Transformer trans = transFact.newTransformer();
			DOMSource dom = new DOMSource(document);
			File tmpFile = new File(System.getProperty("user.home") + System.getProperty("file.separator") + "tmp.dom");
			StreamResult strOut = new StreamResult(tmpFile);
			
			//save the file
			trans.transform(dom, strOut);
			CrypterImpl en = new CrypterImpl(tmpFile.getName(), fileName);
			en.doEncryption();
			//togliere il commento dalla riga sotto
			//tmpFile.delete();
			CrypterImpl de = new CrypterImpl("output.xml", fileName);
			de.doDecryption();
			return true; 
		}
		catch (Exception exc) {
			return false;
		}
		
	}
}
