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
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

public class JoinFoodFrame extends FramePanel implements IWizardPanel {
	
	private final DataServiceAsync dataService = GWT.create(DataService.class);
	
	private UriBuilder params = null;
	private VerticalPanel cp = null; // Content panel
	private int participantCount = 0;
	
	private TextArea foodArea;
	
	public JoinFoodFrame(UriBuilder params) {
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
		
		//Check if joining wizard can be active
		if ( properties.getClosed() ) {
			UriDispatcher.loadContent(UriDispatcher.STEP_CLOSED);
		}
		if ( (participantCount >= properties.getBedAvailableUntil()) &&
				(participantCount >= properties.getTentAvailableUntil()) ) {
			UriDispatcher.loadContent(UriDispatcher.STEP_FULL);
		}
		//TITLE
		setTitle("Food restrictions / Restrizioni sul cibo");
		
		cp.add(new HTML("<p><i>Please tell us your food restrictions: this is important if you are vegetarian or vegan or if you suffer from an allergy</i><br />"+
				"<b>Per favore dicci le tue restrizioni sul cibo: &egrave; fondamentale per i vegetariani, i vegani e per chi soffre di allergie</b></p><br/>"));
		foodArea = new TextArea();
		foodArea.setHeight("5em");
		foodArea.setWidth("100%");
		foodArea.setValue(participant.getFoodRestrictions());
		cp.add(foodArea);
		cp.add(new HTML("<p>&nbsp;</p>"));
		
		//Wizard panel
		WizardButtons wb = new WizardButtons(this, true, true);
		cp.add(wb);
	}
	
	
	private void storeInBean() {
		if (foodArea.getValue().length() > 0) {
			Participant participant = WizardSingleton.get().getParticipantBean();
			participant.setFoodRestrictions(foodArea.getValue());
		}
	}
	
	@Override
	public void goBackward() {
		storeInBean();
		Participant participant = WizardSingleton.get().getParticipantBean();
		//Forward
		UriBuilder param = new UriBuilder();
		param.add(AppConstants.PARAMS_ITEM_NUMBER, participant.getItemNumber());
		param.triggerUri(UriDispatcher.STEP_JOIN_INCLUSION);
	}
	
	@Override
	public void goForward() {
		storeInBean();
		Participant participant = WizardSingleton.get().getParticipantBean();
		//Forward
		UriBuilder param = new UriBuilder();
		param.add(AppConstants.PARAMS_ITEM_NUMBER, participant.getItemNumber());
		param.triggerUri(UriDispatcher.STEP_JOIN_VOLUNTEER);
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
