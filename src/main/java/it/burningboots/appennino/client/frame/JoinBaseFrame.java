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
import it.burningboots.appennino.client.widgets.ExtendedTextBox;
import it.burningboots.appennino.client.widgets.WizardButtons;
import it.burningboots.appennino.shared.AppConstants;
import it.burningboots.appennino.shared.PropertyBean;
import it.burningboots.appennino.shared.StringValidator;
import it.burningboots.appennino.shared.ValidationException;
import it.burningboots.appennino.shared.entity.Participant;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.VerticalPanel;

public class JoinBaseFrame extends FramePanel implements IWizardPanel {
	
	private final DataServiceAsync dataService = GWT.create(DataService.class);
	private LocaleConstants constants = GWT.create(LocaleConstants.class);
	private PropertyBean pb = null;
	
	private UriBuilder params = null;
	private VerticalPanel cp = null; // Content panel
	
	private RadioButton hutRadio;
	private RadioButton tentRadio;
	private ExtendedTextBox emailText;
	private InlineHTML discountLabel;
	
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
		pb = WizardSingleton.get().getPropertyBean();
		loadAsyncData(itemNumber);
	}
	
	private void draw() {		
		if (WizardSingleton.get().getWizardType().equals(AppConstants.WIZARD_REGISTER))
				forwardIfJoinNotPossible();
		Participant participant = WizardSingleton.get().getParticipantBean();
		
		//TITLE
		setTitle(constants.joinBaseTitle());
		
		VerticalPanel formPanel = new VerticalPanel();
		cp.add(formPanel);
		formPanel.add(new HTML("<p>"+constants.joinBaseWelcome()+"</p>"));
		
		//EMAIL
		
		formPanel.add(new HTML("<p>"+constants.joinBaseEmail()+"</p>"));
		HorizontalPanel emailPanel = new HorizontalPanel();
		emailText = new ExtendedTextBox();
		emailText.setValue(participant.getEmail());
		emailPanel.add(emailText);
		emailPanel.add(new InlineHTML("&nbsp;&nbsp;"));
		discountLabel = new InlineHTML();
		emailPanel.add(discountLabel);
		formPanel.add(emailPanel);
		formPanel.add(new HTML("<p><i>"+constants.joinBaseEmailWarning()+"</i></p>"));

		emailText.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (emailText.getValue() != null) {
					onEmailChange(emailText.getValue());
				}
			}
		});
		emailText.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
	        public void onValueChange(ValueChangeEvent<String> event) {
	        	if (emailText.getValue() != null) {
	        		onEmailChange(emailText.getValue());
				}
	        }
	    });
		formPanel.add(new HTML("<p>&nbsp;</p>"));
		
		//ACCOMMODATION
		formPanel.add(new HTML("<p>"+constants.joinBaseAccommodation()+"</p>"));
		
		FlexTable accmTable = new FlexTable();
		formPanel.add(accmTable);
		
		//hut radio
		hutRadio = new RadioButton("accommodation");
		accmTable.setWidget(0, 0, hutRadio);
		//hut img
		accmTable.setWidget(0, 1, new HTML("<img src='img/hut.png' />"));
		accmTable.getFlexCellFormatter().setRowSpan(0, 1, 2);
		//hut descr
		int hutAvail = pb.getAvailableHut();
		String hutDescr = "";
		if (hutAvail <= 4) {
			hutDescr += "<b>"+hutAvail+"</b> "+constants.joinBaseToSoldOut()+"<br />";
		}
		hutDescr += constants.joinBaseBedFeatures();
		accmTable.setWidget(1, 0, new HTML(hutDescr+"<br/>&nbsp;"));

		//tent radio
		tentRadio = new RadioButton("accommodation");
		accmTable.setWidget(2, 0, tentRadio);
		//tent img
		accmTable.setWidget(2, 1, new HTML("<img src='img/tent.png' />"));
		accmTable.getFlexCellFormatter().setRowSpan(2, 1, 2);
		//tent descr
		int tentAvail = pb.getAvailableTent();
		String tentDescr = "";
		if (tentAvail <= 4) {
			tentDescr += "<b>"+tentAvail+"</b> "+constants.joinBaseToSoldOut()+"<br />";
		}
		tentDescr += constants.joinBaseTentFeatures();
		accmTable.setWidget(3, 0, new HTML(tentDescr+"<br/>&nbsp;"));
		
		if (participant.getAccommodationType().equals(AppConstants.ACCOMMODATION_TENT)) {
			tentRadio.setValue(true);
		} else {
			hutRadio.setValue(true);
		}
		
		//Wizard panel
		WizardButtons wb = new WizardButtons(this, false, true);
		cp.add(wb);
		
		onEmailChange(emailText.getValue());
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
	
	private void hideDiscount() {
		discountLabel.setHTML("<i class='fa fa-comment-o'></i> "+constants.discountNo());
		hutRadio.setHTML("<b>"+constants.hut()+"</b> - &euro;"+
				ClientConstants.FORMAT_CURRENCY.format(pb.getHutPrice())+"&nbsp;&nbsp;");
		tentRadio.setHTML("<b>"+constants.tent()+"</b> - &euro;"+
				ClientConstants.FORMAT_CURRENCY.format(pb.getTentPrice())+"&nbsp;&nbsp;");
	}
	
	private void showDiscount() {
		discountLabel.setHTML("<i class='fa fa-check-circle'></i> "+constants.discountYes());
		hutRadio.setHTML("<b>"+constants.hut()+"</b> - &euro;"+
				ClientConstants.FORMAT_CURRENCY.format(pb.getHutPriceLow())+
				" ("+constants.discount()+")&nbsp;&nbsp;");
		tentRadio.setHTML("<b>"+constants.tent()+"</b> - &euro;"+
				ClientConstants.FORMAT_CURRENCY.format(pb.getTentPriceLow())+
				" ("+constants.discount()+")&nbsp;&nbsp;");
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
	
	
	public void onEmailChange(String email) {
		AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {
			@Override
			public void onFailure(Throwable caught) {
				UiSingleton.get().addError(caught);
				hideDiscount();
			}
			@Override
			public void onSuccess(Boolean result) {
				if (result == null) result = false;
				if (result) {
					showDiscount();
				} else {
					hideDiscount();
				}
			}
		};
		hideDiscount();
		dataService.canHaveDiscount(email, callback);
	}
	
}
