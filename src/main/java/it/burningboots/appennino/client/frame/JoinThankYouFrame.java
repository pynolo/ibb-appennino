package it.burningboots.appennino.client.frame;

import it.burningboots.appennino.client.ClientConstants;
import it.burningboots.appennino.client.LocaleConstants;
import it.burningboots.appennino.client.UiSingleton;
import it.burningboots.appennino.client.UriBuilder;
import it.burningboots.appennino.client.UriDispatcher;
import it.burningboots.appennino.client.WaitSingleton;
import it.burningboots.appennino.client.WizardSingleton;
import it.burningboots.appennino.client.service.DataService;
import it.burningboots.appennino.client.service.DataServiceAsync;
import it.burningboots.appennino.shared.AppConstants;
import it.burningboots.appennino.shared.entity.Participant;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

public class JoinThankYouFrame extends FramePanel {
	
	private static final int DELAY = 12000;// 12 seconds
	
	private final DataServiceAsync dataService = GWT.create(DataService.class);
	private LocaleConstants constants = GWT.create(LocaleConstants.class);
	
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
		setTitle(constants.joinThankYouTitle());
		
		cp.add(new HTML("<p>"+constants.joinThankYouDonation()+" &euro;"+amountString+
				". "+constants.joinThankYouConfirmed()+"</p>"));
		
		cp.add(new HTML("<p>&nbsp;</p>"));
		
		cp.add(new HTML("<p style='text-align: center; font-size: 1.5em; color: #e32077;'>"+
				constants.replacementCode()+"</p>"));
		
		cp.add(new HTML("<p style='text-align: center; font-size: 4.5em; color: #e32077;'>"+
				participant.getItemNumber().toUpperCase()+"</p>"));
		cp.add(new HTML("<p style='text-align: center; font-size: 1em;'>"+
				constants.joinThankYouTakeNote()+"</p>"));
		
		cp.add(new HTML("<p>"+constants.joinThankYouWhatIs()+"<br />"+
				"<b>"+constants.joinThankYouTellSomeone()+"</b><br/>"+
				"("+constants.joinThankYouGoToForum()+")</p>"));

		cp.add(new HTML("<p>&nbsp;</p>"));
				
		cp.add(new HTML("<h3><a href='"+AppConstants.EVENT_URL+"'><i class='fa fa-hand-o-left'></i> <b>Italian Burning Boots</b></a></h3>"));
	}
	
		
	
	//Async methods
	
	
	private void loadAsyncData(String itemNumber) {
		AsyncCallback<Participant> callback = new AsyncCallback<Participant>() {
			@Override
			public void onFailure(Throwable caught) {
				UiSingleton.get().addError(caught);
				WaitSingleton.get().stop();
			}
			@Override
			public void onSuccess(Participant result) {
				WizardSingleton.get().setParticipantBean(result);
				draw();
				WaitSingleton.get().stop();
			}
		};
		
		if (itemNumber.length() > 0) {
			WaitSingleton.get().start();
			dataService.findParticipantByItemNumber(itemNumber, DELAY, callback);
		}
	}
	
}
