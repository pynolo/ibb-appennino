package it.burningboots.join.client;

import it.burningboots.join.client.frame.JoinFoodFrame;
import it.burningboots.join.client.frame.JoinNameFrame;
import it.burningboots.join.client.frame.JoinCheckoutFrame;
import it.burningboots.join.client.frame.JoinVolunteerFrame;

import com.google.gwt.user.client.ui.SimplePanel;

public class UriDispatcher {
	
	public static final String SEPARATOR_TOKEN = "!";// Â£
	public static final String SEPARATOR_PARAMS = "/"; // /
	public static final String SEPARATOR_VALUES = "=";
	
	public static final String INDEX = "index";
	public static final String STEP_JOIN_NAME = "name";
	public static final String STEP_JOIN_VOLUNTEER = "volunteer";
	public static final String STEP_JOIN_FOOD = "food";
	public static final String STEP_JOIN_CHECKOUT = "checkout";
	public static final String STEP_FULL = "full";
	public static final String STEP_CLOSED = "closed";
	public static final String STEP_THANK_YOU = "thankyou";
	public static final String PARTICIPANTS = "participants";
	public static final String STEP_TRANSFER_1 = "transfer1";
	public static final String STEP_TRANSFER_2 = "transfer2";
	
	//Reloads the current page from the server reload(true) and not from cache reload(false)
	public static native void hardReload() /*-{
	  $wnd.location.reload(true);
	}-*/;
	
	public static void loadContent(String fullToken) {
		if (fullToken != null) {
			String token = tokenFromUri(fullToken);
			UriBuilder params = paramsFromUri(fullToken);
			SimplePanel contentPanel = UiSingleton.get().getContentPanel();
			contentPanel.clear();
			if (INDEX.equals(token)) {
				contentPanel.add(new JoinNameFrame(params));
			}
			if (STEP_JOIN_NAME.equals(token)) {
				contentPanel.add(new JoinNameFrame(params));
			}
			if (STEP_JOIN_FOOD.equals(token)) {
				contentPanel.add(new JoinFoodFrame(params));
			}
			if (STEP_JOIN_VOLUNTEER.equals(token)) {
				contentPanel.add(new JoinVolunteerFrame(params));
			}
			if (STEP_JOIN_CHECKOUT.equals(token)) {
				contentPanel.add(new JoinCheckoutFrame(params));
			}
			if (STEP_FULL.equals(token)) {
				//contentPanel.add(new AnagraficheFindFrame(params));
			}
			if (STEP_CLOSED.equals(token)) {
				//contentPanel.add(new AnagraficheModifiedFindFrame(params));
			}
			if (STEP_THANK_YOU.equals(token)) {
				//contentPanel.add(new FeedbackAnagraficheFindFrame(params));
			}
			if (PARTICIPANTS.equals(token)) {
				//contentPanel.add(new FeedbackAnagraficaFrame(params));
			}
			if (STEP_TRANSFER_1.equals(token)) {
				//contentPanel.add(new AbbonamentoFrame(params));
			}
			if (STEP_TRANSFER_2.equals(token)) {
				//contentPanel.add(new AbbonamentiModifiedFindFrame(params));
			}
		}
	}
	
	private static String tokenFromUri(String fullToken) {
		String result = null;
		String[] pieces = fullToken.split(SEPARATOR_TOKEN);
		if (pieces.length >= 1) {
			result = pieces[0];
		}
		return result;
	}
	
	private static UriBuilder paramsFromUri(String fullToken) {
		//Extract only the part after SEPARATOR_TOKEN
		String parameters = null;
		String[] tokenPieces = fullToken.split(SEPARATOR_TOKEN);
		if (tokenPieces.length >= 2) {
			parameters = tokenPieces[1];
		} else {
			return null;
		}
		
		//Extract parameters and put them in a map
		UriBuilder result = new UriBuilder();
		String[] pieces = parameters.split(SEPARATOR_PARAMS);
		if (pieces.length >= 1) {
			for (String piece : pieces) {
				String[] couple = piece.split(SEPARATOR_VALUES);
				if (couple.length >= 2) {
					result.add(couple[0], couple[1]);
				}
			}
		}
		return result;
	}
}
