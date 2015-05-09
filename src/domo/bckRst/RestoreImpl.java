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
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import domo.devices.Sensor;
import domo.general.Flat;
import domo.general.FlatImpl;
import domo.general.Room;
import domo.general.RoomImpl;

	/**
	 * 
	 * @author Stefano Falzaresi Stefano.Falzaresi2@studio.unibo.it
	 */
public class RestoreImpl implements Restore {

		Flat fl;
		public boolean checkFilePresence() {
			return false;
		}

		@Override
		public Flat restoreNow(String fileName) throws RestoreDomoConfException{			
			try {
				CrypterImpl de = new CrypterImpl(System.getProperty("user.home") + System.getProperty("file.separator")+"tmp.dom", fileName);
				de.doDecryption();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			File tmpFile = new File(System.getProperty("user.home") + System.getProperty("file.separator") + "tmp.dom");
			
			DocumentBuilderFactory docBuildFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuild;
			try {
				docBuild = docBuildFactory.newDocumentBuilder();
				Document document = docBuild.parse(tmpFile);
				//whit this I enter in the first Child "domo"
				Element rootEle = document.getDocumentElement();
				//Use the createElement function to search elements in the xml and add everything to the environment
				//Start with Flat
				if(createElement("flat",rootEle,Flat.class)){
					System.out.println("Flat Created!");
				}
				else{
					System.out.println("Flat Not Created!");
				}
				if(createElement("room",rootEle,Room.class)){
					System.out.println("Room Created!");
				}
				else{
					System.out.println("Room Not Created!");
				}
				if(createElement("sensor",rootEle,Sensor.class)){
					System.out.println("Sensor Created!");
				}
				else{
					System.out.println("Sensor Not Created!");
				}
				/*
				if(flatList != null){
					for(int i=0;i<flatList.getLength();i++){
						Element el = (Element)flatList.item(i);
						System.out.println("il nome è "+el.getElementsByTagName("name").item(0).getFirstChild().getNodeValue());
						System.out.println("il tipo è "+el.getElementsByTagName("typology").item(0).getFirstChild().getNodeValue());
					}
				}
				*/
			} catch (Exception e) {
				throw new RestoreDomoConfException(e.toString());
			}

			
			
			tmpFile.delete();
			return fl;
		}
		
		private boolean createElement (String toAdd,Element ele,Class <?> cl){
			NodeList nList = ele.getElementsByTagName(toAdd);
			String eleType = cl.getName();
			
			String eleName;
			int eleId;
			
			if (nList != null){
				for(int i=0;i<nList.getLength();i++){
					Element el = (Element)nList.item(i);
					
					eleName = el.getElementsByTagName("name").item(0).getFirstChild().getNodeValue();
					eleId = Integer.parseInt(el.getAttribute("Id"));
					switch (eleType){
					case "domo.general.Flat":
						System.out.println("e' un appartamento");
						fl = new FlatImpl(eleName);
						break;
					case "domo.general.Room":
						System.out.println("e' una Stanza");
						fl.addRoom(new RoomImpl(eleId, eleName));
						break;
					case "domo.general.Sensor":
						System.out.println("e' un sensore");
						break;
					}
					return true;
				}
				
			}
			return false;
		}
	
	

}
