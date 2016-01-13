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

		//TITLE
		setTitle("You're in! / Sei dei nostri!");
		
		cp.add(new HTML("<p><i>Hooray for "+participant.getFirstName()+"!<br/>"+
				"We just received a donation of &euro;"+
				ClientConstants.FORMAT_CURRENCY.format(participant.getPaymentAmount())+
				" from you and your registration is confirmed!</i><br />"+
				"<b>Grande "+participant.getFirstName()+"!<br/>"+
				"&Egrave; appena arrivata l'offerta di &euro;"+
				ClientConstants.FORMAT_CURRENCY.format(participant.getPaymentAmount())+
				" da parte tua e la tua registrazione &grave; confermata!</b></p>"));
		
		cp.add(new HTML("<p>&nbsp;</p>"));
		
		cp.add(new HTML("<p><i>Take note of your <b>secret personal</b> code:</i><br />"+
				"<b>Prendi nota del tuo codice <i>segreto personale</i>:</b></p>"));
		
		cp.add(new HTML("<p style='text-align: center; font-size: 4.5em; color: #e32077;'>"+
				participant.getItemNumber().toUpperCase()+"</p>"));

		cp.add(new HTML("<p><i>What's this code for?!<br />"+
				"That's simple. Give it to someone else to let her/him <b>replace your registration</b> data.<br />"+
				"So, if you will not be able to join us, just tell the one replacing you to click "+
				"TRANSFER on the IBB website, to provide the code and then the data.</i></p>"+
					
				"<p><b>A che serve il codice?!<br />"+
				"Semplice. Dallo a qualcun altro perch&egrave; possa <i>sostituire i suoi dati</i> ai tuoi.<br />"+
				"Cos&igrave;, se non potrai partecipare, baster&agrave; che tu dica chi "+
				"ti sostituir&agrave; di cliccare TRANSFER sul sito IBB, "+
				"inserire il codice e poi i dati.</b></p>"));

		cp.add(new HTML("<p>&nbsp;</p>"));
				
		cp.add(new HTML("<a href='https://burningboots.it'>Italian Burning Boots 2016</a>"));
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
