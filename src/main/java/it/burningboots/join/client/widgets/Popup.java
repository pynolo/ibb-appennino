package it.burningboots.join.client.widgets;

import it.burningboots.join.client.ClientConstants;
import it.burningboots.join.client.LocaleConstants;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Popup extends PopupPanel {
	private LocaleConstants constants = GWT.create(LocaleConstants.class);
	
	public Popup(String message) {
		// PopupPanel's constructor takes 'auto-hide' as its boolean parameter.
		// If this is set, the panel closes itself automatically when the user
		// clicks outside of it.
		super(true);
		// PopupPanel is a SimplePanel, so you have to set it's widget property
		// to
		// whatever you want its contents to be.
		VerticalPanel vp = new VerticalPanel();
		vp.add(new HTML(ClientConstants.MSG_ICON_WARN+" <b>"+constants.warning()+"</b>"));
		vp.add(new HTML(message));
		setWidget(vp);
	}

	public Popup(Throwable e) {
		super(true);
		VerticalPanel vp = new VerticalPanel();
		vp.add(new HTML(ClientConstants.MSG_ICON_ERROR+" <b>"+constants.error()+"</b>"));
		vp.add(new HTML(e.getMessage()));
		setWidget(vp);
	}

	public void centerAndShow() {
		final PopupPanel pop = this;
		pop.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
			public void setPosition(int offsetWidth, int offsetHeight) {
				int left = (Window.getClientWidth() - offsetWidth) / 2;
				int top = (Window.getClientHeight() - offsetHeight) / 2;
				pop.setPopupPosition(left, top);
			}
		});
	}
	
}
