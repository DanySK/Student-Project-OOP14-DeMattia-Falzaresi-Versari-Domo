package domo.bckrst;

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
			final String fileToRestore = unzipEveryThing(fileName);
			final String tmpFileName = System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + "tmp.dom";
			//decrypt the configuration file in a temporary folder
			try {
				final CrypterImpl de = new CrypterImpl(tmpFileName, fileToRestore);
				de.doDecryption();
			} catch (Exception e) {
				throw new RestoreDomoConfException("Unable to decrypt the configuration file " + e);
			}
			final File tmpFile = new File(tmpFileName);
			final DocumentBuilderFactory docBuildFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuild;
			try {
				//start the parsing procedure
				docBuild = docBuildFactory.newDocumentBuilder();
				final Document document = docBuild.parse(tmpFile);
				//whit this I enter in the first Child "domo"
				final Element rootEle = document.getDocumentElement();
				//Use the createElement function to search elements in the xml and add everything to the environment
				//Start with Flat
				createElement("flat", rootEle, Flat.class);
				//and now with rooms and all related sensors
				createElement("room", rootEle, Room.class);
				//set the flat image (the folder may not be the original folder and throw an exception if something is not correct
				if (this.flatImageName == null) {
					throw new RestoreDomoConfException("Flat Image has not been correctly restored");
				} else {
					this.fl.setImagePath(flatImageName);
				}
			} catch (Exception e) {
				throw new RestoreDomoConfException("Error in the element creation procedure " + e);
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
			final NodeList nList = ele.getElementsByTagName(toAdd);
			final String eleType = cl.getName();
			String eleName;
			int eleId;
			if (nList == null) {
				throw new RestoreDomoConfException(toAdd + " entity not imported correctly");
			} else {
				for (int i = 0; i < nList.getLength(); i++) {
					//go over all the elements and select attributes I need 
					final Element el = (Element) nList.item(i);
					eleName = el.getElementsByTagName("name").item(0).getFirstChild().getNodeValue();
					eleId = Integer.parseInt(el.getAttribute("Id"));
					switch(eleType) {
					case "domo.general.Flat":
						fl = new FlatImpl(eleName);
						break;
					case "domo.general.Room":
						//if the element I need to restore is a room I need to go over all its children
						final int roomID = fl.addRoom(eleName);
						Node sensNode = el.getFirstChild();
						while (sensNode.getNextSibling() != null) {
							sensNode = sensNode.getNextSibling();
							if (sensNode.getNodeType() == Node.ELEMENT_NODE) {
								final Element sensEle = (Element) sensNode;
								//I have to get all the sensor attributes and add it to the temporary sensor
								final String posX = sensEle.getElementsByTagName("XPosition").item(0).getFirstChild().getNodeValue();
								final String posY = sensEle.getElementsByTagName("YPosition").item(0).getFirstChild().getNodeValue();
								final String sensName = sensEle.getElementsByTagName("name").item(0).getFirstChild().getNodeValue();
								final String degree = sensEle.getElementsByTagName("degree").item(0).getFirstChild().getNodeValue();
								//sensor is created by the dinamic loader class
								final DynamicLoader<Sensor> listaClassiSensori = new DynamicLoaderImpl<Sensor>("domo.devices", "Sensor", "AbstractSensor");			
								listaClassiSensori.setModulePath("classi");
								final Set<String> resLoader = listaClassiSensori.updateModuleList();
								for (final Room r : fl.getRooms()) {
									if (r.getId() == roomID) {
										for (final String x : resLoader) {
											try {
												if (listaClassiSensori.createClassInstance(x).getName().equals(sensName)) {
													final Sensor tmpS = listaClassiSensori.createClassInstance(x);
													tmpS.setLocation(Double.parseDouble(posX), Double.parseDouble(posY));
													tmpS.setDegree(Double.parseDouble(degree));
													fl.addSensorToRoom(r, tmpS);
												}
												
											} catch (Exception e) {
												throw new RestoreDomoConfException("Error in the adding sensor procedure " + e);
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
			}
		}
		/**
		 * This private method take a given file and restore all the thing needed by the
		 * project (configuration file and other resources like photos)
		 * @param file the file (created by our backup procedure) where all the things are saved
		 * @return a string with the name and path of the temporary file with the configuration
		 * @throws RestoreDomoConfException custom exceptions made for this method
		 */
		
		private String unzipEveryThing(final String file) throws RestoreDomoConfException {
			if (file == null) {
				//custom exception for file name verification
				throw new RestoreDomoConfException("Restore File Cannot Be Null");
			}
			try {
				final File fL = new File(file);
				final ZipInputStream zIn = new ZipInputStream(new FileInputStream(file));
				final File dir = new File(fL.getParent() + System.getProperty("file.separator") + "Domo" + System.getProperty("file.separator"));
				if (!dir.exists() && !dir.mkdir()) {
					zIn.close();
					throw new RestoreDomoConfException("Unable to Create Restore Folder");
				}
				String fileName = null;
				final byte[] bos = new byte[1];
				ZipEntry zEntry = zIn.getNextEntry();
				while (zEntry != null) {
					final String foName = dir + System.getProperty("file.separator") + zEntry.getName();
					final FileOutputStream fos = new FileOutputStream(foName);
					
					if (zEntry.getName().contains(".dom")) {
						fileName = foName;
					}
					int len = zIn.read(bos);
			        while (len > 0) {
			            fos.write(bos);
			            len = zIn.read(bos);
			        }
			        fos.flush();
			        fos.close();
			        final String mimetype = new MimetypesFileTypeMap().getContentType(new File(foName));
					if (mimetype.contains("image")) {
						this.flatImageName = foName;
					}
					zEntry = zIn.getNextEntry();
				}
				zIn.close();
				return fileName;
			} catch (Exception e) {
				throw new RestoreDomoConfException("Error in the unzipping procedure " + e);
			}	
		}
}
