package com.montran.demo.main;


import java.io.IOException;

import org.apache.log4j.Logger;

import com.montran.demo.exception.PersistenceException;
import com.montran.demo.persistence.PersistenceFactory;
import com.montran.demo.persistence.Repository;


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
	public static void main(String[] args) {
		final Person p = new Person();
		p.setAge(1);
		// Repository<Person> repo = null;
		// try {
		//
		// repo = (Repository<Person>) PersistenceFactory.getInstance().getPersistence();
		// p = repo.load(Person.class, ACCOUNT_LEDGER_NAME);
		//
		// if (p != null) {
		// log.info(String.format("persona %s cargada correctamente", p.getNombre()));
		// return;
		// }
		// p = new Person();
		// p.setNombre("Santiago");
		// repo.save(p, ACCOUNT_LEDGER_NAME);
		// log.info("persona creada correctamente");
		// } catch (PersistenceException | IOException e) {
		// p = new Person();
		// p.setNombre("Santiago");
		// try {
		// repo.save(p, ACCOUNT_LEDGER_NAME);
		// log.info("persona creada correctamente");
		// } catch (IOException e1) {
		// log.error("Ocurrio un error ", e);
		// }
		//
		//
		// }

		Runnable run = new Runnable() {
			public void run() {
				
				for (int i = 0; i < 10; i++) {
					System.out.println(Thread.currentThread().getName() + " " + p.getAge());
					p.setAge(p.getAge() + 1);
				}
			}
		};
		for (int i = 0; i < 10; i++) {
			// System.out.println(Thread.currentThread().getName() + " " + p.getAge());
			Thread t = new Thread(run);
			t.start();
		}
		System.out.println("Res " + p.getAge());


	}


}

