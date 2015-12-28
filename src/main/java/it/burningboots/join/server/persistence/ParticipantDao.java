package it.burningboots.join.server.persistence;

import it.burningboots.join.shared.OrmException;
import it.burningboots.join.shared.entity.Participant;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class ParticipantDao {
	
	@SuppressWarnings("unchecked")
	public static Participant findByItemNumber(Session ses, String itemNumber) 
			throws OrmException {
		Participant result = null;
		try {
			String qs = "from Participant where "+
					"itemNumber = :s1 "+
					"order by itemNumber";
			Query q = ses.createQuery(qs);
			q.setString("s1", itemNumber);
			List<Participant> entities = (List<Participant>) q.list();
			if (entities != null) {
				if (entities.size() > 0) result = entities.get(0);
			}
		} catch (HibernateException e) {
			throw new OrmException(e.getMessage(), e);
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Participant> find(Session ses, boolean pagato)
			throws OrmException {
		List<Participant> entities = new ArrayList<Participant>();
		try {
			String qs = "from Participant p ";
			if (pagato) qs += "where p.paymentDt is not null ";
			qs += "order by itemNumber";
			Query q = ses.createQuery(qs);
			entities = (List<Participant>) q.list();
		} catch (HibernateException e) {
			throw new OrmException(e.getMessage(), e);
		}
		return entities;
	}
}
