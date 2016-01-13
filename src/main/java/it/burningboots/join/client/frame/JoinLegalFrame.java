package it.burningboots.join.client.frame;

import it.burningboots.join.client.UiSingleton;
import it.burningboots.join.client.UriBuilder;
import it.burningboots.join.client.UriDispatcher;
import it.burningboots.join.client.WizardSingleton;
import it.burningboots.join.client.service.DataService;
import it.burningboots.join.client.service.DataServiceAsync;
import it.burningboots.join.shared.AppConstants;
import it.burningboots.join.shared.PropertyBean;
import it.burningboots.join.shared.StringValidator;
import it.burningboots.join.shared.ValidationException;
import it.burningboots.join.shared.entity.Participant;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;

public class JoinLegalFrame extends FramePanel implements IWizardPanel {
	
	private final DataServiceAsync dataService = GWT.create(DataService.class);
	
	private DateTimeFormat DTF = DateTimeFormat.getFormat("dd/MM/yyyy");
	
	private UriBuilder params = null;
	private VerticalPanel cp = null; // Content panel
	private int participantCount = 0;
	
	private TextBox firstNameText;
	private TextBox lastNameText;
	private DateBox birthDate;
	private TextBox birthCityText;
	
	public JoinLegalFrame(UriBuilder params) {
		super();
		if (params != null) {
			this.params = params;
		} else {
			this.params = new UriBuilder();
		}
		Integer idParticipant = this.params.getIntValue(AppConstants.PARAMS_ID);
		cp = new VerticalPanel();
		this.add(cp);
		loadAsyncData(idParticipant);
	}
	
	private void draw() {
		PropertyBean properties = WizardSingleton.get().getPropertyBean();
		Participant participant = WizardSingleton.get().getParticipantBean();
		
		//Check if joining wizard can be active
		if ( properties.getClosed() ) {
			UriDispatcher.loadContent(UriDispatcher.STEP_CLOSED);
		}
		if ( (participantCount >= properties.getBedAvailableUntil()) &&
				(participantCount >= properties.getTentAvailableUntil()) ) {
			UriDispatcher.loadContent(UriDispatcher.STEP_FULL);
		}
		//TITLE
		setTitle("Required information / Dati obbligatori");
		
		
		cp.add(new HTML("<p><i>According to Italian law, we're required to ask you some extra info, please fill the form carefully!</i><br />"+
				"<b>A norma di legge, dobbiamo raccogliere alcuni dati identificativi, per favore riempi il modulo con cura!</b></p>"));
		
		HorizontalPanel namePanel = new HorizontalPanel();
		cp.add(namePanel);

		FlowPanel firstNamePanel = new FlowPanel();
		firstNamePanel.add(new HTML("<i>First name</i><br />"+
				"<b>Nome</b>"));
		firstNameText = new TextBox();
		firstNameText.setMaxLength(64);
		firstNameText.setValue(participant.getFirstName());
		firstNamePanel.add(firstNameText);
		namePanel.add(firstNamePanel);
		
		namePanel.add(new InlineHTML("&nbsp;"));
		
		FlowPanel lastNamePanel = new FlowPanel();
		lastNamePanel.add(new HTML("<i>Last name</i><br />"+
				"<b>Cognome</b>"));
		lastNameText = new TextBox();
		lastNameText.setMaxLength(64);
		lastNameText.setValue(participant.getLastName());
		lastNamePanel.add(lastNameText);
		namePanel.add(lastNamePanel);
		
		cp.add(new HTML("<p>&nbsp;</p>"));
		
		cp.add(new HTML("<p><i>Which is your birth city? (please specify the country)</i><br />"+
				"<b>Quale &egrave; la tua citt&agrave; di nascita? (specifica la nazione)</b></p>"));
		birthCityText = new TextBox();
		birthCityText.setMaxLength(128);
		birthCityText.setValue(participant.getBirthCity());
		cp.add(birthCityText);
		cp.add(new HTML("<p>&nbsp;</p>"));
		
		cp.add(new HTML("<p><i>What is your birth date? (dd/mm/yyyy)</i><br/>"+
				"<b>Quale &egrave; la tua data di nascita? (gg/mm/aaaa)</b></p>"));
		birthDate = new DateBox();
		DateBox.Format BOX_FORMAT_TIMESTAMP = new DateBox.DefaultFormat(DTF);
		birthDate.setFormat(BOX_FORMAT_TIMESTAMP);
		birthDate.setValue(participant.getBirthDt());
		cp.add(birthDate);
		cp.add(new HTML("<p>&nbsp;</p>"));
		
		//Wizard panel
		WizardButtons wb = new WizardButtons(this, true, true);
		cp.add(wb);
	}
	
	
	private boolean storeInBean() {
		String errorMessage = "";
		String firstName = firstNameText.getValue();
		try {
			StringValidator.validateName(firstName);
		} catch (ValidationException e) {
			errorMessage += e.getMessage();
		}
		String lastName = lastNameText.getValue();
		try {
			StringValidator.validateName(lastName);
		} catch (ValidationException e) {
			if (errorMessage.length() > 0) errorMessage += "<br />";
			errorMessage += e.getMessage();
		}
		String birthCity = birthCityText.getValue();
		if (birthCity.length() < 3) {
			if (errorMessage.length() > 0) errorMessage += "<br />";
			errorMessage += "Please note that the birth city is mandatory / La data di nascita &egrave; obbligatoria";
		}
		Date birthDt = birthDate.getValue();
		if (birthDt == null) {
			if (errorMessage.length() > 0) errorMessage += "<br />";
			errorMessage += "Would you mind to fill the birth date field too? / Potresti riempire anche la data di nascita?";
		}
		
		boolean isError = (errorMessage.length() > 0);
		if (isError) {
			UiSingleton.get().addWarning(errorMessage);
		} else {
			//Store in bean
			Participant participant = WizardSingleton.get().getParticipantBean();
			participant.setFirstName(firstName);
			participant.setLastName(lastName);
			participant.setBirthCity(birthCity);
			participant.setBirthDt(birthDt);
		}
		return isError;
	}
	
