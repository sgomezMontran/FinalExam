package com.montran.demo.main;


import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.montran.demo.serializer.LocalDateTimeDeserializer;
import com.montran.demo.serializer.LocalDateTimeSerializer;


public class Person {
	private String nombre;
	private volatile int age;
	private AtomicInteger counter;


	public int getCounter() {
		return counter.get();
	}

	public void setCounter(int counter) {
		this.counter.set(counter);
	}

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

	public synchronized int getAge() {
		return age;
	}

	public synchronized void setAge(int age) {
		this.age = age;
	}

	/**
	 * para aumentar valores thread safe con volatile and synchronized
	 */
	public synchronized void increment(int a) {
		this.age = this.age + a;
	}

	/**
	 * para aumentar valores thread safe con atomicos
	 */
	public void atomicIncrement() {
		
		this.counter.getAndIncrement();
	}

	public Person() {
		this.counter = new AtomicInteger();
	}
}
