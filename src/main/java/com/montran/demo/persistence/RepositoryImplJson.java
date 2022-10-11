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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.montran.demo.exception.PersistenceException;
import com.montran.demo.utils.PropertiesUtil;


/**
 * Class that persist an Object into a JSON File
 * 
 * @author Santiago Gomez
 * @see https://mkyong.com/java/jackson-2-convert-java-object-to-from-json/
 * @param <T>
 */
public class RepositoryImplJson<T> implements Repository<T> {
	private static Logger log = Logger.getLogger(RepositoryImplJson.class.getName());

	private static final Properties CONFIG = PropertiesUtil.getProperties("config.properties");
	private static final String EXTENSION = ".json";
	private final Path dataFolder = Path.of(CONFIG.getProperty("repository.data.folder", "src/main/resources"));

	/**
	 * Saves a object into a Json file
	 */
	@Override
	public void save(T obj, String fileName) throws IOException {
		Path file = dataFolder.resolve(fileName + getValidatedExtension(EXTENSION));
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		try (PrintWriter out = new PrintWriter(new OutputStreamWriter(
		        Files.newOutputStream(file, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE), Charset.forName("UTF-8")))) {
			String jsonData = ow.writeValueAsString(obj);
			out.append(jsonData + System.lineSeparator());

		}

		log.log(Level.INFO, String.format("File %s saved successfully", file.toAbsolutePath()));

	}

	/**
	 * Read a object from a JSON file
	 * 
	 * @param T object type
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T load(Class<?> classType, String fileName) throws PersistenceException {
		ObjectMapper mapper = new ObjectMapper();

		Path file = dataFolder.resolve(fileName + getValidatedExtension(EXTENSION));
		StringBuilder sb = new StringBuilder();
		String res = "";
		if (Files.notExists(file)) {
			throw new PersistenceException("File " + file.toAbsolutePath() + " does not exists");
		} else {
			try {
				res = Files.lines(file, Charset.forName("UTF-8")).collect(Collectors.joining(""));
				sb = sb.append(res);
			} catch (IOException e) {
				log.log(Level.FATAL, "Error loading objects " + e.getMessage());
			}
		}

		try {
			return (T) mapper.readValue(sb.toString(), classType);
		} catch (JsonProcessingException e) {
			log.log(Level.FATAL, "Error during load file " + file.toAbsolutePath() + " " + e.getMessage(), e);
			throw new PersistenceException("Error during load file " + file.toAbsolutePath());
		}

	}

	/**
	 * validates extension from File Name
	 * 
	 * @param extension
	 * @return validated extension
	 */
	private String getValidatedExtension(String extension) {
		return extension.startsWith(".") ? extension : ".".concat(extension);
	}

}
