package domo.general;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import domo.GUI.*;
import domo.bckRst.*;
import domo.devices.Sensor;
import domo.devices.SensorTypology;
import domo.devices.loader.DynamicLoader;
import domo.devices.loader.DynamicLoaderImpl;
import domo.devices.sensor.MotionSensor;

/**
 * 
 * @author Marco Versari
 *
 */
public class MainClass {

	/**
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		System.out.println("Welcome!");
		Flat fl;
		//usato per OSX (barra menu a schermo)
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		
		final String classPath = "classi";
		System.out.println(classPath);
		final DynamicLoader<Sensor> listaClassiSensori = new DynamicLoaderImpl<Sensor>("domo.devices", "Sensor", "AbstractSensor");			
		listaClassiSensori.setModulePath(classPath);
		final Set<String> resLoader = listaClassiSensori.updateModuleList();
		
		ArrayList <Map <String, String>> sensorTypeList = new ArrayList<>();
		resLoader.forEach(x -> {
			try {
				HashMap <String, String> t = new HashMap<>();
				t.put("name", listaClassiSensori.createClassInstance(x).getName());
				t.put("image", listaClassiSensori.createClassInstance(x).getImagePath());
				t.put("type", listaClassiSensori.createClassInstance(x).getType().toString());
				t.put("rif", x);
				sensorTypeList.add(t);
				
			} catch (Exception e) {
				fail(e.toString());
			}
		});
		
		GUIFlatImpl t = new GUIFlatImpl("Domo", sensorTypeList);
		
		new TheController(t);
		
		

	}


}
