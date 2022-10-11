package com.montran.demo.main;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.montran.demo.exception.PersistenceException;
import com.montran.demo.persistence.PersistenceFactory;
import com.montran.demo.persistence.Repository;
import com.montran.demo.utils.PropertiesUtil;


/**
 * Demo to test Component Solution
 * 
 * @author Santiago Gómez
 *
 */
public class Demo {

	private static final String ACCOUNT_LEDGER_NAME = "person";
	private static final Logger log = Logger.getLogger(Demo.class.getName());

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws InterruptedException {
		Person p = new Person();
		// p.setNombre("Santiago");
		// p.setAge(10);
		// p.setBirthDate(LocalDateTime.now());
		// Properties prop = PropertiesUtil.getProperties("myYml.yml");
		// System.out.println(prop.get("nombre"));
		// Integer a = (Integer) prop.get("count");
		// System.out.println(a);
		// testPersistence(p);
		Runnable run = new Runnable() {
			public void run() {
				for (int i = 0; i < 10000; i++) {
					// System.out.println(Thread.currentThread().getName() + " " + p.getAge());
					// p.increment(2);
					p.atomicIncrement();

				}
			}
		};
		Thread[] threads = new Thread[100];
		for (int i = 0; i < threads.length; i++) {
			// System.out.println(Thread.currentThread().getName() + " " + p.getAge());

			// creando threads
			threads[i] = new Thread(run);
			threads[i].start();

		}
		for (int i = 0; i < threads.length; i++) {
			// System.out.println(Thread.currentThread().getName() + " " + p.getAge());

			threads[i].join();


		}
		//
		// System.out.println("Res " + p.getAge());
		System.out.println("Res " + p.getCounter());

		// testReadYml();
		// testPersistence(p);

	}

	@SuppressWarnings("unchecked")
	public static void testReadYml() {
		Properties properties = PropertiesUtil.getYmlProperties("prueba.yml");
		// // Properties properties = PropertiesUtil.getProperties("config");
		// Object value = properties.getProperty("id");
		// if (value == null) {
		// log.warn("no hay valor");
		// value = (int) 20;
		// System.out.println(value);
		// }
		//
		// System.out.println(properties.getProperty("persist-classname", "10"));
		// Map<String, Object> map = (Map<String, Object>) properties.get("ontimize");
		// map.forEach((k, v) -> System.out.println(k + " " + v));
		//
		// if (map instanceof Map) {
		// Map<String, Object> map1 = (Map<String, Object>) map.get("globalcors");
		// if (map1 instanceof Map) {
		// Map<String, Object> map2 = (Map<String, Object>) map1.get("cors-configurations");
		// if (map2 instanceof Map) {
		// Map<String, Object> map3 = (Map<String, Object>) map2.get("[/**]");
		// System.out.println(map3.get("exposed-headers"));
		// }
		// }
		// }
		// properties.forEach((k,v)->System.out.println(k+" "+v));

		Map<String, Map<String, Map<String, Map<String, Object>>>> multimap = (Map<String, Map<String, Map<String, Map<String, Object>>>>) properties
		        .get("ontimize");
		Map<String, Object> myMap = multimap.get("globalcors").get("cors-configurations").get("[/**]");
		System.out.println(((List<String>) myMap.get("exposed-headers")).get(0));
		System.out.println(multimap);

		// Properties properties = PropertiesUtil.getProperties("prueba.yml");
		// System.out.println(properties.getProperty("persist-classname", "no value"));
	}

	@SuppressWarnings("unchecked")
	public static void testPersistence(Person p) {
		if (p == null) {
			log.warn("nos se ha enviado informacion");
			return;
		}
		Repository<Person> repo = null;
		try {
			repo = (Repository<Person>) PersistenceFactory.getInstance().getPersistence();
			p = repo.load(Person.class, ACCOUNT_LEDGER_NAME);

			if (p != null) {
				log.info(String.format("persona %s cargada correctamente", p.getBirthDate()));
				return;
			}
			p.setNombre("Santiago");
			repo.save(p, ACCOUNT_LEDGER_NAME);
			log.info("persona creada correctamente");
		} catch (PersistenceException | IOException e) {

			p.setNombre("Santiago");
			try {
				repo.save(p, ACCOUNT_LEDGER_NAME);
				log.info("persona creada correctamente");
			} catch (IOException e1) {
				log.error("Ocurrio un error ", e);
			}
		}
	}


}

