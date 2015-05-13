package domo.bckrst;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;

import java.util.zip.ZipOutputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.util.zip.ZipEntry;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;

import domo.devices.Sensor;

import java.util.HashSet;

import domo.general.Flat;
import domo.general.Room;

import java.util.Set;
import java.io.File;

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
	 * @param file The name of the destination file
	 */
	public BackupImpl(final String file) {
		
		try {
			final DocumentBuilderFactory docBuildFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuild;
			docBuild = docBuildFactory.newDocumentBuilder();
			document = docBuild.newDocument();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		this.fileName = file;
		
	}
	/**
	 *  BackupNow method allow to start an automated backup of the application.
	 *  @param flatB The Flat object to be backupped
	 *  @throws BackupDomoConfException customized error messages
	 *  
	 */
	public void backupNow(final Flat flatB) throws BackupDomoConfException {
		final String tmpFileName = System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + "tmp.dom";
		if (flatB == null) {
			throw new BackupDomoConfException("No Flat has been submitted to the backup procedure");
		}
		try {
		
			//Creation of a new root element and add it to the document
			final Element rootEle = document.createElement("domo");
			document.appendChild(rootEle);
			
			//Creation of the flat element
			final Element flat = document.createElement("flat");
			flat.setAttribute("Id", "1");
			rootEle.appendChild(flat);
			
			//Creation of all variables I need for the flat level
			final Element nameEle = document.createElement("name");

			
			//add attribute "name" to Flat
			nameEle.appendChild(document.createTextNode(flatB.getName()));
			flat.appendChild(nameEle);
			
			//now I need to extract all rooms and backup it
			Set<Room> roomB = new HashSet<>();
			roomB = flatB.getRooms();
			for (final Room room : roomB) {
				final Element roomEl = createElement("room", room.getName(), room.getId(), flat);
				//now I need to extract all sensors for this room and backup it
				Set<Sensor> sensorB = new HashSet<>();
				sensorB = room.getSensor();
				for (final Sensor sensor : sensorB) {
					//creation of element sensor and all his parameters
					try {
					final Element sensorE = createElement("sensor", sensor.getName(), sensor.getId(), roomEl);
					sensorE.appendChild(addArg("image", sensor.getImagePath()));
					sensorE.appendChild(addArg("XPosition", Double.toString(sensor.getXPosition())));
					sensorE.appendChild(addArg("YPosition", Double.toString(sensor.getYPosition())));
					sensorE.appendChild(addArg("degree", Double.toString(sensor.getDegree())));
						try {
							sensorE.appendChild(addArg("typology", sensor.getType().toString()));
						} catch (NullPointerException e) {
							//catch this error but go on because is not a mandatory field
						}
					} catch (NullPointerException e) {
						throw new BackupDomoConfException("Error while loading sensor settings");
					}	
				}	
			}
			//take my document and place it in a DOMsource object using a temporary file in the temp folder
			final TransformerFactory transFact = TransformerFactory.newInstance();
			final Transformer trans = transFact.newTransformer();
			final DOMSource dom = new DOMSource(document);
			final File tmpFile = new File(tmpFileName);
			final StreamResult strOut = new StreamResult(tmpFile);
			
			//save the file
			trans.transform(dom, strOut);
			//start the encryption of all data
			final CrypterImpl en = new CrypterImpl(tmpFileName, System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + flatB.getName() + ".dom");
			en.doEncryption();
			//delete the temp file (not encrypted)
			tmpFile.delete();
			//call the zipping procedure
			zipEveryThing(flatB);
			
		} catch (Exception exc) {
			throw new BackupDomoConfException(exc.toString());
		}
		
	}
	
	/**
	 * This private method add the selected argument and value and give back the element created
	 * @param arg The argument to add (ex "name")
	 * @param val the value to set for this argument (ex "Danilo n#1')
	 * @return an Element object to add to a document object
	 */
	private Element addArg(final String arg, final String val) {
		if (arg != null && val != null) {
		final Element eleArg = document.createElement(arg);
		eleArg.appendChild(document.createTextNode(val));
		return eleArg;
		}
		return null;
		
	}
	
	/**
	 * This private method receive an element and some parameters and append to the element "ele"
	 * a child element with his id and his name
	 * @param type the type of element to append (ex flat)
	 * @param name the name of the child element to append
	 * @param id the id of the child element
	 * @param ele the parent element
	 * @return the child element
	 */
	private Element createElement(final String type, final String name, final int id, final Element ele) {
		final Element elRet = document.createElement(type);
		elRet.setAttribute("Id", Integer.toString(id));
		elRet.appendChild(addArg("name", name));
		ele.appendChild(elRet);
		return elRet;
	}
	
	/**
	 * Make a Zip file with the backup configuration an others resources (Like Images) 
	 * @param flatB the flat element to be backupped
	 * @throws BackupDomoConfException custom exceptions
	 */
	@SuppressWarnings("unused")
	private void zipEveryThing(final Flat flatB) throws BackupDomoConfException {
		final byte[] buf = new byte[1];
		try {
			final ZipOutputStream out = new ZipOutputStream(new FileOutputStream(this.fileName));
			final ArrayList<String> itemToAdd = new ArrayList<>();
			itemToAdd.add(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + flatB.getName() + ".dom");
			itemToAdd.add(flatB.getImagePath());
			for (final String sAdd : itemToAdd) {
				if (sAdd != null) {
					final FileInputStream in = new FileInputStream(sAdd);
					final File ft = new File(sAdd);
					out.putNextEntry(new ZipEntry(ft.getName()));
					int len;
		            while ((len = in.read(buf)) > 0) {
		            	out.write(buf);
		            }
		            out.closeEntry();
		            in.close();
		            if (ft.getName().equals(flatB.getName() + ".dom")) {
		            	ft.delete();
		            }
				} else {
					out.close();
					throw new BackupDomoConfException("One of the files is null, is not possible to proceed");
				}
			}
			out.close();
		} catch (Exception e) {
			throw new BackupDomoConfException("Error in zipping procedure" + e);
		}
	}
	 
}
