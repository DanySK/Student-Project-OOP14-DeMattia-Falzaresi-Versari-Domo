package domo.bckRst;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.activation.MimetypesFileTypeMap;
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
import domo.devices.loader.DynamicLoader;
import domo.devices.loader.DynamicLoaderImpl;
import domo.devices.util.pair.Pair;
import domo.general.Flat;
import domo.general.FlatImpl;
import domo.general.Room;
import domo.general.RoomImpl;

	/**
	 * 
	 * @author Stefano Falzaresi Stefano.Falzaresi2@studio.unibo.it
	 */
public class RestoreImpl implements Restore {

		private Flat fl;
		private String flatImageName;
		public boolean checkFilePresence() {
			return false;
		}

		@Override
		public Flat restoreNow(String fileName) throws RestoreDomoConfException{
			String fileToRestore = UnzipEveryThing(fileName);
			try {
				CrypterImpl de = new CrypterImpl(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator")+"tmp.dom", fileToRestore);
				de.doDecryption();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			File tmpFile = new File(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + "tmp.dom");
			
			DocumentBuilderFactory docBuildFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuild;
			try {
				docBuild = docBuildFactory.newDocumentBuilder();
				Document document = docBuild.parse(tmpFile);
				//whit this I enter in the first Child "domo"
				Element rootEle = document.getDocumentElement();
				//Use the createElement function to search elements in the xml and add everything to the environment
				//Start with Flat
				createElement("flat",rootEle,Flat.class);
				createElement("room",rootEle,Room.class);
				if(this.flatImageName != null){
					this.fl.setImagePath(flatImageName);
				}
				else{
					throw new RestoreDomoConfException("Flat Image has not been correctly restored");
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
			System.out.println("Restore Completed");
			return fl;
		}
		
		private void createElement (String toAdd,Element ele,Class <?> cl) throws RestoreDomoConfException{
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
						fl = new FlatImpl(eleName);
						break;
					case "domo.general.Room":
						int roomID = fl.addRoom(eleName);
						Node sensNode = el.getFirstChild();
						while (sensNode.getNextSibling() != null){
							sensNode = sensNode.getNextSibling();
							if (sensNode.getNodeType() == Node.ELEMENT_NODE) {
								Element sensEle = (Element) sensNode;
								String posX = sensEle.getElementsByTagName("XPosition").item(0).getFirstChild().getNodeValue();
								String posY = sensEle.getElementsByTagName("YPosition").item(0).getFirstChild().getNodeValue();
								String SensName = sensEle.getElementsByTagName("name").item(0).getFirstChild().getNodeValue();
								String degree = sensEle.getElementsByTagName("degree").item(0).getFirstChild().getNodeValue();
								
								final String classPath = "classi";
								final DynamicLoader<Sensor> listaClassiSensori = new DynamicLoaderImpl<Sensor>("domo.devices", "Sensor", "AbstractSensor");			
								listaClassiSensori.setModulePath(classPath);
								final Set<String> resLoader = listaClassiSensori.updateModuleList();
								for (Room r : fl.getRooms()) {
									if (r.getId() == roomID){
										for (String x : resLoader) {
											try {
												if(listaClassiSensori.createClassInstance(x).getName().equals(SensName)) {
													Sensor tmpS = listaClassiSensori.createClassInstance(x);
													tmpS.setLocation(Double.parseDouble(posX),Double.parseDouble(posY));
													tmpS.setDegree(Double.parseDouble(degree));
													fl.addSensorToRoom(r, tmpS);
												}
												
											} catch (Exception e) {
												throw new RestoreDomoConfException("Error in the adding sensor procedure " + e.toString());
											}
										}
									}
								}
							}
						}
						break;
					}
				}
				
			}else{
				throw new RestoreDomoConfException(toAdd +" entity not imported correctly");
			}
		}
	
		private String UnzipEveryThing(String file) throws RestoreDomoConfException{
			FileInputStream fIn;
			String fileName=null;
			
			if(file == null){
				throw new RestoreDomoConfException("Restore File Cannot Be Null");
			}
			try {
				File fl = new File(file);
				ZipInputStream zIn = new ZipInputStream(new FileInputStream(file));
				File dir = new File(fl.getParent()+System.getProperty("file.separator")+"Domo"+System.getProperty("file.separator"));
				if(!dir.exists()){
						if(!dir.mkdir()){
							throw new RestoreDomoConfException("Unable to Create Restore Folder");
						}
				}
				byte[] bos = new byte[1];;
				ZipEntry zEntry;
				
				while ((zEntry = zIn.getNextEntry()) != null){
					FileOutputStream fos = new FileOutputStream(dir+System.getProperty("file.separator")+zEntry.getName());
					
					if(zEntry.getName().contains(".dom")){
						fileName = dir+System.getProperty("file.separator")+zEntry.getName();
					}
					int len;
			        while ((len=zIn.read(bos))>0)
			        {
			            fos.write(bos);
			        }
			        fos.flush();
			        fos.close();
			        
			        String mimetype= new MimetypesFileTypeMap().getContentType(new File(dir+System.getProperty("file.separator")+zEntry.getName()));
					if (mimetype.contains("image")){
						this.flatImageName= dir+System.getProperty("file.separator")+zEntry.getName();
					}
				}
			} catch (Exception e) {
				throw new RestoreDomoConfException(e.toString());
			}
			return fileName;
		}
	

}
