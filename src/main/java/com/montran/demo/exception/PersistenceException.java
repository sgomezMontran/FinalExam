package com.montran.demo.exception;

/**
 * 
 * Custom Exception class that is throwed into the manager classes.
 * 
 * @author Santiago Gomez
 */
public class PersistenceException extends Exception {

	private static final long serialVersionUID = -5933518363905581377L;

	public PersistenceException(String message, Throwable cause) {
		super(message, cause);
	}

	public PersistenceException(String message) {
		super(message);
	}

}
