package it.burningboots.join.client.frame;

import it.burningboots.join.client.LocaleConstants;
import it.burningboots.join.client.UriBuilder;
import it.burningboots.join.shared.AppConstants;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ErrorClosedFrame extends FramePanel {
	
	private LocaleConstants constants = GWT.create(LocaleConstants.class);
	
	private VerticalPanel cp = null; // Content panel
		
	public ErrorClosedFrame(UriBuilder params) {
		super();
		cp = new VerticalPanel();
		this.add(cp);
		draw();
	}
	
	private void draw() {
		forwardIfJoinNotPossible();
		
		//TITLE
		setTitle(constants.errorClosedTitle());
		
		cp.add(new HTML("<p>"+constants.errorClosedInfo()+"<br />"+
				constants.errorClosedAsk()+"</p>"));
		cp.add(new HTML("<p>&nbsp;</p>"));
	
		cp.add(new HTML("<h3><a href='"+AppConstants.EVENT_URL+"'><i class='fa fa-hand-o-left'></i> <b>Italian Burning Boots</b></a></h3>"));
	}
	
}
