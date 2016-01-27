package it.burningboots.join.client.frame;

import it.burningboots.join.client.ClientConstants;
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

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class JoinBaseFrame extends FramePanel implements IWizardPanel {
	
	private final DataServiceAsync dataService = GWT.create(DataService.class);
	
	private UriBuilder params = null;
	private VerticalPanel cp = null; // Content panel
	
	private RadioButton bedRadio;
	private RadioButton tentRadio;
	private TextBox emailText;
	
	public JoinBaseFrame(UriBuilder params) {
		super();
		if (params != null) {
			this.params = params;
		} else {
			this.params = new UriBuilder();
		}
		String itemNumber = this.params.getValue(AppConstants.PARAMS_ITEM_NUMBER);
		if (itemNumber == null) itemNumber = "";
		cp = new VerticalPanel();
		this.add(cp);
		loadAsyncData(itemNumber);
	}
	
	private void draw() {
		if (WizardSingleton.get().getWizardType().equals(AppConstants.WIZARD_REGISTER))
				forwardIfJoinNotPossible();
		PropertyBean pb = WizardSingleton.get().getPropertyBean();
		Participant participant = WizardSingleton.get().getParticipantBean();
		
		//TITLE
		setTitle("Registration / Iscrizione");
		
		VerticalPanel accommodationPanel = new VerticalPanel();
		cp.add(accommodationPanel);
		
		accommodationPanel.add(new HTML("<p><i>Welcome to IBB registration form! Choose your accommodation:</i><br />"+
				"<b>IBB ti d&agrave; il benvenuto sul form di registrazione! Scegli dove vuoi dormire:</b></p>"));
		
		HorizontalPanel bedPanel = new HorizontalPanel();
		accommodationPanel.add(bedPanel);
		bedRadio = new RadioButton("accommodation", "<b>Hut / Rifugio</b> - &euro;"+
				ClientConstants.FORMAT_CURRENCY.format(pb.getBedPrice())+"&nbsp;&nbsp;", true);
		bedPanel.add(bedRadio);
		int bedAvail = pb.getAvailableBed();
		String bedDescr = "";
		if (bedAvail <= 4) {
			bedDescr += "<b>"+bedAvail+"</b> to sold-out!<br />";
		}
		bedDescr += "<i>Food included, sleeping bag needed</i><br />"+
				"<b>Cibo incluso, serve un sacco a pelo</b>";
		bedPanel.add(new HTML(bedDescr));
		
		HorizontalPanel tentPanel = new HorizontalPanel();
		accommodationPanel.add(tentPanel);
		tentRadio = new RadioButton("accommodation", "<b>Tent / Tenda</b> - &euro;"+
				ClientConstants.FORMAT_CURRENCY.format(pb.getTentPrice())+"&nbsp;&nbsp;", true);
		tentPanel.add(tentRadio);
		int tentAvail = pb.getAvailableTent();
		String tentDescr = "";
		if (tentAvail <= 4) {
			tentDescr += "<b>"+tentAvail+"</b> to sold-out!<br />";
		}
		tentDescr += "<i>Food not included, radical self-reliance</i><br />"+
				"<b>Cibo non incluso, autosostentamento radicale</b>";
		tentPanel.add(new HTML(tentDescr));
		
		if (participant.getAccommodationType().equals(AppConstants.ACCOMMODATION_TENT)) {
			tentRadio.setValue(true);
		} else {
			bedRadio.setValue(true);
		}
		cp.add(new HTML("<p>&nbsp;</p>"));
		
		
		cp.add(new HTML("<p><i>Your email to receive information about the event</i><br/>"+
				"<b>La tua email per ricevere dettagli e avvisi sull'evento</b></p>"));
		emailText = new TextBox();
		emailText.setValue(participant.getEmail());
		cp.add(emailText);
		cp.add(new HTML("<p><i>(You will be receiving IBB 2016 official announcements)</i><br/>"
				+ "<b>(Riceverai gli annunci ufficiali di IBB 2016)</b></p>"));
		
		//Wizard panel
		WizardButtons wb = new WizardButtons(this, false, true);
		cp.add(wb);
	}
	
	@Override
	public void goBackward() {
		/* disabled */
	}
	
	@Override
	public void goForward() {
		//Verification
		String errorMessage = "";
		String email = emailText.getValue();
		try {
			StringValidator.validateEmail(email);
		} catch (ValidationException e) {
			if (errorMessage.length() > 0) errorMessage += "<br />";
			errorMessage += e.getMessage();
		}
		
		if (errorMessage.length() > 0) {
			UiSingleton.get().addWarning(errorMessage);
		} else {
			//Store in bean
			Participant participant = WizardSingleton.get().getParticipantBean();
			if (tentRadio.getValue()) {
				participant.setAccommodationType(AppConstants.ACCOMMODATION_TENT);
			} else {
				participant.setAccommodationType(AppConstants.ACCOMMODATION_BED);
			}
			participant.setEmail(email);
			//Forward
			UriBuilder param = new UriBuilder();
			param.add(AppConstants.PARAMS_ITEM_NUMBER, participant.getItemNumber());
			param.triggerUri(UriDispatcher.STEP_JOIN_VOLUNTEER);
		}
	}

	
	
	//Async methods
	
	
	private void loadAsyncData(String itemNumber) {
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
		
		if (itemNumber.length() == 0) {
			//No itemNumber passed => brand new participant
			dataService.createTransientParticipant(callback);
		} else {
			//itemNumberKey passed => check participant in WizardSingleton and load it from DB if empty
			Participant prt = WizardSingleton.get().getParticipantBean();
			if (prt == null) {
				dataService.findParticipantByItemNumber(itemNumber, callback);
			} else {
				draw();
			}
		}
	}
	
}
