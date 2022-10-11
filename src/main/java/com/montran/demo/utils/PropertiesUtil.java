package com.montran.demo.utils;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.yaml.snakeyaml.Yaml;


/**
 *
 * Class that returns a Properties object from a properties file.
 * 
 * @author Santiago Gomez
 */
public class PropertiesUtil {

	private static final String RESOURCE_PATH = "src/main/resources";
	private static final String PROP_EXTENSION = ".properties";
	private static final String YML_EXTENSION = ".yml";
	private static Logger log = Logger.getLogger(PropertiesUtil.class.getName());

	/**
	 * Method that read a properties file and create a Properties object.
	 * 
	 * @param fileName
	 * @return properties Properties object with a configuration
	 */
	public static Properties getProperties(String fileName) {
		Path file = Path.of(RESOURCE_PATH);
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(file.resolve(getValidatedFileName(fileName).concat(PROP_EXTENSION)).toFile()));
			return properties;

		} catch (IOException e) {
			log.warn("Error during load file " + file.toString() + "\\" + fileName + "\ntrying to load file with extension .yml");
			return getYmlProperties(getValidatedFileName(fileName).concat(YML_EXTENSION));
		}
	}

	/**
	 * Method that validate fileName string.
	 * 
	 * @param fileName
	 * @return a validated File name
	 */
	private static String getValidatedFileName(String fileName) {
		if (fileName.endsWith(PROP_EXTENSION) || fileName.endsWith(YML_EXTENSION)) {
			int dotIndex = fileName.lastIndexOf(".");
			return fileName.substring(0, dotIndex);
		}
		return fileName;
	}

	/**
	 * Method that read a yml file and create a Properties object.
	 * 
	 * @param fileName
	 * @return properties Properties object with a configuration
	 */
	public static Properties getYmlProperties(String fileName) {
		Path file = Path.of(RESOURCE_PATH);

		Properties properties = new Properties();
		try {
			InputStream inputStream = new FileInputStream(file.resolve(fileName).toFile());
			// library to read values from a input Stream to Map format
			Yaml yaml = new Yaml();
			Map<String, Object> data = yaml.load(inputStream);
			if (data == null) {
				return properties;
			}
			data.forEach((k, v) -> properties.put(k, v));

			return properties;

		} catch (IOException e) {
			log.log(Level.ERROR, "Error during load file " + file.toString() + "\\" + fileName, e);
		}
		return null;

	}

}
