package com.montran.demo.main;


public class Person {
	private String nombre;
	private volatile int age;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age + 1;
	}
}
