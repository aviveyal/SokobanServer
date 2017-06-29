package com.example.DB;

/**
 * databasae holding all solution , this class handler all the query to control DB
 */

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class DbHandler {

	private SessionFactory factory;

	public DbHandler() {

		Configuration config = new Configuration();
		config.configure();
		factory = config.buildSessionFactory();

	}

	public void addSolution(SokobanSolution sol) {
		Session session = null;
		Transaction tx = null;
try{
		session = factory.openSession();
		tx = session.beginTransaction();

		session.save(sol);
		tx.commit();

	}
	catch (HibernateException ex) {
		if (tx != null)
			tx.rollback();
		System.out.println(ex.getMessage());
	}
		finally{
		if (session != null)
			session.close();
		}
	}

	public String getSolution(String name) {

		Session session = null;
	try{	
		session = factory.openSession();

		SokobanSolution sol = session.get(SokobanSolution.class, name);
		if (sol != null) {
			return sol.getSol();
		}
	}
	catch (HibernateException ex) {			
		System.out.println(ex.getMessage());
	}
	finally {
		if (session != null)
			session.close();
	}
		return null;
	}

}
