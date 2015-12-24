package it.burningboots.join.server.persistence;

import it.burningboots.join.shared.entity.Participant;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.apache.commons.beanutils.PropertyUtils;

public class ParticipantDao {
	
	public static String saveOrUpdate(PersistenceManager pm, Participant entity) {
		String key = null;
		try {
			//Update
			Participant persisted = pm.getObjectById(entity.getClass(), entity.getItemNumberKey());
			PropertyUtils.copyProperties(persisted, entity);
			key = entity.getItemNumberKey();
		} catch (JDOObjectNotFoundException e) {
			//Save
			pm.makePersistent(entity);
			key = entity.getItemNumberKey();
		} catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
			e.printStackTrace();/* this cannot happen */
		}
		return key;
	}
	
	@SuppressWarnings("unchecked")
	public static Participant findByKey(PersistenceManager pm, String itemNumberKey) {
		//PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(Participant.class);
		query.declareParameters("String p");
		query.setFilter("itemNumberKey == p");
		query.setOrdering("itemNumberKey ASC");
		List<Participant> entities = (List<Participant>) query.execute(itemNumberKey);
		Participant result = null;
		if (entities != null) {
			if (entities.size() > 0) result = entities.get(0);
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Participant> find(PersistenceManager pm) {
		//PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(Participant.class);
		query.setOrdering("key ASC");
		List<Participant> entities = (List<Participant>) query.execute();
		return entities;
	}
}
