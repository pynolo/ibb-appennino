package it.burningboots.join.client.frame;

import it.burningboots.join.client.UiSingleton;
import it.burningboots.join.client.UriBuilder;
import it.burningboots.join.client.UriDispatcher;
import it.burningboots.join.client.WizardSingleton;
import it.burningboots.join.client.service.DataService;
import it.burningboots.join.client.service.DataServiceAsync;
import it.burningboots.join.shared.AppConstants;
import it.burningboots.join.shared.PropertyBean;
import it.burningboots.join.shared.entity.Participant;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

public class JoinVolunteerFrame extends FramePanel implements IWizardPanel {
	
	private final DataServiceAsync dataService = GWT.create(DataService.class);
	
	private UriBuilder params = null;
	private VerticalPanel cp = null; // Content panel
	private int participantCount = 0;
	
	private CheckBox kitchenCheck;
	private CheckBox woodCheck;
	private CheckBox greeterCheck;
	
	public JoinVolunteerFrame(UriBuilder params) {
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
		PropertyBean properties = WizardSingleton.get().getPropertyBean();
		Participant participant = WizardSingleton.get().getParticipantBean();
		String volunteer = participant.getVolunteering();
		if (volunteer == null) volunteer = "";
		
		//Check if joining wizard can be active
		if ( properties.getClosed() ) {
			UriDispatcher.loadContent(UriDispatcher.STEP_CLOSED);
		}
		if ( (participantCount >= properties.getBedAvailableUntil()) &&
				(participantCount >= properties.getTentAvailableUntil()) ) {
			UriDispatcher.loadContent(UriDispatcher.STEP_FULL);
		}
		//TITLE
		setTitle("Participation / Partecipazione");
		
		cp.add(new HTML("<p><i>IBB exists only because everyone does their part :) Tell us how you'd like to help!</i><br/>"+
				"<b>IBB esiste solo perch&eacute; tutti fanno qualcosa :) Scegli ci&ograve; di cui potresti occuparti!</b></p>"));
		
		kitchenCheck = new CheckBox("Kitchen superstar", true);
		kitchenCheck.setValue(volunteer.contains(AppConstants.VOLUNTEER_KITCHEN));
		cp.add(kitchenCheck);
		cp.add(new HTML("<i>to give your personal touch in the kitchen</i> / "+
				"<b>per dare il tuo tocco personale in cucina</b><br/>&nbsp;"));
		
		greeterCheck = new CheckBox("Greeter", true);
		greeterCheck.setValue(volunteer.contains(AppConstants.VOLUNTEER_GREETER));
		cp.add(greeterCheck);
		cp.add(new HTML("<i>to welcome people and help them at arrival</i> / "+
				"<b>per dare il benvenuto e aiutare all'arrivo</b><br/>&nbsp;"));
		
		woodCheck = new CheckBox("Wood bandit", true);
		woodCheck.setValue(volunteer.contains(AppConstants.VOLUNTEER_WOOD));
		cp.add(woodCheck);
		cp.add(new HTML("<i>to explore the woods and prepare the bonfire</i> / "+
				"<b>per esplorare i boschi e preparare il fuoco</b><br/>&nbsp;"));
		
		//Wizard panel
		WizardButtons wb = new WizardButtons(this, true, true);
		cp.add(wb);
	}
	
	private void storeInBean() {
		Participant participant = WizardSingleton.get().getParticipantBean();
		String volunteer = "";
		if (kitchenCheck.getValue()) volunteer += AppConstants.VOLUNTEER_KITCHEN+" ";
		if (greeterCheck.getValue()) volunteer += AppConstants.VOLUNTEER_GREETER+" ";
		if (woodCheck.getValue()) volunteer += AppConstants.VOLUNTEER_WOOD+" ";
		participant.setVolunteering(volunteer);
	}
	
	@Override
	public void goBackward() {
		storeInBean();
		Participant participant = WizardSingleton.get().getParticipantBean();
		//Forward
		UriBuilder param = new UriBuilder();
		param.add(AppConstants.PARAMS_ITEM_NUMBER, participant.getItemNumber());
		param.triggerUri(UriDispatcher.STEP_JOIN_FOOD);
	}
	
	@Override
	public void goForward() {
		storeInBean();
		Participant participant = WizardSingleton.get().getParticipantBean();
		//Forward
		UriBuilder param = new UriBuilder();
		param.add(AppConstants.PARAMS_ITEM_NUMBER, participant.getItemNumber());
		param.triggerUri(UriDispatcher.STEP_JOIN_LEGAL);
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
