package com.montran.demo.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * 
 * Class that deserialize a LocalDate object.
 * 
 * @author Santiago Gomez
 */
@SuppressWarnings("serial")
public class LocalDateTimeDeserializer extends StdDeserializer<LocalDateTime> {

	protected LocalDateTimeDeserializer() {
		super(LocalDateTime.class);
	}

	@Override
	public LocalDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException {
		return LocalDateTime.parse(parser.readValueAs(String.class));
	}
}
