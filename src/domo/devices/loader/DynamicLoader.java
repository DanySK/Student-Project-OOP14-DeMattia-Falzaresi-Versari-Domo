package domo.devices.loader;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

/**
 * 
 * @author Marco Versari
 *
 * @param <E> module class interface.
 */
public interface DynamicLoader<E> {
	
	/**
	 * Set the Class module path.
	 * @param path of the module.
	 */
	void setModulePath(final String path);
	
	/**
	 * update the list of the module.
	 * @return the list of the module.
	 */
	Set<String> updateModuleList();
	
	/**
	 * Get the module list.
	 * @return the module list.
	 */
	Set<String> getModuleList();
	
	/**
	 * Create an instance of the class.	 
	 * @param module the name of the module.
	 * @return the instance of the selected module.
	 * @throws IllegalAccessException if the updateModuleList fail to check the module compatibility.
	 * @throws InvocationTargetException if something go wrong on the constructor of the module.
	 */
	E createClassInstance(final String module) throws IllegalAccessException, InvocationTargetException;
}
