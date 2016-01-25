package it.burningboots.join.client.frame;

import it.burningboots.join.client.ClientConstants;
import it.burningboots.join.client.UiSingleton;
import it.burningboots.join.client.UriDispatcher;
import it.burningboots.join.client.WizardSingleton;
import it.burningboots.join.shared.AppConstants;
import it.burningboots.join.shared.PropertyBean;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;

public class FramePanel extends FlowPanel {

	public FramePanel() {
		super();
		setTitle(ClientConstants.DEFAULT_FRAME_TITLE);
	}
	
	public void setTitle(String frameTitle) {
		UiSingleton.get().setApplicationTitle(frameTitle);
	    SimplePanel headerPanel = UiSingleton.get().getHeaderPanel();
	    headerPanel.clear();
	    String title = ClientConstants.DEFAULT_FRAME_TITLE;
		if (frameTitle != null) {
			title = frameTitle;
		}
		headerPanel.add(new HTML("<h1>"+title+"</h1>"));
	}
	
	//private void setBrowserWindowTitle(String windowTitle) {
	//	String title = ClientConstants.DEFAULT_FRAME_TITLE;
	//	if (windowTitle != null) {
	//		title = windowTitle;
	//	}
	//    if (Document.get() != null) {
	//        Document.get().setTitle(title);
	//    }
	//}
	
	public void forwardIfJoinNotPossible() {
		PropertyBean p = WizardSingleton.get().getPropertyBean();
		Boolean isRegisterWizard = WizardSingleton.get().getWizardType()
				.equals(AppConstants.WIZARD_REGISTER);
		//Check if joining wizard can be active
		if (isRegisterWizard) {
			if ( p.getClosed() ) {
				UriDispatcher.loadContent(UriDispatcher.STEP_ERR_CLOSED);
			}
			if ( ((p.getBedCount() >= p.getBedAvailableUntil()) && (p.getTentCount() >= p.getTentAvailableUntil()))
					||
					((p.getBedCount() >= p.getBedMax()) && (p.getTentCount() >= p.getTentMax())) ) {
				UriDispatcher.loadContent(UriDispatcher.STEP_ERR_CLOSED);
			}
		}
	}
	
}
