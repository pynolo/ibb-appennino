package it.burningboots.join.server.servlet;

import it.burningboots.join.client.service.DataService;
import it.burningboots.join.server.DataBusiness;
import it.burningboots.join.server.EnvSingleton;
import it.burningboots.join.server.PropertyReader;
import it.burningboots.join.server.persistence.ConfigDao;
import it.burningboots.join.server.persistence.GenericDao;
import it.burningboots.join.server.persistence.ParticipantDao;
import it.burningboots.join.server.persistence.SessionFactory;
import it.burningboots.join.shared.AppConstants;
import it.burningboots.join.shared.OrmException;
import it.burningboots.join.shared.PropertyBean;
import it.burningboots.join.shared.SystemException;
import it.burningboots.join.shared.entity.Config;
import it.burningboots.join.shared.entity.Participant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class DataServiceImpl extends RemoteServiceServlet implements
		DataService {
	
	private static final Logger LOG = LoggerFactory.getLogger(IpnServlet.class);
	
	@Override
	public PropertyBean getPropertyBean() throws SystemException {
		PropertyBean bean = new PropertyBean();
		try {
			//From app file
			bean.setVersion(PropertyReader.readProperty(PropertyReader.PROPERTY_VERSION));
			String closedString = PropertyReader.readProperty(PropertyReader.PROPERTY_CLOSED);
			if (closedString.equals("false")) bean.setClosed(false);
			if (closedString.equals("true")) bean.setClosed(true);
			bean.setBedAvailableFrom(PropertyReader.readProperty(PropertyReader.PROPERTY_BED_FROM));
			bean.setBedAvailableUntil(PropertyReader.readProperty(PropertyReader.PROPERTY_BED_UNTIL));
			bean.setBedMax(PropertyReader.readProperty(PropertyReader.PROPERTY_BED_MAX));
			bean.setBedPrice(PropertyReader.readProperty(PropertyReader.PROPERTY_BED_PRICE));
			bean.setTentAvailableFrom(PropertyReader.readProperty(PropertyReader.PROPERTY_TENT_FROM));
			bean.setTentAvailableUntil(PropertyReader.readProperty(PropertyReader.PROPERTY_TENT_UNTIL));
			bean.setTentMax(PropertyReader.readProperty(PropertyReader.PROPERTY_TENT_MAX));
			bean.setTentPrice(PropertyReader.readProperty(PropertyReader.PROPERTY_TENT_PRICE));
			//From config file
			bean.setAccessKey(EnvSingleton.get().getAccessKey());
		} catch (IOException e) { // catch exception in case properties file does not exist
			LOG.error(e.getMessage(), e);
			throw new SystemException(e.getMessage(), e);
		}
		//From DB
		Session ses = SessionFactory.getSession();
		Transaction trn = ses.beginTransaction();
		try {
			int bedCount = ParticipantDao.countConfirmed(ses, AppConstants.ACCOMMODATION_BED);
			bean.setBedCount(bedCount);
			int tentCount = ParticipantDao.countConfirmed(ses, AppConstants.ACCOMMODATION_TENT);
			bean.setTentCount(tentCount);
			trn.commit();
		} catch (OrmException e) {
			trn.rollback();
			LOG.error(e.getMessage(), e);
			throw new SystemException(e.getMessage(), e);
		} finally {
			ses.close();
		}
		return bean;
	}
	
	@Override
	public Config findConfigByKey(String name) throws SystemException {
		Config config = null;
		Session ses = SessionFactory.getSession();
		Transaction trn = ses.beginTransaction();
		try {
			config = ConfigDao.findByName(ses, name);
			trn.commit();
		} catch (OrmException e) {
			trn.rollback();
			LOG.error(e.getMessage(), e);
			throw new SystemException(e.getMessage(), e);
		} finally {
			ses.close();
		}
		return config;
	}

	@Override
	public Integer saveOrUpdateConfig(Config config) throws SystemException {
		Integer id = null;
		Session ses = SessionFactory.getSession();
		Transaction trn = ses.beginTransaction();
		try {
			if (config.getId() == null) {
				id = (Integer) GenericDao.saveGeneric(ses, config);
			} else {
				id = config.getId();
				GenericDao.updateGeneric(ses, id, config);
			}
			trn.commit();
		} catch (OrmException e) {
			trn.rollback();
			LOG.error(e.getMessage(), e);
			throw new SystemException(e.getMessage(), e);
		} finally {
			ses.close();
		}
		return id;
	}

	@Override
	public Participant findParticipantById(Integer id) throws SystemException {
		Participant p = null;
		Session ses = SessionFactory.getSession();
		Transaction trn = ses.beginTransaction();
		try {
			p = GenericDao.findById(ses, Participant.class, id);
			trn.commit();
		} catch (OrmException e) {
			trn.rollback();
			LOG.error(e.getMessage(), e);
			throw new SystemException(e.getMessage(), e);
		} finally {
			ses.close();
		}
		return p;
	}
	
	@Override
	public Participant findParticipantByItemNumber(String itemNumber, int delayMillis) throws SystemException {
		Participant p = null;
		if (delayMillis > 0) {
			try {
			    Thread.sleep(delayMillis);
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
		}
		Session ses = SessionFactory.getSession();
		Transaction trn = ses.beginTransaction();
		try {
			p = ParticipantDao.findByItemNumber(ses, itemNumber);
			trn.commit();
		} catch (OrmException e) {
			trn.rollback();
			LOG.error(e.getMessage(), e);
			throw new SystemException(e.getMessage(), e);
		} finally {
			ses.close();
		}
		return p;
	}

	@Override
	public List<Participant> findParticipants(boolean confirmed) throws SystemException {
		List<Participant> pList = new ArrayList<Participant>();
		Session ses = SessionFactory.getSession();
		Transaction trn = ses.beginTransaction();
		try {
			pList = ParticipantDao.find(ses, confirmed);
			trn.commit();
		} catch (OrmException e) {
			trn.rollback();
			LOG.error(e.getMessage(), e);
			throw new SystemException(e.getMessage(), e);
		} finally {
			ses.close();
		}
		return pList;
	}
	
	@Override
	public Integer saveOrUpdateParticipant(Participant prt) throws SystemException {
		Integer id = null;
		Session ses = SessionFactory.getSession();
		Transaction trn = ses.beginTransaction();
		try {
			Date now = new Date();
			prt.setUpdateDt(now);
			Participant oldPrt = null;
			if (prt.getId() != null) oldPrt = GenericDao.findById(ses, Participant.class, prt.getId());
			if (oldPrt == null) {
				prt.setEmailOriginal(prt.getEmail());
				prt.setCreationDt(now);
				id = (Integer) GenericDao.saveGeneric(ses, prt);
			} else {
				id = prt.getId();
				GenericDao.updateGeneric(ses, prt.getId(), prt);
			}
        	trn.commit();
		} catch (OrmException e) {
			trn.rollback();
			LOG.error(e.getMessage(), e);
			throw new SystemException(e.getMessage(), e);
		} finally {
			ses.close();
		}
		return id;
	}

	@Override
	public Participant createTransientParticipant() throws SystemException {
		try {
			String itemNumber = DataBusiness.createCode(this.getClass().getName(), AppConstants.ITEM_NUMBER_LENGHT);
			Participant prt = new Participant();
			prt.setItemNumber(itemNumber);
			prt.setAccommodationType(AppConstants.ACCOMMODATION_BED);
			return prt;
		} catch (Exception e) {
			throw new SystemException(e.getMessage(), e);
		}
	}
}
