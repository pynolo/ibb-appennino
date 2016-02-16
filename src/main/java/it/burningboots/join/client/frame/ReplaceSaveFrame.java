package it.burningboots.join.client.frame;

import it.burningboots.join.client.ClientConstants;
import it.burningboots.join.client.LocaleConstants;
import it.burningboots.join.client.UiSingleton;
import it.burningboots.join.client.UriBuilder;
import it.burningboots.join.client.WaitSingleton;
import it.burningboots.join.client.WizardSingleton;
import it.burningboots.join.client.service.DataService;
import it.burningboots.join.client.service.DataServiceAsync;
import it.burningboots.join.shared.AppConstants;
import it.burningboots.join.shared.entity.Participant;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ReplaceSaveFrame extends FramePanel {
	
	private final DataServiceAsync dataService = GWT.create(DataService.class);
	private LocaleConstants constants = GWT.create(LocaleConstants.class);
	
	private UriBuilder params = null;
	private VerticalPanel cp = null; // Content panel
	
	private VerticalPanel checkoutPanel = null;
	
	public ReplaceSaveFrame(UriBuilder params) {
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
		saveOrUpdateAsyncData(itemNumber);
	}
	
	private void draw() {
		if (WizardSingleton.get().getWizardType().equals(AppConstants.WIZARD_REGISTER)) 
				forwardIfJoinNotPossible();
		Participant participant = WizardSingleton.get().getParticipantBean();
		
		//TITLE
		setTitle(constants.replaceSaveTitle());
		
		checkoutPanel = new VerticalPanel();
		cp.add(checkoutPanel);
		String amountString = null;
		if (participant.getPaymentAmount() != null) {
			if (participant.getPaymentAmount() > 0) {
				amountString = ClientConstants.FORMAT_CURRENCY.format(participant.getPaymentAmount());
			}
		}
		
		String type = "";
		if (participant.getAccommodationType().equals(AppConstants.ACCOMMODATION_BED)) {
			type = constants.hut();
		} 
		if (participant.getAccommodationType().equals(AppConstants.ACCOMMODATION_TENT)) {
			type = constants.tent();
		} 
		if (amountString == null) {
			type += " ["+constants.replaceSaveNotConfirmed()+"]";
			amountString = "--";
		}
		
		checkoutPanel.add(new HTML("<p>"+constants.replaceSaveCongratulations()+"<br /><br />"+
				constants.replaceSaveDetails()+"<br />"+
				constants.replaceSaveAmount()+": &euro;"+amountString+"<br />"+
				constants.replaceSaveAccommodation()+": "+type+"</p>"));
		
		cp.add(new HTML("<h3><a href='"+AppConstants.EVENT_URL+"'><i class='fa fa-hand-o-left'></i> <b>Italian Burning Boots</b></a></h3>"));

	}
	
	
	//Async methods
	
	private void saveOrUpdateAsyncData(String itemNumber) {
		AsyncCallback<Integer> callback = new AsyncCallback<Integer>() {
			@Override
			public void onFailure(Throwable caught) {
				UiSingleton.get().addError(caught);
				WaitSingleton.get().stop();
			}
			@Override
			public void onSuccess(Integer id) {
				draw();
				WaitSingleton.get().stop();
			}
		};
		if (itemNumber != null) {
			Participant prt = WizardSingleton.get().getParticipantBean();
			if (prt.getItemNumber().equals(itemNumber)) {
				WaitSingleton.get().start();
				dataService.saveOrUpdateParticipant(prt, callback);
			}
		} else {
			UiSingleton.get().addWarning(constants.replaceSaveErrorCode());
		}
	}
	
}
