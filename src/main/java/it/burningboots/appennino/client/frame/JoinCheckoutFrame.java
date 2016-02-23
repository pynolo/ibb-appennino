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

public class JoinCheckoutFrame extends FramePanel {
	
	private final DataServiceAsync dataService = GWT.create(DataService.class);
	private LocaleConstants constants = GWT.create(LocaleConstants.class);
	
	private UriBuilder params = null;
	private VerticalPanel cp = null; // Content panel
	
	private VerticalPanel checkoutPanel = null;
	
	public JoinCheckoutFrame(UriBuilder params) {
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
		setTitle(constants.joinCheckoutTitle());
		
		checkoutPanel = new VerticalPanel();
		cp.add(checkoutPanel);
		String amountString = "[ERROR]";
		String type = "";
		if (participant.getAccommodationType().equals(AppConstants.ACCOMMODATION_BED)) {
			amountString = ClientConstants.FORMAT_CURRENCY.format(WizardSingleton.get().getPropertyBean().getBedPrice());
			type = constants.hut();
		} 
		if (participant.getAccommodationType().equals(AppConstants.ACCOMMODATION_TENT)) {
			amountString = ClientConstants.FORMAT_CURRENCY.format(WizardSingleton.get().getPropertyBean().getTentPrice());
			type = constants.tent();
		}
		amountString = amountString.replaceAll(",", "\\.");//Non deve essere nel formato italiano
		
		checkoutPanel.add(new HTML("<p>"+constants.joinCheckoutOneMoreStep()+"<br />"+
				constants.joinCheckoutPleaseConfirm()+"</p>"+
				"<p>"+constants.joinCheckoutMinimumAmount()+" <b>&euro;"+amountString+"</b> ("+type+") "+ 
				constants.joinCheckoutContactUs()+"<br/>"+
				"&nbsp;</p>"));
				
		checkoutPanel.add(new HTML("<p>&nbsp;<br /></p>"+
				"<form action='"+AppConstants.PAYPAL_URL+"' method='post'>"+
				"<input type='hidden' name='cmd' value='_donations'>"+
				"<input type='hidden' name='business' value='"+AppConstants.PAYPAL_ACCOUNT+"'>"+
				"<input type='hidden' name='item_name' value='Italian Burning Boots'>"+
				"<input type='hidden' name='item_number' value='"+participant.getItemNumber()+"'>"+
				"<input type='hidden' name='amount' value='"+amountString+"'>"+
				"<input type='hidden' name='no_shipping' value='1'>"+
				"<input type='hidden' name='no_note' value='1'>"+
				"<input type='hidden' name='currency_code' value='EUR'>"+
				"<input type='hidden' name='lc' value='US'>"+
				"<input type='hidden' name='notify_url' value='"+AppConstants.IPN_URL+"'>"+
				"<input type='hidden' name='return' value='"+
						AppConstants.BASE_URL+"/"+/*?locale="+constants.locale()+*/
						"#"+UriDispatcher.STEP_THANK_YOU+
						UriDispatcher.SEPARATOR_TOKEN+AppConstants.PARAMS_ITEM_NUMBER+UriDispatcher.SEPARATOR_VALUES+
						participant.getItemNumber()+"'>"+
				"<input type='submit' name='submit' title='PayPal' class='btn btn-primary btn-lg' "+
						"value=' "+constants.joinCheckoutDonateButton()+" ' />&nbsp;"+
						constants.joinCheckoutMinimum()+" &euro;"+amountString+
					"<!--input type='image' src='https://www.paypal.com/en_AU/i/btn/btn_buynow_LG.gif' border='0' name='submit' alt='PayPal - The safer, easier way to pay online.'-->"+
				"</form>"));
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
			UiSingleton.get().addWarning("No participant id has been provided");
		}
	}
	
}
