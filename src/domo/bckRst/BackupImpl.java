package domo.bckRst;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;

import domo.devices.Sensor;
import domo.general.Flat;
import domo.general.Room;

	/**
	 * 
	 * @author Stefano Falzaresi Stefano.Falzaresi2@studio.unibo.it
	 * 
	 */
public class BackupImpl implements Backup {
	private final String fileName;
	private Document document;
	
	/**
	 * Constructor.
	 * @param file File Name
	 * @throws Exception 
	 */
	public BackupImpl(String file) {
		
		try {
			DocumentBuilderFactory docBuildFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuild;
			docBuild = docBuildFactory.newDocumentBuilder();
			document = docBuild.newDocument();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.fileName = file;
		
	}
	/**
	 *  BackupNow method allow to start an automated backup of the application.
	 *  
	 *  @return True if no error occurred False if something wrong is happened
	 */
	public void backupNow(Flat flatB) throws BackupDomoConfException{
		if (flatB == null){
			throw new BackupDomoConfException("No Flat has been submitted to the backup procedure");
		}
		try {
		
			//Creation of a new root element and add it to the document
			Element rootEle = document.createElement("domo");
			document.appendChild(rootEle);
			
			//Creation of the flat element
			Element flat = document.createElement("flat");
			flat.setAttribute("Id", "1");
			rootEle.appendChild(flat);
			
			//Creation of all variables I need for the flat level
			Element nameEle = document.createElement("name");

			
			//add attribute "name" to Flat
			nameEle.appendChild(document.createTextNode(flatB.getName()));
			flat.appendChild(nameEle);
			
			//now I need to extract all rooms and backup it
			Set<Room> roomB = new HashSet<>();
			roomB = flatB.getRooms();
			for (Room room : roomB) {
				
				Element roomEl = createElement("room",room.getName(),room.getId(), flat);
				
				
				//now I need to extract all sensors for this room and backup it
				Set<Sensor> sensorB = new HashSet<>();
				sensorB = room.getSensor();
				
				for(Sensor sensor : sensorB){
					//creation of element sensor and set his ID
					try{
					Element sensorE = createElement("sensor",sensor.getName(),sensor.getId(), roomEl);
					sensorE.appendChild(addArg("image", sensor.getImagePath()));
					sensorE.appendChild(addArg("XPosition", Double.toString(sensor.getXPosition())));
					sensorE.appendChild(addArg("YPosition", Double.toString(sensor.getYPosition())));
					sensorE.appendChild(addArg("size", sensor.getSize().toString()));
					sensorE.appendChild(addArg("typology", sensor.getType().toString()));
					}
					catch (NullPointerException e){
						
					}
				}
				
				
			}
			
			TransformerFactory transFact = TransformerFactory.newInstance();
			Transformer trans = transFact.newTransformer();
			DOMSource dom = new DOMSource(document);
			File tmpFile = new File(System.getProperty("user.home") + System.getProperty("file.separator") + "tmp.dom");
			StreamResult strOut = new StreamResult(tmpFile);
			
			//save the file
			trans.transform(dom, strOut);
			CrypterImpl en = new CrypterImpl(System.getProperty("user.home") + System.getProperty("file.separator") + "tmp.dom", fileName);
			en.doEncryption();
			//togliere il commento dalla riga sotto
			tmpFile.delete();
			//CrypterImpl de = new CrypterImpl(System.getProperty("user.home") + System.getProperty("file.separator")+"output.xml", fileName);
			//de.doDecryption();
		}
		catch (Exception exc) {
			throw new BackupDomoConfException(exc.toString());
		}
		
	}
	
	private Element addArg (String arg,String val){
		if(arg != null && val != null){
		Element eleArg = document.createElement(arg);
		eleArg.appendChild(document.createTextNode(val));
		return eleArg;
		}
		return null;
		
	}
	private Element createElement (String type,String name,int id,Element ele){
		Element elRet = document.createElement(type);
		elRet.setAttribute("Id", Integer.toString(id));
		elRet.appendChild(addArg("name", name));
		ele.appendChild(elRet);
		return elRet;
	}
}
