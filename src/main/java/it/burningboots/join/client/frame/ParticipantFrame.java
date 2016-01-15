package it.burningboots.join.client.frame;

import it.burningboots.join.client.AuthSingleton;
import it.burningboots.join.client.ClientConstants;
import it.burningboots.join.client.CookieSingleton;
import it.burningboots.join.client.IAuthenticatedWidget;
import it.burningboots.join.client.UriBuilder;
import it.burningboots.join.client.widgets.DataModel;
import it.burningboots.join.client.widgets.ParticipantTable;
import it.burningboots.join.shared.entity.Participant;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ParticipantFrame extends FramePanel implements IAuthenticatedWidget {
	
	private String filterConfirmed = "1";
	
	private VerticalPanel panel = null;
	private FlowPanel resultPanel = null;
	
	public ParticipantFrame(UriBuilder params) {
		super();
		if (params == null) {
			params = new UriBuilder();
		}
		String sc = CookieSingleton.get().getCookie(ClientConstants.COOKIE_FILTER_CONFIRMED);
		if (sc != null) {
			filterConfirmed = sc;
		}
		
		this.setWidth("100%");
		AuthSingleton.get().queueForAuthentication(this);
	}
	
	@Override
	public void onSuccessfulAuthentication() {
		draw();
	}
	
	private void draw() {
		panel = new VerticalPanel();
		// Periodico
		FlowPanel topPanel = new FlowPanel();
		topPanel.add(new HTML("Mostra:&nbsp;"));
		final ListBox confirmList = new ListBox();
		confirmList.addItem("0", "Tutti");
		confirmList.addItem("1", "Solo i confermati");
		if (filterConfirmed.equals("0")) {
			confirmList.setSelectedIndex(0);
		} else {
			confirmList.setSelectedIndex(1);
		}
		confirmList.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				filterConfirmed = confirmList.getSelectedValue();
				CookieSingleton.get().setCookie(ClientConstants.COOKIE_FILTER_CONFIRMED,
						filterConfirmed);
			}
		});
		confirmList.setEnabled(true);
		topPanel.add(confirmList);
		panel.add(topPanel);
		resultPanel = new FlowPanel();
		panel.add(resultPanel);
		refreshTable();
	}
	
	private void refreshTable() {
		resultPanel.clear();
		//Tabella
		DataModel<Participant> model = new ParticipantTable.ParticipantModel(filterConfirmed.equals("1"));
		ParticipantTable partTable = new ParticipantTable(model);
		resultPanel.add(partTable);
	}
	
}
