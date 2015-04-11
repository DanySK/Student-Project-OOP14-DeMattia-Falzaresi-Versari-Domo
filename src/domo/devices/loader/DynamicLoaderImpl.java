package domo.devices.loader;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author Marco Versari
 * @param <E> The module instance that want to retrieve.
 *
 */
public class DynamicLoaderImpl<E> implements DynamicLoader<E> {
	
	private final String interfacePath;
	private final String interfaceName;	
	
	private File modulePath;
	private final Map<String, Class<?>> moduleList;

	/**
	 * Create a module loader instance.
	 * @param pInterfacePath the interface path.
	 * @param pInterfaceName the interface name.
	 */
	public DynamicLoaderImpl(final String pInterfacePath, final String pInterfaceName) { 
		moduleList = new HashMap<>();
		this.interfacePath = pInterfacePath;
		this.interfaceName = pInterfaceName;
	}

	@Override
	public Set<String> getModuleList() {
		return new HashSet<>(moduleList.keySet());
	}

	@SuppressWarnings("unchecked")
	@Override
	public E createClassInstance(final String module) throws IllegalAccessException, InvocationTargetException {
		try {
			return (E) moduleList.get(module).getConstructors()[0].newInstance();			
		} catch (InstantiationException | IllegalArgumentException
				| SecurityException e) {
			System.out.println("createClassInstance: " + e);
			return null;
		}
	}
	
	private void listAllModule(final File directory, final List<String> files) {
	    final File[] fList = directory.listFiles();
	    for (final File file : fList) {
	        if (file.isDirectory()) {
	        	listAllModule(file, files);	           
	        } else if (file.getName().endsWith(".class")) {
	        	files.add(file.getPath().substring(modulePath.getPath().length() + 1, file.getPath().length() - 6).replace(File.separator, "."));
	        }
	    }
	}
	
	@Override
	public Set<String> updateModuleList() {
		if (modulePath != null && modulePath.exists()) {						
			final List<String> a = new ArrayList<>();			
			listAllModule(modulePath, a);			
			try {					
				final ClassLoader classLoader = new URLClassLoader(new URL[]{modulePath.toURI().toURL()});						
				for (final String module : a) {					
					try {						
						final Class<?> c2 = classLoader.loadClass(module);									
						for (final Class<?> interfaces : c2.getInterfaces()) {							
							if (interfaces.getName().endsWith(interfaceName)) {										
								moduleList.put(c2.getName(), c2);								
								if (Array.getLength(c2.getConstructors()) == 1) {
									for (final Method met : Class.forName(interfacePath + "." + interfaceName).getMethods()) {
										try {								
											interfaces.getDeclaredMethod(met.getName(), met.getParameterTypes());											
										} catch (NoSuchMethodException e) {
											moduleList.remove(c2.getName());
											break;
										} catch (SecurityException e) {
											System.out.println("Error: " + e.getMessage());
										}	
									}
								}
							}
						}
							
					} catch (ClassNotFoundException e) {
						System.out.println("updateModuleList -> listAllModule: " + e);
					}						
				}
				
			} catch (MalformedURLException e) {
				System.out.println("updateModuleList -> URLClassLoader: " + e);
			}
		}					
		return new HashSet<>(moduleList.keySet());
	}

	@Override
	public void setModulePath(final String path) {
		modulePath = new File(path);
	}
}
