package it.burningboots.join.server.persistence;

import org.hibernate.Session;

public class SessionFactory {

	public static Session getSession() {
		return HibernateSessionFactory.getSession();
	}
	
//	public static void closeSession() {
//		HibernateSessionFactory.closeSession();
//	}

}