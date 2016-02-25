package it.burningboots.appennino.server.persistence;

import it.burningboots.appennino.shared.OrmException;
import it.burningboots.appennino.shared.entity.Discount;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class DiscountDao {
	
	@SuppressWarnings("unchecked")
	public static Discount findDiscount(Session ses, String email)
			throws OrmException {
		Discount result = null;
		try {
			String qs = "from Discount where "+
					"email like :s1 "+
					"order by id asc";
			Query q = ses.createQuery(qs);
			q.setString("s1", email);
			List<Discount> entities = (List<Discount>) q.list();
	
			if (entities != null) {
				if (entities.size() > 0) result = entities.get(0);
			}
		} catch (HibernateException e) {
			throw new OrmException(e.getMessage(), e);
		}
		return result;
	}
	
}
