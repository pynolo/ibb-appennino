package it.burningboots.appennino.client.widgets;

import it.burningboots.appennino.client.LocaleConstants;
import it.burningboots.appennino.client.UiSingleton;
import it.burningboots.appennino.client.service.DataService;
import it.burningboots.appennino.client.service.DataServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.InlineHTML;

public class DiscountLabel extends InlineHTML {
	
	private final DataServiceAsync dataService = GWT.create(DataService.class);
	private LocaleConstants constants = GWT.create(LocaleConstants.class);
	
	public DiscountLabel() {
		hideLabel();
	}
	
	public void update(String email) {
		AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {
			@Override
			public void onFailure(Throwable caught) {
				UiSingleton.get().addError(caught);
				hideLabel();
			}
			@Override
			public void onSuccess(Boolean result) {
				if (result == null) result = false;
				showLabel(result);
			}
		};
		showWait();
		dataService.canHaveDiscount(email, callback);
	}
	
	private void showWait() {
		//this.setHTML(ClientConstants.ICON_LOADING_SMALL+" "+constants.pleaseWait());
		this.setHTML("<i class='fa fa-comment-o'></i> "+constants.discountNo());
	}
	private void hideLabel() {
		//this.setHTML("<i class='fa fa-dot-circle-o'></i>");
		this.setHTML("<i class='fa fa-comment-o'></i> "+constants.discountNo());
	}
	private void showLabel(boolean discounted) {
		if (discounted) {
			this.setHTML("<i class='fa fa-check-circle'></i> "+constants.discountYes());
		} else {
			this.setHTML("<i class='fa fa-comment-o'></i> "+constants.discountNo());
		}
	}
	
}
