package it.burningboots.join.client.frame;

import it.burningboots.join.client.UriBuilder;
import it.burningboots.join.shared.AppConstants;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ErrorPaymentFrame extends FramePanel {
	
	private VerticalPanel cp = null; // Content panel
		
	public ErrorPaymentFrame(UriBuilder params) {
		super();
		cp = new VerticalPanel();
		this.add(cp);
		draw();
	}
	
	private void draw() {
		forwardIfJoinNotPossible();
		
		//TITLE
		setTitle("Error during transaction / Errore durante il pagamento");
		
		cp.add(new HTML("<p><i>It looks like there has been an error during payment!<br />"+
				"But don't worry, just write to info@burningboots.it so we can mark your registration as confirmed/paid!</i></p>"+
				"<p><b>C'&egrave; stato un errore durante il pagamento!<br />"+
				"Ma non preoccuparti, scrivi subito a info@burningboots.it cos&igrave; segneremo la tua iscrizione come confermata/pagata!</b></p>"));
		cp.add(new HTML("<p>&nbsp;</p>"));
	
		cp.add(new HTML("<h3><a href='"+AppConstants.EVENT_URL+"'><i class='fa fa-hand-o-left'></i> <b>Italian Burning Boots</b></a></h3>"));
	}
	
}
