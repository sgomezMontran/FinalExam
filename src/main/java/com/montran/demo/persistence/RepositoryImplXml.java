package com.montran.demo.persistence;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Properties;

import java.util.stream.Collectors;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.montran.demo.exception.PersistenceException;
import com.montran.demo.utils.PropertiesUtil;


/**
 * 
 * Implementation of the Repository Interface that its communicating with the
 * persistence XML.
 * 
 * @param <T>
 * @author Santiago Gomez
 */
public class RepositoryImplXml<T> implements Repository<T> {

	private static Logger log = Logger.getLogger(RepositoryImplXml.class.getName());

	private static final Properties CONFIG = PropertiesUtil.getProperties("config.properties");
	private static final String EXTENSION =  ".xml";
	private final Path dataFolder = Path.of(CONFIG.getProperty("repository.data.folder", "src/main/resources"));

	/**
	 * Override Method that save the data into the persistence.
	 * 
	 * @param obj
	 * @param fileName
	 * @throws IOException
	 */
	@Override
	public void save(T obj, String fileName) throws IOException {

		Path file = dataFolder.resolve(fileName + getValidatedExtension(EXTENSION));

		try (PrintWriter out = new PrintWriter(new OutputStreamWriter(
				Files.newOutputStream(file, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE),
				Charset.forName("UTF-8")))) {
			String xmlData = objectToXML(obj);
			out.append(xmlData + System.lineSeparator());

		}

		log.log(Level.INFO, String.format("File %s saved successfully", file.toAbsolutePath()));
	}

	/**
	 * Method that convert an object to XML format.
	 * 
	 * @param obj
	 * @return String
	 * @throws JsonProcessingException
	 */
	private String objectToXML(T obj) throws JsonProcessingException {
		XmlMapper xmlMapper = new XmlMapper();
		return xmlMapper.writeValueAsString(obj);
	}

	/**
	 * Method that convert from XML to an Object T.
	 * 
	 * @param data
	 * @param class1
	 * @return Object T
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	@SuppressWarnings("unchecked")
	private T xmlToObject(String data, Class<?> class1) throws JsonMappingException, JsonProcessingException {
		if (data == null || data.isBlank()) {
			return null;
		}
		XmlMapper xmlMapper = new XmlMapper();
		return (T) xmlMapper.readValue(data, class1);
	}

	private String getValidatedExtension(String extension) {
		return extension.startsWith(".") ? extension : ".".concat(extension);
	}

	/**
	 * Override Method that load the data of the persistence.
	 * 
	 * @param classType
	 * @param fileName
	 * @return
	 * @throws PersistenceException
	 */
	@Override
	public T load(Class<?> classType, String fileName) throws PersistenceException {
		Path file = dataFolder.resolve(fileName + getValidatedExtension(EXTENSION));
		//System.out.println("ESTE ES EL TAMANIO " + file.toFile().length());
		StringBuilder sb = new StringBuilder();
		String res = "";
		if (Files.notExists(file)) {
			throw new PersistenceException("File " + file.toAbsolutePath() + " does not exists");
		} else {
			try {
				res = Files.lines(file, Charset.forName("UTF-8")).collect(Collectors.joining(""));
				sb = sb.append(res);
			} catch (IOException e) {
				log.log(Level.FATAL, "Error loading reviews " + e.getMessage());
			}
		}

		try {
			return xmlToObject(sb.toString(), classType);
		} catch (JsonProcessingException e) {
			log.log(Level.FATAL, "Error during load file " + file.toAbsolutePath() + " " + e.getMessage(), e);
			throw new PersistenceException("Error during load file " + file.toAbsolutePath());
		}
	}

}
