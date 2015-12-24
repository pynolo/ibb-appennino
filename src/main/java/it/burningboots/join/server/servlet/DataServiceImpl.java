package it.burningboots.join.server.servlet;

import it.burningboots.join.client.service.DataService;
import it.burningboots.join.server.DataBusiness;
import it.burningboots.join.server.PropertyReader;
import it.burningboots.join.server.jdo.PMF;
import it.burningboots.join.server.persistence.ConfigDao;
import it.burningboots.join.server.persistence.ParticipantDao;
import it.burningboots.join.shared.AppConstants;
import it.burningboots.join.shared.PropertyBean;
import it.burningboots.join.shared.SystemException;
import it.burningboots.join.shared.entity.Config;
import it.burningboots.join.shared.entity.Participant;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class DataServiceImpl extends RemoteServiceServlet implements
		DataService {

	@Override
	public PropertyBean getPropertyBean() {
		PropertyBean bean = new PropertyBean();
		bean.setVersion(PropertyReader.readProperty(PropertyReader.PROPERTY_VERSION));
		String closedString = PropertyReader.readProperty(PropertyReader.PROPERTY_CLOSED);
		if (closedString.equals("false")) bean.setClosed(false);
		if (closedString.equals("true")) bean.setClosed(true);
		bean.setBedAvailableFrom(PropertyReader.readProperty(PropertyReader.PROPERTY_BED_FROM));
		bean.setBedAvailableUntil(PropertyReader.readProperty(PropertyReader.PROPERTY_BED_UNTIL));
		bean.setBedPrice(PropertyReader.readProperty(PropertyReader.PROPERTY_BED_PRICE));
		bean.setTentAvailableFrom(PropertyReader.readProperty(PropertyReader.PROPERTY_TENT_FROM));
		bean.setTentAvailableUntil(PropertyReader.readProperty(PropertyReader.PROPERTY_TENT_UNTIL));
		bean.setTentPrice(PropertyReader.readProperty(PropertyReader.PROPERTY_TENT_PRICE));
		return bean;
	}
	
	@Override
	public Config findConfigByKey(String nameKey) throws SystemException {
		try {
			PersistenceManager pm = PMF.get().getPersistenceManager();
			Config config = ConfigDao.findByKey(pm, nameKey);
			return config;
		} catch (Exception e) {
			throw new SystemException(e.getMessage(), e);
		}
	}

	@Override
	public String saveOrUpdateConfig(Config config) throws SystemException {
		try {
			String key = null;
			PersistenceManager pm = PMF.get().getPersistenceManager();
			Transaction tx = pm.currentTransaction();
	        try {
	        	tx.begin();
	        	key = ConfigDao.saveOrUpdate(pm, config);
		        tx.commit();
			} catch (Exception e) {
				throw new SystemException(e.getMessage(), e);
		    } finally {
		        if (tx.isActive()) {
		            tx.rollback();
		        }
		    }
			return key;
		} catch (Exception e) {
			throw new SystemException(e.getMessage(), e);
		}
	}

	@Override
	public Participant findParticipantByKey(String itemNumberKey) throws SystemException {
		try {
			PersistenceManager pm = PMF.get().getPersistenceManager();
			Participant p = ParticipantDao.findByKey(pm, itemNumberKey);
			return p;
		} catch (Exception e) {
			throw new SystemException(e.getMessage(), e);
		}
	}

	@Override
	public List<Participant> findParticipants() throws SystemException {
		try {
			PersistenceManager pm = PMF.get().getPersistenceManager();
			List<Participant> pList = ParticipantDao.find(pm);
			return pList;
		} catch (Exception e) {
			throw new SystemException(e.getMessage(), e);
		}
	}

	@Override
	public String saveOrUpdateParticipant(Participant prt) throws SystemException {
		try {
			String key = null;
			PersistenceManager pm = PMF.get().getPersistenceManager();
			Transaction tx = pm.currentTransaction();
	        try {
	        	tx.begin();
	        	prt.setArrivalTime(DataBusiness.escape(prt.getArrivalTime()));
	        	prt.setCountryName(DataBusiness.escape(prt.getCountryName()));
	        	key = ParticipantDao.saveOrUpdate(pm, prt);
		        tx.commit();
	        } catch (Exception e) {
				throw new SystemException(e.getMessage(), e);
		    } finally {
		        if (tx.isActive()) {
		            tx.rollback();
		        }
		    }
			return key;
		} catch (Exception e) {
			throw new SystemException(e.getMessage(), e);
		}
	}

	@Override
	public Participant createTransientParticipant() throws SystemException {
		try {
			String itemNumberKey = DataBusiness.createCode(this.getClass().getName(), AppConstants.ITEM_NUMBER_LENGHT);
			Participant prt = new Participant(itemNumberKey);
			return prt;
		} catch (Exception e) {
			throw new SystemException(e.getMessage(), e);
		}
	}
}
