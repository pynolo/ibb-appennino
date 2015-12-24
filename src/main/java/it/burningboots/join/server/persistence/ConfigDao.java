package it.burningboots.join.server.persistence;

import it.burningboots.join.shared.entity.Config;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.apache.commons.beanutils.PropertyUtils;

public class ConfigDao {
	
	public static String saveOrUpdate(PersistenceManager pm, Config entity) {
		String key = null;
		try {
			//Update
			Config persisted = pm.getObjectById(entity.getClass(), entity.getNameKey());
			PropertyUtils.copyProperties(persisted, entity);
			key = entity.getNameKey();
		} catch (JDOObjectNotFoundException e) {
			//Save
			pm.makePersistent(entity);
			key = entity.getNameKey();
		} catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
			e.printStackTrace();/* this cannot happen */
		}
		return key;
	}
	
	@SuppressWarnings("unchecked")
	public static Config findByKey(PersistenceManager pm, String nameKey) {
		//PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(Config.class);
		query.declareParameters("String p");
		query.setFilter("nameKey == p");
		query.setOrdering("nameKey ASC");
		List<Config> entities = (List<Config>) query.execute(nameKey);
		Config result = null;
		if (entities != null) {
			if (entities.size() > 0) result = entities.get(0);
		}
		return result;
	}
	
}
