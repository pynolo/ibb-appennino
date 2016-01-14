package it.burningboots.join.client.frame;

import it.burningboots.join.client.UiSingleton;
import it.burningboots.join.client.UriBuilder;
import it.burningboots.join.client.UriDispatcher;
import it.burningboots.join.client.WizardSingleton;
import it.burningboots.join.client.service.DataService;
import it.burningboots.join.client.service.DataServiceAsync;
import it.burningboots.join.shared.AppConstants;
import it.burningboots.join.shared.StringValidator;
import it.burningboots.join.shared.ValidationException;
import it.burningboots.join.shared.entity.Participant;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class JoinInclusionFrame extends FramePanel implements IWizardPanel {
	
	private final DataServiceAsync dataService = GWT.create(DataService.class);
	
	private UriBuilder params = null;
	private VerticalPanel cp = null; // Content panel
	
	private TextBox emailText;
	private CheckBox ibbCheck;
	private CheckBox burnerCheck;
	
	public JoinInclusionFrame(UriBuilder params) {
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
		forwardIfJoinNotPossible();
		Participant participant = WizardSingleton.get().getParticipantBean();
		
		//TITLE
		setTitle("Registration / Iscrizione");
		
		cp.add(new HTML("<p><i>Welcome to IBB registration form!</i><br />"+
				"<b>IBB ti d&agrave; il benvenuto sul form di registrazione!</b></p>"));
		cp.add(new HTML("<p>&nbsp;</p>"));
		
		cp.add(new HTML("<p><i>Have you already been at IBB or similar events?</i><br />"+
				"<b>Hai gi&agrave; partecipato a IBB o a eventi simili?</b></p>"));
		VerticalPanel hp = new VerticalPanel();
		cp.add(hp);
		ibbCheck = new CheckBox("&nbsp; Italian Burning Boots 2015", true);
		ibbCheck.setValue(participant.getAlreadyIbb());
		hp.add(ibbCheck);
		burnerCheck = new CheckBox("&nbsp; Nowhere, Burning Man or other burns", true);
		burnerCheck.setValue(participant.getAlreadyBurner());
		hp.add(burnerCheck);
		cp.add(new HTML("<p>&nbsp;</p>"));
		
		cp.add(new HTML("<p><i>Your email to receive information about the event</i><br/>"+
				"<b>La tua email per ricevere dettagli e avvisi sull'evento</b></p>"));
		emailText = new TextBox();
		emailText.setValue(participant.getEmail());
		cp.add(emailText);
		cp.add(new HTML("<p>&nbsp;</p>"));
		
		cp.add(new HTML("<p><i>(You will be signed up to the mailing list for participants and official announcements)</i><br/>"
				+ "<b>(Il tuo indirizzo sar&agrave; inserito nella mailing list dei partecipanti e degli annunci ufficiali)</b></p>"));
		
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
			participant.setEmail(email);
			participant.setAlreadyIbb(ibbCheck.getValue());
			participant.setAlreadyBurner(burnerCheck.getValue());
			//Forward
			UriBuilder param = new UriBuilder();
			param.add(AppConstants.PARAMS_ITEM_NUMBER, participant.getItemNumber());
			param.triggerUri(UriDispatcher.STEP_JOIN_FOOD);
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
