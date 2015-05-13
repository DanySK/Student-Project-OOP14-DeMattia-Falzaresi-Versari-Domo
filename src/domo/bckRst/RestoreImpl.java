package domo.bckRst;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.activation.MimetypesFileTypeMap;

import domo.devices.loader.DynamicLoaderImpl;

import javax.xml.parsers.DocumentBuilder;

import domo.devices.loader.DynamicLoader;

import java.util.zip.ZipInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.util.zip.ZipEntry;

import domo.general.FlatImpl;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import domo.devices.Sensor;
import domo.general.Room;
import domo.general.Flat;

import org.w3c.dom.Node;

import java.util.Set;
import java.io.File;

	/**
	 * The Restore impl class implements Restore and manage all the things related to the
	 * restore procedure.
	 * @author Stefano Falzaresi Stefano.Falzaresi2@studio.unibo.it
	 */
public class RestoreImpl implements Restore {

		private Flat fl;
		private String flatImageName;

		@Override
		public Flat restoreNow(final String fileName) throws RestoreDomoConfException {
			//open the file and start with the environment creation
			String fileToRestore = unzipEveryThing(fileName);
			//decrypt the configuration file in a temporary folder
			try {
				CrypterImpl de = new CrypterImpl(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + "tmp.dom", fileToRestore);
				de.doDecryption();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			File tmpFile = new File(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + "tmp.dom");
			DocumentBuilderFactory docBuildFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuild;
			try {
				//start the parsing procedure
				docBuild = docBuildFactory.newDocumentBuilder();
				Document document = docBuild.parse(tmpFile);
				//whit this I enter in the first Child "domo"
				Element rootEle = document.getDocumentElement();
				//Use the createElement function to search elements in the xml and add everything to the environment
				//Start with Flat
				createElement("flat", rootEle, Flat.class);
				//and now with rooms and all related sensors
				createElement("room", rootEle, Room.class);
				//set the flat image (the folder may not be the original folder and throw an exception if something is not correct
				if (this.flatImageName != null) {
					this.fl.setImagePath(flatImageName);
				} else {
					throw new RestoreDomoConfException("Flat Image has not been correctly restored");
				}
			} catch (Exception e) {
				throw new RestoreDomoConfException(e.toString());
			}
			tmpFile.delete();
			//return the element
			return this.fl;
		}
		/**
		 *  This procedure create the given element in the environment 
		 * @param toAdd the type of element to add (ex flat)
		 * @param ele the xml element where the element to add can be found
		 * @param cl the class of the element is needed
		 * @throws RestoreDomoConfException custom exception related to the procedure
		 */
		@SuppressWarnings("unused")
		private void createElement(final String toAdd, final Element ele, final Class < ? > cl) throws RestoreDomoConfException {
			NodeList nList = ele.getElementsByTagName(toAdd);
			String eleType = cl.getName();
			String eleName;
			int eleId;
			if (nList != null) {
				for (int i = 0; i < nList.getLength(); i++) {
					//go over all the elements and select attributes I need
					Element el = (Element) nList.item(i);
					eleName = el.getElementsByTagName("name").item(0).getFirstChild().getNodeValue();
					eleId = Integer.parseInt(el.getAttribute("Id"));
					switch(eleType) {
					case "domo.general.Flat":
						fl = new FlatImpl(eleName);
						break;
					case "domo.general.Room":
						//if the element I need to restore is a room I need to go over all its children
						int roomID = fl.addRoom(eleName);
						Node sensNode = el.getFirstChild();
						while (sensNode.getNextSibling() != null) {
							sensNode = sensNode.getNextSibling();
							if (sensNode.getNodeType() == Node.ELEMENT_NODE) {
								Element sensEle = (Element) sensNode;
								//I have to get all the sensor attributes and add it to the temporary sensor
								String posX = sensEle.getElementsByTagName("XPosition").item(0).getFirstChild().getNodeValue();
								String posY = sensEle.getElementsByTagName("YPosition").item(0).getFirstChild().getNodeValue();
								String sensName = sensEle.getElementsByTagName("name").item(0).getFirstChild().getNodeValue();
								String degree = sensEle.getElementsByTagName("degree").item(0).getFirstChild().getNodeValue();
								//sensor is created by the dinamic loader class 
								final String classPath = "classi";
								final DynamicLoader<Sensor> listaClassiSensori = new DynamicLoaderImpl<Sensor>("domo.devices", "Sensor", "AbstractSensor");			
								listaClassiSensori.setModulePath(classPath);
								final Set<String> resLoader = listaClassiSensori.updateModuleList();
								for (Room r : fl.getRooms()) {
									if (r.getId() == roomID) {
										for (String x : resLoader) {
											try {
												if (listaClassiSensori.createClassInstance(x).getName().equals(sensName)) {
													Sensor tmpS = listaClassiSensori.createClassInstance(x);
													tmpS.setLocation(Double.parseDouble(posX), Double.parseDouble(posY));
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
					default:
						break;
					}
				}
				
			} else {
				throw new RestoreDomoConfException(toAdd + " entity not imported correctly");
			}
		}
		/**
		 * This private method take a given file and restore all the thing needed by the
		 * project (configuration file and other resources like photos)
		 * @param file the file (created by our backup procedure) where all the things are saved
		 * @return a string with the name and path of the temporary file with the configuration
		 * @throws RestoreDomoConfException custom exceptions made for this method
		 */
		@SuppressWarnings("unused")
		private String unzipEveryThing(final String file) throws RestoreDomoConfException {
			FileInputStream fIn;
			String fileName = null;
			if (file == null) {
				//custom exception for file name verification
				throw new RestoreDomoConfException("Restore File Cannot Be Null");
			}
			try {
				File fL = new File(file);
				ZipInputStream zIn = new ZipInputStream(new FileInputStream(file));
				File dir = new File(fL.getParent() + System.getProperty("file.separator") + "Domo" + System.getProperty("file.separator"));
				if (!dir.exists()) {
						if (!dir.mkdir()) {
							//throw an exception if I'm unable to create the requested folder
							zIn.close();
							throw new RestoreDomoConfException("Unable to Create Restore Folder");
						}
				}
				byte[] bos = new byte[1];
				ZipEntry zEntry;
				while ((zEntry = zIn.getNextEntry()) != null) {
					FileOutputStream fos = new FileOutputStream(dir + System.getProperty("file.separator") + zEntry.getName());
					
					if (zEntry.getName().contains(".dom")) {
						fileName = dir + System.getProperty("file.separator") + zEntry.getName();
					}
					int len;
			        while ((len = zIn.read(bos)) > 0) {
			            fos.write(bos);
			        }
			        fos.flush();
			        fos.close();
			        String mimetype = new MimetypesFileTypeMap().getContentType(new File(dir + System.getProperty("file.separator") + zEntry.getName()));
					if (mimetype.contains("image")) {
						this.flatImageName = dir + System.getProperty("file.separator") + zEntry.getName();
					}
				}
				zIn.close();
			} catch (Exception e) {
				throw new RestoreDomoConfException(e.toString());
			}
			return fileName;
		}
}
