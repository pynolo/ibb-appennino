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
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ReplaceBaseFrame extends FramePanel implements IWizardPanel {
	
	private final DataServiceAsync dataService = GWT.create(DataService.class);
	
	private UriBuilder params = null;
	private VerticalPanel cp = null; // Content panel
	
	private TextBox codeText;
	private TextBox emailText;
	private String itemNumber = null;
	private String email = null;
	
	public ReplaceBaseFrame(UriBuilder params) {
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
		draw();
	}
	
	private void draw() {
		if (WizardSingleton.get().getWizardType().equals(AppConstants.WIZARD_REGISTER))
				forwardIfJoinNotPossible();
		Participant participant = WizardSingleton.get().getParticipantBean();
		if (participant == null) participant = new Participant();
		
		//TITLE
		setTitle("Replacement / Sostituzione");
		
		cp.add(new HTML("<p><i>Welcome to IBB replacement form! Please provide the replacement code:</i><br />"+
				"<b>Benvenuto sul form di sostituzione di IBB! Fornisci il codice di sostituzione:</b></p>"));
		codeText = new TextBox();
		codeText.setValue(participant.getItemNumber());
		cp.add(codeText);
		
		cp.add(new HTML("<p>&nbsp;</p>"));
		
		
		cp.add(new HTML("<p><i>Your email to receive information about the event</i><br/>"+
				"<b>La tua email per ricevere dettagli e avvisi sull'evento</b></p>"));
		emailText = new TextBox();
		emailText.setValue(participant.getEmail());
		cp.add(emailText);
		cp.add(new HTML("<p><i>(You will be signed up to the official announcement miling list)</i><br/>"
				+ "<b>(Il tuo indirizzo sar&agrave; inserito nella mailing list degli annunci ufficiali)</b></p>"));
		
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
		itemNumber = codeText.getValue();
		try {
			StringValidator.validateItemNumber(itemNumber);
		} catch (ValidationException e) {
			if (errorMessage.length() > 0) errorMessage += "<br />";
			errorMessage += e.getMessage();
		}
		email = emailText.getValue();
		try {
			StringValidator.validateEmail(email);
		} catch (ValidationException e) {
			if (errorMessage.length() > 0) errorMessage += "<br />";
			errorMessage += e.getMessage();
		}
		
		if (errorMessage.length() > 0) {
			UiSingleton.get().addWarning(errorMessage);
		} else {
			loadAsyncData(itemNumber);
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
				if (result.getEmail() == null) result.setEmail("");
				if (result.getEmail().equals("")) {
					// NOT FOUND
					UiSingleton.get().addWarning("Incorrect code / Codice errato");
				} else {
					// ITEM NUMBER FOUND
					UiSingleton.get().addInfo("The code is valid / Il codice &egrave; valido");
					WizardSingleton.get().setParticipantBean(result);
					//Store in bean
					result.setEmail(email);
					//Forward
					UriBuilder param = new UriBuilder();
					param.add(AppConstants.PARAMS_ITEM_NUMBER, result.getItemNumber());
					param.triggerUri(UriDispatcher.STEP_JOIN_VOLUNTEER);
				}
			}
		};
		
		dataService.findParticipantByItemNumber(itemNumber, callback);
	}
	
}
