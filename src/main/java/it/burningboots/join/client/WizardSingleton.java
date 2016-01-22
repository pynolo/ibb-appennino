package it.burningboots.join.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

import it.burningboots.join.client.service.DataService;
import it.burningboots.join.client.service.DataServiceAsync;
import it.burningboots.join.shared.AppConstants;
import it.burningboots.join.shared.PropertyBean;
import it.burningboots.join.shared.entity.Participant;

public class WizardSingleton {

	private static WizardSingleton instance = null;
	private Participant participantBean = null;
	private PropertyBean propertyBean = null;
	private int participantCount = 0;
	private Integer wizardType = AppConstants.WIZARD_REGISTER;
	private String accessKey = null;
	
	private WizardSingleton() {}
	
	public static WizardSingleton get() {
		if (instance == null) {
			instance = new WizardSingleton();
			instance.setParticipantBean(new Participant());
			instance.loadParticipantCount();
			instance.loadAccessKey();
		}
		return instance;
	}

	public Participant getParticipantBean() {
		return participantBean;
	}

	public void setParticipantBean(Participant participantBean) {
		this.participantBean = participantBean;
	}

	public PropertyBean getPropertyBean() {
		return propertyBean;
	}

	public void setPropertyBean(PropertyBean propertyBean) {
		this.propertyBean = propertyBean;
	}

	public Integer getWizardType() {
		return wizardType;
	}

	public void setWizardType(Integer wizardType) {
		this.wizardType = wizardType;
	}	
	
	public int getParticipantCount() {
		return participantCount;
	}

	public void setParticipantCount(int participantCount) {
		this.participantCount = participantCount;
	}	

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}
	
	
	
	// Async methods



	private void loadParticipantCount() {
		DataServiceAsync dataService = GWT.create(DataService.class);
		
		AsyncCallback<Integer> callback = new AsyncCallback<Integer>() {
			@Override
			public void onFailure(Throwable caught) {
				UiSingleton.get().addError(caught);
			}
			@Override
			public void onSuccess(Integer result) {
				participantCount = result;
			}
		};
		dataService.countConfirmedParticipants(callback);
	}
	
	private void loadAccessKey() {
		DataServiceAsync dataService = GWT.create(DataService.class);
		
		AsyncCallback<String> callback = new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught) {
				UiSingleton.get().addError(caught);
			}
			@Override
			public void onSuccess(String result) {
				accessKey = result;
			}
		};
		dataService.getAccessKey(callback);
	}
}
