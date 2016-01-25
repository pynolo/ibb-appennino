package it.burningboots.join.client;

import it.burningboots.join.shared.AppConstants;
import it.burningboots.join.shared.PropertyBean;
import it.burningboots.join.shared.entity.Participant;

public class WizardSingleton {

	private static WizardSingleton instance = null;
	private Participant participantBean = null;
	private PropertyBean propertyBean = null;
	private Integer wizardType = AppConstants.WIZARD_REGISTER;
	
	private WizardSingleton() {}
	
	public static WizardSingleton get() {
		if (instance == null) {
			instance = new WizardSingleton();
			instance.setParticipantBean(new Participant());
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

}
