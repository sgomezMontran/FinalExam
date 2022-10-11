package com.montran.demo.persistence;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.montran.demo.exception.PersistenceException;
import com.montran.demo.utils.PropertiesUtil;



/**
 * Class that generates Objects to use to persistence other objects
 * 
 * @author Santiago Gomez
 *
 */
public class PersistenceFactory {
	public static final PersistenceFactory instance = new PersistenceFactory();
	private static final Properties CONFIG = PropertiesUtil.getProperties("config.properties");
	private Logger logger = Logger.getLogger(PersistenceFactory.class.getName());

	// singleton pattern
	private PersistenceFactory() {
	}

	public static PersistenceFactory getInstance() {
		return instance;
	}

	/**
	 * Get the comparator by the name and params.
	 *
	 * @param Comparator name, the level of the url if it is necessary.
	 * @return The comparator object.
	 */
	public Repository<?> getPersistence() throws PersistenceException {
		Class<?> repositoryClass = null;
		Constructor<?> repositoryConstructor = null;
		Repository<?> repsitory = null;
		try {

			repositoryClass = Class.forName(
					CONFIG.getProperty("persist.classname", "com.montran.demo.persistence.RepositoryImplXml"));
			repositoryConstructor = getConstructor(repositoryClass);
			repsitory = (Repository<?>) repositoryConstructor.newInstance();
			return repsitory;
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException
				| SecurityException e) {
			logger.log(Level.INFO, "Error during comparator creation: " + e.getMessage());
			throw new PersistenceException(e.getMessage(), e);
		} catch (NullPointerException e) {
			logger.log(Level.FATAL, "Error during persistence creation with persistence Class: " + e.getMessage()
					+ ", wrong name of comparator");
			throw new PersistenceException(e.getMessage(), e);
		} catch (IllegalArgumentException e) {
			throw new PersistenceException(e.getMessage(), e);
		}
	}

	/**
	 * Get the constructor of the sorter class.
	 *
	 * @param A class, in this case is a sorter class.
	 * @return The constructor of the class.
	 */
	private Constructor<?> getConstructor(Class<?> sorterClass) {
		Constructor<?>[] constructor = sorterClass.getDeclaredConstructors();
		return constructor[0];
	}
}