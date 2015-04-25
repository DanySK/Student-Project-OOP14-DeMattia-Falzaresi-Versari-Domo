package domo.bckRst;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
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
			
			//Creation of all variables I need for the flat level
			Element nameEle = document.createElement("name");
			Attr idAttr = document.createAttribute("id");
			
			//Set Attribute for flat
			idAttr.setValue("1");
			flat.setAttributeNode(idAttr);
			// shorten way
			// flat.setAttribute("id", "1");
			
			//add attribute "name" to Flat
			nameEle.appendChild(document.createTextNode(flatB.getName()));
			flat.appendChild(nameEle);
			
			//now I need to extract all rooms and backup it
			Set<Room> roomB = new HashSet<>();
			roomB = flatB.getRooms();
			for (Room room : roomB) {
				
				//creation of element room
				Element roomE = document.createElement("room");
				Element roomName = document.createElement("name");
				Attr roomID =document.createAttribute("id");
				flat.appendChild(roomE);
				roomID.setValue(Integer.toString(room.getId()));
				roomE.setAttributeNode(roomID);
				
				//room configuration
				roomName.appendChild(document.createTextNode(room.getName()));
				roomE.appendChild(roomName);
				
				//now I need to extract all sensors for this room and backup it
				Set<Sensor> sensorB = new HashSet<>();
				sensorB = room.getSensor();
				
				for(Sensor sensor : sensorB){
					//creation of element sensor and set his ID
					Element sensorE = document.createElement("sensor");
					roomE.appendChild(sensorE);
					Attr sensID =document.createAttribute("id");
					sensID.setValue(Integer.toString(sensor.getId()));
					sensorE.setAttributeNode(sensID);
					
					//now I put all the information of this sensor
					//Sensor Name
					if(sensor.getName()!=null){
						Element sensorName = document.createElement("name");
						sensorName.appendChild(document.createTextNode(sensor.getName()));
						sensorE.appendChild(sensorName);
					}
					
					//Sensor Image
					if(sensor.getImagePath()!=null){
						Element sensorImage = document.createElement("image");
						sensorImage.appendChild(document.createTextNode(sensor.getImagePath()));
						sensorE.appendChild(sensorImage);
					}
				
					//Sensor Location
					if(sensor.getLocation()!=null){
						Element sensorLocation = document.createElement("location");
						sensorLocation.appendChild(document.createTextNode(sensor.getLocation().toString()));
						sensorE.appendChild(sensorLocation);
					}
					
					//Sensor Size
					if(sensor.getSize()!=null){
						Element sensorSize = document.createElement("size");
						sensorSize.appendChild(document.createTextNode(sensor.getSize().toString()));
						sensorE.appendChild(sensorSize);
					}
					
					//Sensor Typology
					if(sensor.getType()!=null){
						Element sensorTypology = document.createElement("typology");
						sensorTypology.appendChild(document.createTextNode(sensor.getType().toString()));
						sensorE.appendChild(sensorTypology);
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
			CrypterImpl en = new CrypterImpl(tmpFile.getName(), fileName);
			en.doEncryption();
			//togliere il commento dalla riga sotto
			//tmpFile.delete();
			CrypterImpl de = new CrypterImpl("output.xml", fileName);
			de.doDecryption();
			return true; 
		}
		catch (Exception exc) {
			System.out.print(exc.toString());
			return false;
		}
		
	}
}
