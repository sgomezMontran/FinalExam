package com.montran.demo.persistence;

import java.io.IOException;

import com.montran.demo.exception.PersistenceException;



/**
 * 
 * Interface that contains the save and load methods.
 * 
 * @author Santiago Gomez
 * @param <T>
 */
public interface Repository<T> {

	/**
	 * Method that save the data into the persistence.
	 * 
	 * @param obj
	 * @param fileName
	 * @throws IOException
	 */
	void save(T obj, String fileName) throws IOException;

	/**
	 * Method that load the data of the persistence.
	 * 
	 * @param classType
	 * @param fileName
	 * @return
	 * @throws PersistenceException
	 */
	T load(Class<?> classType, String fileName) throws PersistenceException;

}
