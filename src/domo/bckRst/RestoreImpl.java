package domo.bckRst;
import java.io.File;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import domo.general.Flat;

	/**
	 * 
	 * @author Stefano Falzaresi Stefano.Falzaresi2@studio.unibo.it
	 */
public class RestoreImpl implements Restore {

		// guarda qui http://www.mrwebmaster.it/java/xml-java-esempio-parsing-jaxp_7488_2.html
		public boolean checkFilePresence() {
			return false;
		}

		@Override
		public Flat restoreNow(String fileName) {
			Flat flOut = null;
			File tmpFile = new File(System.getProperty("user.home") + System.getProperty("file.separator") + fileName);
			
			DocumentBuilderFactory docBuildFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuild;
			try {
				docBuild = docBuildFactory.newDocumentBuilder();
				Document document = docBuild.parse(tmpFile);
				//whit this I enter in the first Child "domo"
				Node rootN = document.getFirstChild();
				
				//whit this I enter in the flat child
				Node flatN = rootN.getFirstChild();
				System.out.println(flatN.getAttributes().toString());
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
			
			
			return flOut;
		}
	
	

}
