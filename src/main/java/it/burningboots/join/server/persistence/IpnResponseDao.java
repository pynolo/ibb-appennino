package it.burningboots.join.server.persistence;

import it.burningboots.join.shared.entity.IpnResponse;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.apache.commons.beanutils.PropertyUtils;

public class IpnResponseDao {
	
	public static String saveOrUpdate(PersistenceManager pm, IpnResponse entity) {
		String key = null;
		try {
			//Update
			IpnResponse persisted = pm.getObjectById(entity.getClass(), entity.getIdKey());
			PropertyUtils.copyProperties(persisted, entity);
			key = entity.getIdKey();
		} catch (JDOObjectNotFoundException e) {
			//Save
			pm.makePersistent(entity);
			key = entity.getIdKey();
		} catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
			e.printStackTrace();/* this cannot happen */
		}
		return key;
	}
	
	@SuppressWarnings("unchecked")
	public static IpnResponse findByKey(PersistenceManager pm, String idKey) {
		//PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(IpnResponse.class);
		query.declareParameters("String p");
		query.setFilter("idKey == p");
		query.setOrdering("idKey ASC");
		List<IpnResponse> entities = (List<IpnResponse>) query.execute(idKey);
		IpnResponse result = null;
		if (entities != null) {
			if (entities.size() > 0) result = entities.get(0);
		}
		return result;
	}
	
}
