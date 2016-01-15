package it.burningboots.join.client.frame;

import it.burningboots.join.client.UriBuilder;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ErrorClosedFrame extends FramePanel {
	
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
		setTitle("Closed / Chiuso");
		
		cp.add(new HTML("<p><i>Registrations are closed at this time.<br />"+
				"If you have important questions to ask, please write to registrations</i>@<i>burningboots.it</i><br />"+
				"<b>Le iscrizioni sono chiuse in questo momento.<br />"+
				"Se hai domande importanti al riguardo, scrivi a registrations</b>@<b>burningboots.it</b></p>"));
		cp.add(new HTML("<p>&nbsp;</p>"));
	
		cp.add(new HTML("<h3><a href='https://burningboots.it'><i class='fa fa-hand-o-left'></i> <b>Italian Burning Boots</b></a></h3>"));
	}
	
}
