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
				
				//creation of element room
				Element roomE = document.createElement("room");
				roomE.setAttribute("Id", Integer.toString(room.getId()));
				Element roomName = document.createElement("name");
				Text roomNameTxt = document.createTextNode(room.getName());
				roomName.appendChild(roomNameTxt);
				flat.appendChild(roomE);
				
				//room configuration
				//roomName.appendChild(document.createTextNode(room.getName()));
				//roomE.appendChild(roomName);
				
				//now I need to extract all sensors for this room and backup it
				Set<Sensor> sensorB = new HashSet<>();
				sensorB = room.getSensor();
				
				for(Sensor sensor : sensorB){
					//creation of element sensor and set his ID
					Element sensorE = document.createElement("sensor");
					roomE.appendChild(sensorE);
					sensorE.setAttribute("Id",Integer.toString(sensor.getId()));
					
					//now I put all the information of this sensor
					//Sensor Name
					if(sensor.getName()!=null){
						Element sensorName = document.createElement("name");
						Text sensNameText = document.createTextNode(sensor.getName());
						sensorName.appendChild(sensNameText);
						sensorE.appendChild(sensorName);
					}
					
					//Sensor Image
					if(sensor.getImagePath()!=null){
						Element sensorImage = document.createElement("image");
						Text sensImageText =document.createTextNode(sensor.getImagePath());
						sensorImage.appendChild(sensImageText);
						sensorE.appendChild(sensorImage);
					}
				
					//Sensor Location
					if(sensor.getLocation()!=null){
						Element sensorLocation = document.createElement("location");
						Text sensLocText=document.createTextNode(sensor.getLocation().toString());
						sensorLocation.appendChild(sensLocText);
						sensorE.appendChild(sensorLocation);
					}
					
					//Sensor Size
					if(sensor.getSize()!=null){
						Element sensorSize = document.createElement("size");
						Text sensSizeText = document.createTextNode(sensor.getSize().toString());
						sensorSize.appendChild(sensSizeText);
						sensorE.appendChild(sensorSize);
					}
					
					//Sensor Typology
					if(sensor.getType()!=null){
						Element sensorTypology = document.createElement("typology");
						Text sensTypeText = document.createTextNode(sensor.getType().toString());
						sensorTypology.appendChild(sensTypeText);
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
