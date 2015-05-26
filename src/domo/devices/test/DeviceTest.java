package domo.devices.test;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.util.Set;

import org.junit.Test;

import domo.devices.Sensor;
import domo.devices.loader.DynamicLoader;
import domo.devices.loader.DynamicLoaderImpl;

/**
 * An automatic test class that test the module loader.
 * @author Marco Versari
 *
 */
public class DeviceTest {
	
	private static final String CLASS_PATH = "classi";

	/**
	 * 
	 */
	@Test
	public void test() {				
		System.out.println(CLASS_PATH);
		final DynamicLoader<Sensor> instance = new DynamicLoaderImpl<Sensor>("domo.devices", "Sensor", "AbstractSensor");			
		
		instance.setModulePath(CLASS_PATH);
		final Set<String> res = instance.updateModuleList();
		
		assertSame(res.size(), 2);
		
		res.forEach(x -> {
			try {
				System.out.println(instance.createClassInstance(x).getId());
				System.out.println(instance.createClassInstance(x).getName());
			} catch (Exception e) {
				fail(e.toString());
			}
		});
	}

}
