package it.burningboots.join.client.frame;

import it.burningboots.join.client.UriBuilder;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ErrorFullFrame extends FramePanel {
	
	private VerticalPanel cp = null; // Content panel
		
	public ErrorFullFrame(UriBuilder params) {
		super();
		cp = new VerticalPanel();
		this.add(cp);
		draw();
	}
	
	private void draw() {
		forwardIfJoinNotPossible();
		
		//TITLE
		setTitle("Sorry, we're full / Siamo al completo");
		
		cp.add(new HTML("<p><i>It looks like there are no more places left!<br />"+
				"But don't worry, there's a discussion group where you can find persons who can't attend, so you can replace them.</i><br />"+
				"<b>Direi proprio che siamo al completo!<br />"+
				"Ma non preoccuparti, c'&egrave; un gruppo di discussione dove puoi trovare persone che puoi sostituire perch&eacute; non possono partecipare.</b></p>"));
		cp.add(new HTML("<p>&nbsp;</p>"));
	
		cp.add(new HTML("<h3><a href='https://burningboots.it'><i class='fa fa-hand-o-left'></i> <b>Italian Burning Boots</b></a></h3>"));
	}
	
}
