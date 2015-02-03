package BckRst;
import java.io.File;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;

	/**
	 * 
	 * @author Stefano Falzaresi Stefano.Falzaresi2@studio.unibo.it
	 * @version 1.0.0
	 */
public class BackupImpl implements Backup {
	private final String fileName;
	
	/**
	 * Constructor
	 * @param file: File Name
	 * @throws Exception 
	 */
	public BackupImpl(String file){
		this.fileName = file;
		
	}
	
	public boolean BackupNow(){
		try{
			// Creation of the document builder
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
			flatName.appendChild(document.createTextNode("My Flat"));
			flat.appendChild(flatName);
			
			TransformerFactory transFact = TransformerFactory.newInstance();
			Transformer trans = transFact.newTransformer();
			DOMSource dom = new DOMSource(document);
			StreamResult strOut = new StreamResult(new File(System.getProperty("user.home")+System.getProperty("file.separator")+this.fileName));
			//save the file
			trans.transform(dom, strOut);
			return true;
		}
		catch (Exception exc){
			return false;
		}
		
	}
}