	@Override
	public void goBackward() {
		boolean isError = storeInBean();
		if (!isError) {
			Participant participant = WizardSingleton.get().getParticipantBean();
			//Forward
			UriBuilder param = new UriBuilder();
			param.add(AppConstants.PARAMS_ITEM_NUMBER, participant.getItemNumber());
			param.triggerUri(UriDispatcher.STEP_JOIN_VOLUNTEER);
		}
	}
	
	@Override
	public void goForward() {
		boolean isError = storeInBean();
		if (!isError) {
			Participant participant = WizardSingleton.get().getParticipantBean();
			//Forward
			UriBuilder param = new UriBuilder();
			param.add(AppConstants.PARAMS_ITEM_NUMBER, participant.getItemNumber());
			param.triggerUri(UriDispatcher.STEP_JOIN_CHECKOUT);
		}
	}
	
	
	
	//Async methods
	
	
	private void loadAsyncData(Integer idParticipant) {
		AsyncCallback<Participant> callback = new AsyncCallback<Participant>() {
			@Override
			public void onFailure(Throwable caught) {
				UiSingleton.get().addError(caught);
			}
			@Override
			public void onSuccess(Participant result) {
				WizardSingleton.get().setParticipantBean(result);
				draw();
			}
		};
		
		if (idParticipant == null) {
			//No idParticipant passed => brand new participant
			dataService.createTransientParticipant(callback);
		} else {
			//itemNumberKey passed => check participant in WizardSingleton and load it from DB if empty
			Participant prt = WizardSingleton.get().getParticipantBean();
			if (prt == null) {
				dataService.findParticipantById(idParticipant, callback);
			}
		}
		
	}
	
}
