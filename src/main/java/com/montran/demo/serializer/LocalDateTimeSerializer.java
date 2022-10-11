package com.montran.demo.serializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * 
 * Class that serialize a LocalDate object.
 * 
 * @author Santiago Gomez
 */
@SuppressWarnings("serial")
public class LocalDateTimeSerializer extends StdSerializer<LocalDateTime> {

	public LocalDateTimeSerializer() {
		super(LocalDateTime.class);
	}

	@Override
	public void serialize(LocalDateTime value, JsonGenerator generator, SerializerProvider provider)
			throws IOException {
		generator.writeString(value.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
	}
}