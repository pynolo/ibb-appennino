package it.burningboots.join.server.persistence;

import it.burningboots.join.shared.OrmException;
import it.burningboots.join.shared.entity.Config;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class ConfigDao {
	
	@SuppressWarnings("unchecked")
	public static Config findByName(Session ses, String name)
			throws OrmException {
		Config result = null;
		try {
			String qs = "from Config where "+
					"name = :s1 "+
					"order by name asc";
			Query q = ses.createQuery(qs);
			q.setString("s1", name);
			List<Config> entities = (List<Config>) q.list();
	
			if (entities != null) {
				if (entities.size() > 0) result = entities.get(0);
			}
		} catch (HibernateException e) {
			throw new OrmException(e.getMessage(), e);
		}
		return result;
	}
	
}
