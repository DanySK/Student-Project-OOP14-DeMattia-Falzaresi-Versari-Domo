package domo.devices.test;

/**
 * 
 */
import static org.junit.Assert.*;

import java.lang.reflect.Constructor;

import org.junit.Test;

/**
 * 
 * @author Marco Versari
 *
 */
public class TestReflection {

	/**
	 * @throws ClassNotFoundException 
	 * 
	 */
	@Test
	public void test() throws ClassNotFoundException {
		// Ottengo il riferimento alla Classe di interesse
		Class c = Class.forName("domo.devices.sensor.MotionSensor");

		// Acquisisco la lista dei costruttori
		Constructor listaCostruttori[] = c.getConstructors();
		System.out.println ("Numero Costruttori: " + listaCostruttori.length);

		// Stampo a video i dettagli di ciascun costruttore
		for (int i=0; i < listaCostruttori.length; i++)
		{
			String fullDesc = listaCostruttori[i].toString();
			System.out.println ("Costruttore n." + (i+1) + ": " + fullDesc);
			Class  tipiParam[] = listaCostruttori[i].getParameterTypes();
			if (tipiParam.length == 0)
			{	
				System.out.println ("Nessun Parametro!");
				continue;
			}
			System.out.println ("Parametri: ");
			for (int j=0; j < tipiParam.length; j++)
				System.out.println (tipiParam[j].getName());
		}
	}
}
