package it.burningboots.join.client.frame;

import it.burningboots.join.client.ClientConstants;
import it.burningboots.join.client.UiSingleton;
import it.burningboots.join.client.UriBuilder;
import it.burningboots.join.client.UriDispatcher;
import it.burningboots.join.client.WizardSingleton;
import it.burningboots.join.client.service.DataService;
import it.burningboots.join.client.service.DataServiceAsync;
import it.burningboots.join.shared.AppConstants;
import it.burningboots.join.shared.entity.Participant;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

public class JoinThankYouFrame extends FramePanel {
	
	private final DataServiceAsync dataService = GWT.create(DataService.class);
	
	private UriBuilder params = null;
	private VerticalPanel cp = null; // Content panel
	
	public JoinThankYouFrame(UriBuilder params) {
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
		Participant participant = WizardSingleton.get().getParticipantBean();
		String amountString = "##";
		if (participant.getPaymentAmount() != null) {
			amountString = ClientConstants.FORMAT_CURRENCY.format(participant.getPaymentAmount());
		} else {
			//Payment not registered
			UriBuilder param = new UriBuilder();
			param.add(AppConstants.PARAMS_ITEM_NUMBER, participant.getItemNumber());
			param.triggerUri(UriDispatcher.ERROR_PAYMENT);
		}
		//TITLE
		setTitle("You're in! / Sei dei nostri!");
		
		cp.add(new HTML("<p><i>Donation: &euro;"+amountString+
				". Your registration is confirmed!</i><br />"+
				"<b>Donazione: &euro;"+amountString+
				". La tua registrazione &egrave; confermata!</b></p>"));
		
		cp.add(new HTML("<p>&nbsp;</p>"));
		
		cp.add(new HTML("<p style='text-align: center; font-size: 1.5em; color: #e32077;'>"+
				"Replacement code / Codice di sostituzione</p>"));
		
		cp.add(new HTML("<p style='text-align: center; font-size: 4.5em; color: #e32077;'>"+
				participant.getItemNumber().toUpperCase()+"</p>"));
		cp.add(new HTML("<p style='text-align: center; font-size: 1em;'>"+
				"take note ;)</p>"));
		
		cp.add(new HTML("<p><i>What's this code for?!<br />"+
				"Tell it to someone to let her/him take your place "+
				"at IBB </i><b>replacing your registration</b><i> data.<br/>"+
				"(You can find people to replace you in the &quot;Replacement&quot; Forum)</i></p>"+
					
				"<p><b>A che serve il codice?!<br />"+
				"Dallo a qualcun altro perch&eacute; possa prendere il tuo "+
				"posto a IBB </b><i>sostituendo i suoi dati</i><b> ai tuoi.<br/>"+
				"(Puoi trovare chi ti pu&ograve; sostituire nel Forum &quot;Sostituzione&quot;)</b></p>"));

		cp.add(new HTML("<p>&nbsp;</p>"));
				
		cp.add(new HTML("<h3><a href='"+AppConstants.EVENT_URL+"'><i class='fa fa-hand-o-left'></i> <b>Italian Burning Boots</b></a></h3>"));
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
		
		if (itemNumber.length() > 0) {
			dataService.findParticipantByItemNumber(itemNumber, callback);
		}
	}
	
}
