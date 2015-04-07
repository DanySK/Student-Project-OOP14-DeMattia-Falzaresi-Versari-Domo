package domo.devices.test;

/**
 * 
 */
import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

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

		Method[] m = c.getDeclaredMethods();
		for(int i = 0; i < m.length; i++) {
		   System.out.println("method = " + m[i].toString());
		}
	}
}
