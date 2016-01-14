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

public class JoinCheckoutFrame extends FramePanel {
	
	private final DataServiceAsync dataService = GWT.create(DataService.class);
	
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
		forwardIfJoinNotPossible();
		Participant participant = WizardSingleton.get().getParticipantBean();
		
		//TITLE
		setTitle("Confirm your registration / Conferma la registrazione");
		
		checkoutPanel = new VerticalPanel();
		cp.add(checkoutPanel);
		String amountString = "[ERROR]";
		if (participant.getAccommodationType().equals(AppConstants.ACCOMMODATION_BED)) {
			amountString = ClientConstants.FORMAT_CURRENCY.format(WizardSingleton.get().getPropertyBean().getBedPrice());
		} 
		if (participant.getAccommodationType().equals(AppConstants.ACCOMMODATION_TENT)) {
			amountString = ClientConstants.FORMAT_CURRENCY.format(WizardSingleton.get().getPropertyBean().getTentPrice());
		} 
		
		checkoutPanel.add(new HTML("<p><i>YOU'RE NOT REGISTERED YET, there's just one more step:<br />"+
				"You need to confirm with a donation, to cover costs like rental and food.</i></p>"+
				"<p><i>Minimum amount is </i><b>&euro;"+amountString+"</b><i> "+ 
				"but if you want to donate more contact us, we'll use it to add some extras!</i><br/>"+
				"&nbsp;</p>"));
		
		checkoutPanel.add(new HTML("<p><b>LA REGISTRAZIONE NON &Egrave; FINITA, manca solo l'ultimo passo:<br />"+
				"Devi confermare con una donazione, per coprire costi come affitti e cibo.</b></p>"+
				"<p><b>L'importo minimo &egrave; </b><i>&euro;"+amountString+"</i><b> "+
				"ma se vuoi donare di pi&ugrave; contattaci e penseremo a degli extra!</b><br />"+
				"&nbsp</p>"));
		
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
				"<input type='hidden' name='return' value='"+AppConstants.THANKYOU_URL+"?code="+participant.getItemNumber()+"'>"+
				"<input type='submit' name='submit' title='PayPal' class='btn btn-primary btn-lg' "+
						"value=' Donate to confirm / Per confermare fai una donazione ' />"+
					"<i>Minimum &euro;"+amountString+"</i> / "+
					"<b>Minimo &euro;"+amountString+"</b>"+
					"<!--input type='image' src='https://www.paypal.com/en_AU/i/btn/btn_buynow_LG.gif' border='0' name='submit' alt='PayPal - The safer, easier way to pay online.'-->"+
				"</form>"));
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
