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
import it.burningboots.appennino.client.widgets.DiscountLabel;
import it.burningboots.appennino.client.widgets.WizardButtons;
import it.burningboots.appennino.shared.AppConstants;
import it.burningboots.appennino.shared.PropertyBean;
import it.burningboots.appennino.shared.StringValidator;
import it.burningboots.appennino.shared.ValidationException;
import it.burningboots.appennino.shared.entity.Participant;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class JoinBaseFrame extends FramePanel implements IWizardPanel {
	
	private final DataServiceAsync dataService = GWT.create(DataService.class);
	private LocaleConstants constants = GWT.create(LocaleConstants.class);
	
	private UriBuilder params = null;
	private VerticalPanel cp = null; // Content panel
	
	private RadioButton bedRadio;
	private RadioButton tentRadio;
	private TextBox emailText;
	private DiscountLabel discountLabel;
	
	public JoinBaseFrame(UriBuilder params) {
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
		if (WizardSingleton.get().getWizardType().equals(AppConstants.WIZARD_REGISTER))
				forwardIfJoinNotPossible();
		PropertyBean pb = WizardSingleton.get().getPropertyBean();
		Participant participant = WizardSingleton.get().getParticipantBean();
		
		//TITLE
		setTitle(constants.joinBaseTitle());
		
		VerticalPanel accommodationPanel = new VerticalPanel();
		cp.add(accommodationPanel);
		
		accommodationPanel.add(new HTML("<p>"+constants.joinBaseWelcome()+"</p>"));
		
		VerticalPanel bedPanel = new VerticalPanel();
		accommodationPanel.add(bedPanel);
		bedRadio = new RadioButton("accommodation", "<b>"+constants.hut()+"</b> - &euro;"+
				ClientConstants.FORMAT_CURRENCY.format(pb.getBedPrice())+"&nbsp;&nbsp;", true);
		bedPanel.add(bedRadio);
		int bedAvail = pb.getAvailableBed();
		String bedDescr = "";
		if (bedAvail <= 4) {
			bedDescr += "<b>"+bedAvail+"</b> "+constants.joinBaseSoldOut()+"<br />";
		}
		bedDescr += constants.joinBaseBedFeatures();
		bedPanel.add(new HTML(bedDescr+"<br/>&nbsp;"));
		
		VerticalPanel tentPanel = new VerticalPanel();
		accommodationPanel.add(tentPanel);
		tentRadio = new RadioButton("accommodation", "<b>"+constants.tent()+"</b> - &euro;"+
				ClientConstants.FORMAT_CURRENCY.format(pb.getTentPrice())+"&nbsp;&nbsp;", true);
		tentPanel.add(tentRadio);
		int tentAvail = pb.getAvailableTent();
		String tentDescr = "";
		if (tentAvail <= 4) {
			tentDescr += "<b>"+tentAvail+"</b> "+constants.tent()+"<br />";
		}
		tentDescr += constants.joinBaseTentFeatures();
		tentPanel.add(new HTML(tentDescr));
		
		if (participant.getAccommodationType().equals(AppConstants.ACCOMMODATION_TENT)) {
			tentRadio.setValue(true);
		} else {
			bedRadio.setValue(true);
		}
		cp.add(new HTML("<p>&nbsp;</p>"));
		
		
		cp.add(new HTML("<p>"+constants.joinBaseEmail()+"</p>"));
		HorizontalPanel emailPanel = new HorizontalPanel();
		emailText = new TextBox();
		emailText.setValue(participant.getEmail());
		emailPanel.add(emailText);
		emailPanel.add(new InlineHTML("&nbsp;&nbsp;"));
		discountLabel = new DiscountLabel();
		emailPanel.add(discountLabel);
		cp.add(emailPanel);
		cp.add(new HTML("<p><i>"+constants.joinBaseEmailWarning()+"</i></p>"));

		emailText.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (emailText.getValue() != null) {
					discountLabel.update(emailText.getValue());
				}
			}
		});

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
		String email = emailText.getValue();
		try {
			StringValidator.validateEmail(email);
		} catch (ValidationException e) {
			if (errorMessage.length() > 0) errorMessage += "<br />";
			errorMessage += e.getMessage();
		}
		
		if (errorMessage.length() > 0) {
			UiSingleton.get().addWarning(errorMessage);
		} else {
			//Store in bean
			Participant participant = WizardSingleton.get().getParticipantBean();
			if (tentRadio.getValue()) {
				participant.setAccommodationType(AppConstants.ACCOMMODATION_TENT);
			} else {
				participant.setAccommodationType(AppConstants.ACCOMMODATION_BED);
			}
			participant.setEmail(email);
			//Forward
			UriBuilder param = new UriBuilder();
			param.add(AppConstants.PARAMS_ITEM_NUMBER, participant.getItemNumber());
			param.triggerUri(UriDispatcher.STEP_JOIN_VOLUNTEER);
		}
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
		
		if (itemNumber.length() == 0) {
			//No itemNumber passed => brand new participant
			dataService.createTransientParticipant(callback);
			WaitSingleton.get().start();
		} else {
			//itemNumberKey passed => check participant in WizardSingleton and load it from DB if empty
			Participant prt = WizardSingleton.get().getParticipantBean();
			if (prt == null) {
				WaitSingleton.get().start();
				dataService.findParticipantByItemNumber(itemNumber, 0, callback);
			} else {
				draw();
			}
		}
	}
	
}
