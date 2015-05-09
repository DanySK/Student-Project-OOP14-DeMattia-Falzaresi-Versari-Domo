package domo.bckRst;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
			CrypterImpl en = new CrypterImpl(System.getProperty("user.home") + System.getProperty("file.separator") + "tmp.dom", System.getProperty("user.home") + System.getProperty("file.separator") + flatB.getName() + ".dom");
			en.doEncryption();
			tmpFile.delete();
			ZipEveryThing(flatB);
			
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
	
	/**
	 * Make a Zip with Backup an others resources (Like Images) 
	 */
	private void ZipEveryThing(Flat flatB) throws BackupDomoConfException {
		byte[] buf = new byte[1024];
		try {
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(this.fileName));
			ArrayList<String> itemToAdd = new ArrayList<>();
			itemToAdd.add(System.getProperty("user.home") + System.getProperty("file.separator") + flatB.getName() + ".dom");
			itemToAdd.add(flatB.getImagePath());
			for(String s : itemToAdd){
				if(s != null){
					System.out.println("The file is "+s);
					FileInputStream in = new FileInputStream(s);
					out.putNextEntry(new ZipEntry(s));
					int len;
		            while ((len = in.read(buf)) > 0) {
		                out.write(buf, 0, len);
		            }
		            out.closeEntry();
		            in.close();
				}
				else {
					throw new BackupDomoConfException("One of the files is null, is not possible to proceed");
				}
			}
			out.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	 
}
