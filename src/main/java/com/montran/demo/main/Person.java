package com.montran.demo.main;


import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.montran.demo.serializer.LocalDateTimeDeserializer;
import com.montran.demo.serializer.LocalDateTimeSerializer;


public class Person {
	private String nombre;
	private volatile int age;

	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime birthDate;


	public LocalDateTime getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDateTime birthDate) {
		this.birthDate = birthDate;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getAge() {
		return age;
	}

	public synchronized void setAge(int age) {
		this.age = age + 1;
	}

	public Person() {
	}
}
