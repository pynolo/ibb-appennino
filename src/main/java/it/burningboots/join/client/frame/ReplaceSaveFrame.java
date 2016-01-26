package it.burningboots.join.client.frame;

import it.burningboots.join.client.ClientConstants;
import it.burningboots.join.client.UiSingleton;
import it.burningboots.join.client.UriBuilder;
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
		setTitle("Replacement is confirmed / La sostituzione &egrave; confermata");
		
		checkoutPanel = new VerticalPanel();
		cp.add(checkoutPanel);
		String amountString = " NOT PAID";
		if (participant.getPaymentAmount() != null) {
			if (participant.getPaymentAmount() > 0) {
				amountString = ClientConstants.FORMAT_CURRENCY.format(participant.getPaymentAmount());
			}
		}
		
		String typeIt = "";
		String typeEn = "";
		if (participant.getAccommodationType().equals(AppConstants.ACCOMMODATION_BED)) {
			typeIt = "rifugio";
			typeEn = "hut";
		} 
		if (participant.getAccommodationType().equals(AppConstants.ACCOMMODATION_TENT)) {
			typeIt = "tenda";
			typeEn = "tent";
		} 
		
		checkoutPanel.add(new HTML("<p><i>Congratulations, your replacement is confirmed!<br />"+
				"Details<br />"+
				"donation amount: &euro;"+amountString+"<br />"+
				"accommodation type: "+typeEn+"</i></p>"));
		
		checkoutPanel.add(new HTML("<p><b>COngratulazioni, la tua sostituzione &egrave; confermata.<br />"+
				"Dettagli<br />"+
				"donazione: &euro;"+amountString+"<br />"+
				"tipo di sistemazione: "+typeIt+"</b></p>"));
		
		cp.add(new HTML("<h3><a href='"+AppConstants.EVENT_URL+"'><i class='fa fa-hand-o-left'></i> <b>Italian Burning Boots</b></a></h3>"));

	}
	
	
	//Async methods
	
	private void saveOrUpdateAsyncData(String itemNumber) {
		AsyncCallback<Integer> callback = new AsyncCallback<Integer>() {
			@Override
			public void onFailure(Throwable caught) {
				UiSingleton.get().addError(caught);
			}
			@Override
			public void onSuccess(Integer id) {
				draw();
			}
		};
		if (itemNumber != null) {
			Participant prt = WizardSingleton.get().getParticipantBean();
			if (prt.getItemNumber().equals(itemNumber)) {
				dataService.saveOrUpdateParticipant(prt, callback);
			}
		} else {
			UiSingleton.get().addWarning("No participant id has been provided");
		}
	}
	
}
